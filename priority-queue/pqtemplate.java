import java.util.PriorityQueue;

public class PriorityQueueExample {
    public static void main(String[] args) {
        // Create a priority queue of integers
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

        // Adding elements to the priority queue
        priorityQueue.add(5);
        priorityQueue.add(3);
        priorityQueue.add(8);
        priorityQueue.add(1);
        priorityQueue.add(4);

        // Printing the elements of the priority queue
        System.out.println("Priority Queue Elements: " + priorityQueue);

        // Removing elements from the priority queue (min-heap order)
        while (!priorityQueue.isEmpty()) {
            int element = priorityQueue.poll();
            System.out.println("Removed: " + element);
        }
    }
}
