package com.iluwatar.singleton;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

/**
 * This class provides several test case that test singleton construction.
 *
 * The first proves that multiple calls to the singleton getInstance object are the same when called
 * in the SAME thread. The second proves that multiple calls to the singleton getInstance object are
 * the same when called in the DIFFERENT thread.
 *
 */
public class LazyLoadedSingletonThreadSafetyTest {

  private static final int NUM_THREADS = 5;
  private List<ThreadSafeLazyLoadedIvoryTower> threadObjects = Collections
      .synchronizedList(new ArrayList<>());

  // NullObject class so Callable has to return something
  private class NullObject {
    private NullObject() {}
  }

  @Test
  public void testMultipleCallsReturnTheSameObjectInSameThread() {
    // Create several instances in the same calling thread
    ThreadSafeLazyLoadedIvoryTower instance1 = ThreadSafeLazyLoadedIvoryTower.getInstance();
    ThreadSafeLazyLoadedIvoryTower instance2 = ThreadSafeLazyLoadedIvoryTower.getInstance();
    ThreadSafeLazyLoadedIvoryTower instance3 = ThreadSafeLazyLoadedIvoryTower.getInstance();
    // now check they are equal
    assertEquals(instance1, instance1);
    assertEquals(instance1, instance2);
    assertEquals(instance2, instance3);
    assertEquals(instance1, instance3);
  }

  @Test
  public void testMultipleCallsReturnTheSameObjectInDifferentThreads()
      throws InterruptedException, ExecutionException {
    {
      // create several threads and inside each callable instantiate the singleton class
      ExecutorService executorService = Executors.newSingleThreadExecutor();

      List<Callable<NullObject>> threadList = new ArrayList<>();
      for (int i = 0; i < NUM_THREADS; i++) {
        threadList.add(new SingletonCreatingThread());
      }

      ExecutorService service = Executors.newCachedThreadPool();
      List<Future<NullObject>> results = service.invokeAll(threadList);

      // wait for all of the threads to complete
      for (Future res : results) {
        res.get();
      }

      // tidy up the executor
      executorService.shutdown();
    }
    {
      // now check the contents that were added to threadObjects by each thread
      assertEquals(NUM_THREADS, threadObjects.size());
      assertEquals(threadObjects.get(0), threadObjects.get(1));
      assertEquals(threadObjects.get(1), threadObjects.get(2));
      assertEquals(threadObjects.get(2), threadObjects.get(3));
      assertEquals(threadObjects.get(3), threadObjects.get(4));
    }
  }

  private class SingletonCreatingThread implements Callable<NullObject> {
    @Override
    public NullObject call() {
      // instantiate the thread safety class and add to list to test afterwards
      ThreadSafeLazyLoadedIvoryTower instance = ThreadSafeLazyLoadedIvoryTower.getInstance();
      threadObjects.add(instance);
      return new NullObject();// return null object (cannot return Void)
    }
  }
}
