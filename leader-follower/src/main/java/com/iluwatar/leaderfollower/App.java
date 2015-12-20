package com.iluwatar.leaderfollower;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 
 * Leader Follower is a concurrency pattern where multiple threads can efficiently demultiplex
 * events and dispatch to event handlers. One may think of it as a taxi station at night, where all
 * the drivers are sleeping except for one, the leader. The ThreadPool is a station managing many
 * threads - cabs.
 * 
 * The leader is waiting for an IO event on the HandleSet, so as a driver waits for a client.
 * 
 * When a client arrives (in the form of a Handle identifying the IO event), the leader driver wakes
 * up another driver to be the next leader and serves the request from his passenger.
 * 
 * While he is taking the client to the given address (calling ConcreteEventHandler and handing over
 * Handle to it) the next leader can concurrently serve another passenger.
 * 
 * When a driver finishes he take his taxi back to the station and falls asleep if the station is
 * not empty. Otherwise he become the leader
 * 
 * 
 * <p>
 * In this example we use ThreadPool which basically acts as the ThreadPool. One of the Workers
 * becomes Leader and listens on the {@link HandleSet} for work. HandleSet basically acts as the
 * source of input events for the Workers, who are spawned and controlled by the {@link WorkStation}
 * . When Work arrives which implements the {@link Handle} interface then the leader takes the work
 * and calls the {@link ConcreteEventHandler}. However it also selects one of the waiting Workers as
 * leader, who can then process the next work and so on.
 * 
 * The pros for this pattern are:
 * 
 * no communication between the threads are necessary, no synchronization, nor shared memory (no
 * locks, mutexes) are needed. more ConcreteEventHandlers can be added without affecting any other
 * EventHandler minimizes the latency because of the multiple threads
 * 
 * The cons are:
 * 
 * complex network IO can be a bottleneck
 *
 * 
 */
public class App {

  public static void main(String[] args)
      throws IOException, ClassNotFoundException, InterruptedException {
    ExecutorService exec = Executors.newFixedThreadPool(4);
    WorkStation station = new WorkStation(exec);
    station.startWork();

    exec.awaitTermination(10, TimeUnit.SECONDS);
    exec.shutdownNow();
  }
}
