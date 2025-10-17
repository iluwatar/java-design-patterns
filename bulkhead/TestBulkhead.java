import java.util.concurrent.CompletableFuture;

public class TestBulkhead {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Testing Bulkhead Pattern...");
        
        // Test 1: Basic functionality
        BulkheadConfig config = new BulkheadConfig(2, 1000);
        ServiceBulkhead bulkhead = BulkheadPattern.getBulkhead("TestService", config);
        
        // Execute a simple task
        CompletableFuture<String> future = bulkhead.execute(() -> {
            Thread.sleep(500);
            return "Success!";
        });
        
        future.thenAccept(result -> System.out.println("Result: " + result));
        
        Thread.sleep(1000);
        System.out.println("Metrics: " + bulkhead.getMetrics());
        
        bulkhead.shutdown();
        System.out.println("✅ Basic test passed!");
    }
}