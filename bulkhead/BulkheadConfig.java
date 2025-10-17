/**
 * Configuration for Bulkhead pattern
 */
public class BulkheadConfig {
    private final int maxConcurrentCalls;
    private final long maxWaitTimeMs;

    public BulkheadConfig(int maxConcurrentCalls, long maxWaitTimeMs) {
        this.maxConcurrentCalls = maxConcurrentCalls;
        this.maxWaitTimeMs = maxWaitTimeMs;
    }

    public int getMaxConcurrentCalls() { return maxConcurrentCalls; }
    public long getMaxWaitTimeMs() { return maxWaitTimeMs; }
}