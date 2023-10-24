package com.iluwatar.implicitlock;

/**
 * Implicit locks are used in Java for synchronization.
 * Every object in Java has an implicit lock associated with it. This lock is utilized for achieving
 * thread safety by allowing only one thread to execute synchronized code blocks or methods on the object.
 * Implicit locks ensure that shared resources are accessed exclusively by one thread at a time,
 * preventing race conditions and ensuring data consistency in a multi-threaded environment.
 *
 * The WorkerThread class represents threads that access the shared resource. Each thread uses
 * the synchronized updateState() method of the SharedResource class.
 *
 * <p>In this example, two threads are created to illustrate how implicit locks (achieved with the
 * synchronized keyword) prevent conflicts, ensuring that only one thread can use the critical section
 * of code at a time.
 */
public class App {
    public static void main(String[] args) {
        // Shared resource instance
        SharedResource sharedResource = new SharedResource();

        // Create multiple threads accessing the shared resource
        Thread thread1 = new Thread(new WorkerThread(sharedResource));
        Thread thread2 = new Thread(new WorkerThread(sharedResource));

        // Start the threads
        thread1.start();
        thread2.start();

        // Wait for threads to complete
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Program completed.");
    }
}
