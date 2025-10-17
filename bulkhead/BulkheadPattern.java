import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bulkhead Pattern - isolates application elements to prevent cascading failures
 * 
 * <p>Inspired by ship bulkheads that prevent water from flooding entire vessel.
 * Limits concurrent executions to protect system resources.</p>
 */
public class BulkheadPattern {
    private static final Map<String, ServiceBulkhead> BULKHEADS = new ConcurrentHashMap<>();

    public static ServiceBulkhead getBulkhead(String name, BulkheadConfig config) {
        return BULKHEADS.computeIfAbsent(name, k -> new ServiceBulkhead(name, config));
    }

    public static ServiceBulkhead getBulkhead(String name) {
        return getBulkhead(name, new BulkheadConfig(10, 1000));
    }

    public static void shutdownAll() {
        BULKHEADS.values().forEach(ServiceBulkhead::shutdown);
        BULKHEADS.clear();
    }
}