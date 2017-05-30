/**
 * The MIT License Copyright (c) 2014 Ilkka Seppälä
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.iluwatar.leaderfollower;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The Leader/Followers architectural pattern provides an efficient concurrency model where multiple threads take turns
 * sharing a set of event sources in order to detect, demultiplex, dispatch, and process service requests that occur on
 * these event sources.
 * <p>
 * Structure a pool of threads to share a set of event sources efficiently by taking turns demultiplexing events that
 * arrive on these event sources and synchronously dispatching the events to application services that process them.
 * In detail: design a thread pool mechanism that allows multiple threads to coordinate themselves and protect critical
 * sections while detecting, demultiplexing, dispatching, and processing events. In this mechanism, allow one thread at
 * a time the leader to wait for an event to occur from a set of event sources. Meanwhile, other threads the followers
 * can queue up waiting their turn to become the leader. After the current leader thread detects an event from the event
 * source set, it first promotes a follower * thread to become the new leader. It then plays the role of a processing
 * thread, which demultiplexes and dispatches the event to a designated event handler that performs application-specific
 * event handling in the processing thread. Multiple processing threads can handle events concurrently while the current
 * leader thread waits for new events on the set of event sources shared by the threads. After handling its event, a
 * processing thread reverts to a follower role and waits to become the leader thread again.
 * <p>
 * <p>
 * The Leader/Followers pattern provides the following benefits:
 * <p>
 * 1)Performance enhancements. Compared with the Half-Sync/Half-Reactive thread pool approach described in the Example
 * section, the Leader/Followers pattern can improve performance as follows:
 * <p>
 * • It enhances CPU cache affinity and eliminates the need for dynamic allocation and data buffer sharing between
 * threads. For example, a processing thread can read the request into buffer space allocated on its run-time stack or
 * by using the ThreadSpecific Storage pattern to allocate memory.
 * <p>
 * • It minimizes locking overhead by not exchanging data between threads, thereby reducing thread synchronization.
 * In bound handle/thread associations, the leader thread demultiplexes the event to its event handler based on the
 * value of the handle. The request event is then read from the handle by the follower thread processing the event.
 * In unbound associations, the leader thread itself reads the request event from the handle and processes it.
 * <p>
 * • It can minimize priority inversion because no extra queueing is introduced in the server. When combined with
 * real-time I/O subsystems, the Leader/Followers thread pool model can reduce sources of non-determinism in server
 * request processing significantly.
 * <p>
 * • It does not require a context switch to handle each event, reducing the event dispatching latency. Note that
 * promoting a follower thread to fulfill the leader role does require a context switch. If two events arrive
 * simultaneously this increases the dispatching latency for the second event, but the performance is no worse than
 * Half-Sync/Half-Reactive thread pool implementations.
 * <p>
 * 2)Programming simplicity. The Leader/Follower pattern simplifies the programming of concurrency models where multiple
 * threads can receive requests, process responses, and demultiplex connections using a shared handle set.
 * <p>
 * However, the Leader/Followers pattern has the following liabilities:
 * <p>
 * 1)Implementation complexity. The advanced variants of the Leader/Followers pattern are harder to implement than
 * Half-Sync/Half-Reactive thread pools. In particular, when used as a multi-threaded connection multiplexer,
 * the Leader/Followers pattern must maintain a pool of follower threads waiting to process requests. This set must
 * be updated when a follower thread is promoted to a leader and when a thread rejoins the pool of follower threads.
 * All these operations can happen concurrently, in an unpredictable order. Thus, the Leader/Follower pattern
 * implementation must be efficient, while ensuring operation atomicity.
 * <p>
 * 2)Lack of flexibility. Thread pool models based on the Half-Sync/Half-Reactive variant of the Half-Sync/Half-Async
 * pattern allow events in the queueing layer to be discarded or re-prioritized. Similarly, the system can maintain
 * multiple separate queues serviced by threads at different priorities to reduce contention and priority inversion
 * between events at different priorities. In the Leader/Followers model, however, it is harder to discard or reorder
 * events because there is no explicit queue. One way to provide this functionality is to offer different levels of
 * service by using multiple Leader/Followers groups in the application, each one serviced by threads at different
 * priorities.
 * <p>
 * 3)Network I/O bottlenecks. The Leader/Followers pattern, as described in the Implementation section, serializes
 * processing by allowing only a single thread at a time to wait on the handle set. In some environments, this design
 * could become a bottleneck because only one thread at a time can demultiplex I/O events. In practice, however, this
 * may not be a problem because most of the I/O-intensive processing is performed by the operating system kernel. Thus,
 * application-level I/O operations can be performed rapidly.
 */

public class App {
  /**
   * Main method that starts the leader/followers pattern based application
   */
  public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

    ExecutorService executorService = Executors.newFixedThreadPool(5);
    ThreadPool pool = new ThreadPool(executorService, 20, 5);
    pool.start();
    executorService.awaitTermination(10000, TimeUnit.MILLISECONDS);
    executorService.shutdownNow();

  }
}