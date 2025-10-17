import java.util.concurrent.atomic.AtomicLong;

/**
 * Tracks bulkhead performance metrics
 */
public class BulkheadMetrics {
    private final AtomicLong successfulCalls = new AtomicLong();
    private final AtomicLong failedCalls = new AtomicLong();
    private final AtomicLong rejectedCalls = new AtomicLong();

    public void recordSuccessfulCall() { successfulCalls.incrementAndGet(); }
    public void recordFailedCall() { failedCalls.incrementAndGet(); }
    public void recordRejectedCall() { rejectedCalls.incrementAndGet(); }

    @Override
    public String toString() {
        return String.format("Metrics[successful=%d, failed=%d, rejected=%d]", 
            successfulCalls.get(), failedCalls.get(), rejectedCalls.get());
    }
}