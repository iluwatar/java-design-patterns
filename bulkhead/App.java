import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Demonstrates Bulkhead Pattern
 */
public class App {
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Bulkhead Pattern Demo ===");
        
        // Create bulkhead with only 2 concurrent calls
        BulkheadConfig config = new BulkheadConfig(2, 1000);
        ServiceBulkhead bulkhead = BulkheadPattern.getBulkhead("PaymentService", config);
        
        // Simulate multiple service calls
        List<CompletableFuture<String>> futures = new ArrayList<>();
        
        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            CompletableFuture<String> future = bulkhead.execute(() -> {
                System.out.println("Task " + taskId + " started - Available permits: " + 
                    bulkhead.getAvailablePermits());
                Thread.sleep(2000); // Simulate work
                System.out.println("Task " + taskId + " completed");
                return "Result-" + taskId;
            });
            
            futures.add(future);
        }
        
        // Wait for completion
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        System.out.println("\nFinal metrics: " + bulkhead.getMetrics());
        
        BulkheadPattern.shutdownAll();
    }
}