package com.iluwatar;

/**
 *
 * Thread-safe Singleton class.
 * The instance is lazily initialized and thus needs synchronization
 * mechanism.
 *
 */
public class ThreadSafeLazyLoadedIvoryTower {

	private static ThreadSafeLazyLoadedIvoryTower instance = null;
	
	private ThreadSafeLazyLoadedIvoryTower() {
	}

	public synchronized static ThreadSafeLazyLoadedIvoryTower getInstance() {
		/*
		 * The instance gets created only when it is called for first time.
		 * Lazy-loading
		 */
		if (instance == null) {
			instance = new ThreadSafeLazyLoadedIvoryTower();
		}

		return instance;
	}
}
