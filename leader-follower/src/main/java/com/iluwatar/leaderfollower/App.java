package com.iluwatar.leaderfollower;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 
 * Leader Follower is a concurrency pattern where multiple threads can efficiently de-multiplex events and dispatch to
 * event handlers. One may think of it as a taxi station at night, where all the drivers are sleeping except for one,
 * the leader. The ThreadPool is a station managing many threads - cabs.
 * 
 * The leader is waiting for an IO event on the HandleSet, so as a driver waits for a client.
 * 
 * When a client arrives (in the form of a Handle identifying the IO event), the leader driver wakes up another driver
 * to be the next leader and serves the request from his passenger.
 * 
 * While he is taking the client to the given address (calling ConcreteEventHandler and handing over Handle to it) the
 * next leader can concurrently serve another passenger.
 * 
 * When a driver finishes he take his taxi back to the station and falls asleep if the station is not empty. Otherwise
 * he become the leader
 * 
 * 
 * <p>
 * In this example we use ThreadPool which basically acts as the ThreadPool. One of the Workers becomes Leader and
 * listens on the {@link HandleSet} for work. {@link HandleSet} basically acts as the source of input events for the
 * {@link Worker}, who are spawned and controlled by the {@link WorkStation} . When {@link Work} arrives which
 * implements the {@link Handle} interface then the leader takes the work and calls the {@link ConcreteEventHandler}.
 * However it also selects one of the waiting Workers as leader, who can then process the next work and so on.
 * 
 * The pros for this pattern are:
 * 
 * It enhances CPU cache affinity and eliminates unbound allocation and data buffer sharing between threads by reading
 * the request into buffer space allocated on the stack of the leader or by using the Thread-Specific Storage pattern
 * [22] to allocate memory.
 * 
 * It minimizes locking overhead by not exchanging data between threads, thereby reducing thread synchronization. In
 * bound handle/thread associations, the leader thread dispatches the event based on the I/O handle.
 * 
 * It can minimize priority inversion because no extra queuing is introduced in the server
 * 
 * It does not require a context switch to handle each event, reducing the event dispatching latency. Note that
 * promoting a follower thread to fulfill the leader role requires a context switch.
 * 
 * Programming simplicity: The Leader/Follower pattern simplifies the programming of concurrency models where multiple
 * threads can receive requests, process responses, and demultiplex connections using a shared handle set.
 * 
 * 
 * 
 * The cons are: complex, network IO can be a bottleneck, Lack of flexibility
 * </p>
 *
 * 
 */
public class App {

  public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
    ExecutorService exec = Executors.newFixedThreadPool(4);
    WorkStation station = new WorkStation(exec);
    station.startWork();

    exec.awaitTermination(10, TimeUnit.SECONDS);
    exec.shutdownNow();
  }
}
