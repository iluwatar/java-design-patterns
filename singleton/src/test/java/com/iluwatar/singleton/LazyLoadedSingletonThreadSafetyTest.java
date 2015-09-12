package com.iluwatar.singleton;

import org.junit.Test;

/**
 * 
 * This test case demonstrates thread safety issues of lazy loaded Singleton implementation.
 * <p>
 * Out of the box you should see the test output something like the following:
 * <p>
 * Thread=Thread-4 got instance=com.iluwatar.singleton.LazyLoadedSingletonThreadSafetyTest$LazyLoadedIvoryTower@6fde356e
 * Thread=Thread-2 creating instance=com.iluwatar.singleton.LazyLoadedSingletonThreadSafetyTest$LazyLoadedIvoryTower@6fde356e
 * Thread=Thread-0 creating instance=com.iluwatar.singleton.LazyLoadedSingletonThreadSafetyTest$LazyLoadedIvoryTower@6fde356e
 * Thread=Thread-0 got instance=com.iluwatar.singleton.LazyLoadedSingletonThreadSafetyTest$LazyLoadedIvoryTower@6fde356e
 * Thread=Thread-3 got instance=com.iluwatar.singleton.LazyLoadedSingletonThreadSafetyTest$LazyLoadedIvoryTower@6fde356e
 * Thread=Thread-1 got instance=com.iluwatar.singleton.LazyLoadedSingletonThreadSafetyTest$LazyLoadedIvoryTower@60f330b0
 * Thread=Thread-2 got instance=com.iluwatar.singleton.LazyLoadedSingletonThreadSafetyTest$LazyLoadedIvoryTower@6fde356e
 * <p>
 * By changing the method signature of LazyLoadedIvoryTower#getInstance from
 * <p><blockquote><pre>
 * 	 public static LazyLoadedIvoryTower getInstance()
 * </pre></blockquote><p>
 * into
 * <p><blockquote><pre>
 *   public synchronized static LazyLoadedIvoryTower getInstance()
 * </pre></blockquote><p>
 * you should see the test output change to something like the following:
 * <p>
 * Thread=Thread-4 creating instance=com.iluwatar.singleton.LazyLoadedSingletonThreadSafetyTest$LazyLoadedIvoryTower@3c688490
 * Thread=Thread-4 got instance=com.iluwatar.singleton.LazyLoadedSingletonThreadSafetyTest$LazyLoadedIvoryTower@3c688490
 * Thread=Thread-0 got instance=com.iluwatar.singleton.LazyLoadedSingletonThreadSafetyTest$LazyLoadedIvoryTower@3c688490
 * Thread=Thread-3 got instance=com.iluwatar.singleton.LazyLoadedSingletonThreadSafetyTest$LazyLoadedIvoryTower@3c688490
 * Thread=Thread-2 got instance=com.iluwatar.singleton.LazyLoadedSingletonThreadSafetyTest$LazyLoadedIvoryTower@3c688490
 * Thread=Thread-1 got instance=com.iluwatar.singleton.LazyLoadedSingletonThreadSafetyTest$LazyLoadedIvoryTower@3c688490
 *
 */
public class LazyLoadedSingletonThreadSafetyTest {

	private static final int NUM_THREADS = 5;
	
	@Test
	public void test() {
		SingletonThread runnable = new SingletonThread();
		for (int j=0; j<NUM_THREADS; j++) {
			Thread thread = new Thread(runnable);
			thread.start();
		}
	}
	
	private static class SingletonThread implements Runnable {

		@Override
		public void run() {
			LazyLoadedIvoryTower instance = LazyLoadedIvoryTower.getInstance();
			System.out.println("Thread=" + Thread.currentThread().getName() + " got instance=" + instance);
		}
	}
	
	private static class LazyLoadedIvoryTower {

		private static LazyLoadedIvoryTower instance = null;
		
		private LazyLoadedIvoryTower() {
		}

		public static LazyLoadedIvoryTower getInstance() {
			if (instance == null) {
				instance = new LazyLoadedIvoryTower();
				System.out.println("Thread=" + Thread.currentThread().getName() + " creating instance=" + instance);
			}
			return instance;
		}
	}
}
