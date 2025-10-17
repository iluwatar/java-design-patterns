import java.util.concurrent.*;

/**
 * Implements Bulkhead pattern to limit concurrent executions
 */
public class ServiceBulkhead {
    private final Semaphore semaphore;
    private final BulkheadConfig config;
    private final String name;
    private final BulkheadMetrics metrics;
    private final ExecutorService executor;

    public ServiceBulkhead(String name, BulkheadConfig config) {
        this.name = name;
        this.config = config;
        this.semaphore = new Semaphore(config.getMaxConcurrentCalls());
        this.metrics = new BulkheadMetrics();
        this.executor = Executors.newCachedThreadPool();
    }

    public <T> CompletableFuture<T> execute(Callable<T> task) {
        try {
            if (!semaphore.tryAcquire(config.getMaxWaitTimeMs(), TimeUnit.MILLISECONDS)) {
                metrics.recordRejectedCall();
                throw new RuntimeException("Bulkhead '" + name + "' timeout after " + 
                    config.getMaxWaitTimeMs() + "ms");
            }

            return CompletableFuture.supplyAsync(() -> {
                try {
                    T result = task.call();
                    metrics.recordSuccessfulCall();
                    return result;
                } catch (Exception e) {
                    metrics.recordFailedCall();
                    throw new CompletionException(e);
                } finally {
                    semaphore.release();
                }
            }, executor);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            metrics.recordRejectedCall();
            throw new RuntimeException("Bulkhead acquisition interrupted", e);
        }
    }

    public BulkheadMetrics getMetrics() { return metrics; }
    public int getAvailablePermits() { return semaphore.availablePermits(); }
    
    public void shutdown() {
        executor.shutdown();
    }
}