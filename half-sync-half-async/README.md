---
title: Half-Sync/Half-Async
category: Concurrency
language: en
tag:
 - Performance
---

## Intent
The Half-Sync/Half-Async pattern decouples synchronous I/O from
asynchronous I/O in a system to simplify concurrent programming effort without
degrading execution efficiency.

## Class diagram
![Half-Sync/Half-Async class diagram](./etc/half-sync-half-async.png)

## Applicability
Use Half-Sync/Half-Async pattern when

* a system possesses following characteristics:
  * the system must perform tasks in response to external events that occur asynchronously, like hardware interrupts in OS
  * it is inefficient to dedicate separate thread of control to perform synchronous I/O for each external source of event
  * the higher level tasks in the system can be simplified significantly if I/O is performed synchronously.
* one or more tasks in a system must run in a single thread of control, while other tasks may benefit from multi-threading.

## Explanation
The Half-Sync/Half-Async pattern is a concurrency design pattern used in software engineering to manage concurrent operations and interactions in a system. It's particularly useful in applications where both asynchronous and synchronous processing are required to achieve optimal performance and responsiveness.

The pattern is structured into two components: the synchronous part (Half-Sync) and the asynchronous part (Half-Async).

Half-Sync (Synchronous Part):
In this part, there is a synchronous layer that handles high-level control flow and coordination of the application. It typically consists of a set of threads that handle communication, synchronization, and coordination. These threads are responsible for managing shared resources and orchestrating the flow of work in a synchronous manner.

Half-Async (Asynchronous Part):
The asynchronous part involves a set of asynchronous workers or threads that perform the actual processing and execution of tasks. These threads are responsible for carrying out time-consuming or potentially blocking operations asynchronously. They handle I/O operations, long-running computations, or any task that can be parallelized effectively.

How it Works:

Synchronous Part:

The synchronous part handles incoming requests, organizes the tasks to be performed, and dispatches them to the asynchronous workers.
It's responsible for high-level orchestration, resource management, and synchronization of the overall system.
Asynchronous Part:

Asynchronous workers execute the tasks independently and concurrently without blocking the main application.
They handle time-consuming or I/O-bound tasks efficiently, ensuring the system remains responsive.
Advantages:

Responsiveness: Asynchronous processing ensures that the system remains responsive by allowing non-blocking operations.
Parallel Execution: It enables concurrent execution of tasks, improving performance by utilizing multiple threads or processes.
Scalability: The asynchronous part can be scaled easily to handle a larger number of requests efficiently.
Disadvantages:

Complexity: Implementing and managing the two parts can be complex, requiring careful coordination and synchronization mechanisms.
Potential Deadlocks: The interplay between the synchronous and asynchronous parts can introduce potential deadlocks or race conditions if not handled correctly.
The Half-Sync/Half-Async pattern strikes a balance between responsiveness and efficiency by utilizing both synchronous and asynchronous processing to optimize the performance of a system while ensuring responsiveness to user requests.

## Programmatic Example
```java
{
import java.util.LinkedList;
import java.util.Queue;

class TaskQueue {
    private Queue<String> queue = new LinkedList<>();

    synchronized void enqueue(String task) {
        queue.add(task);
        notify(); // Notify waiting threads that a task is available
    }

    synchronized String dequeue() throws InterruptedException {
        while (queue.isEmpty()) {
            wait(); // Wait until a task is enqueued
        }
        return queue.poll();
    }
}

class SynchronousPart extends Thread {
    private TaskQueue taskQueue;

    public SynchronousPart(TaskQueue taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String task = taskQueue.dequeue();
                System.out.println("Synchronous part processing task: " + task);
                // Simulate some synchronous processing
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class AsynchronousPart extends Thread {
    @Override
    public void run() {
        while (true) {
            // Simulate some asynchronous processing
            System.out.println("Asynchronous part processing tasks...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

public class HalfSyncHalfAsyncExample {
    public static void main(String[] args) {
        TaskQueue taskQueue = new TaskQueue();

        SynchronousPart synchronousThread = new SynchronousPart(taskQueue);
        AsynchronousPart asynchronousThread = new AsynchronousPart();

        synchronousThread.start();
        asynchronousThread.start();

        // Enqueue tasks
        taskQueue.enqueue("Task 1");
        taskQueue.enqueue("Task 2");
        taskQueue.enqueue("Task 3");

        // Allow the threads to run for a while
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Stop the threads (in a real application, you would have a proper way to signal the threads to stop)
        synchronousThread.interrupt();
        asynchronousThread.interrupt();
    }
}
 ```
### In this Java example:

* TaskQueue class manages the task queue and provides methods for enqueueing and dequeuing tasks in a thread-safe manner.
* SynchronousPart and AsynchronousPart classes represent the synchronous and asynchronous parts of the application, respectively.
* The synchronous part processes tasks in a synchronous manner, and the asynchronous part processes tasks in an asynchronous manner, simulating some processing time.
* The main program creates instances of TaskQueue, SynchronousPart, and AsynchronousPart, enqueues tasks, and starts the threads to demonstrate the Half-Sync/Half-Async pattern.


## Real world examples

* [BSD Unix networking subsystem](https://www.dre.vanderbilt.edu/~schmidt/PDF/PLoP-95.pdf)
* [Real Time CORBA](http://www.omg.org/news/meetings/workshops/presentations/realtime2001/4-3_Pyarali_thread-pool.pdf)
* [Android AsyncTask framework](https://developer.android.com/reference/android/os/AsyncTask)

## Credits

* [Douglas C. Schmidt and Charles D. Cranor - Half Sync/Half Async](https://www.dre.vanderbilt.edu/~schmidt/PDF/PLoP-95.pdf)
* [Pattern Oriented Software Architecture Volume 2: Patterns for Concurrent and Networked Objects](https://www.amazon.com/gp/product/0471606952/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0471606952&linkCode=as2&tag=javadesignpat-20&linkId=889e4af72dca8261129bf14935e0f8dc)
