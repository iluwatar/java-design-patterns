package com.iluwatar;

public class SingletonClass {

	private static SingletonClass singletonInstance = null;

	public synchronized static SingletonClass getSingleton() {
		/*
		 * The instance gets created only when it is called for first time.
		 * Lazy-loading
		 */
		if (singletonInstance == null) {
			singletonInstance = new SingletonClass();
		}

		return singletonInstance;
	}
}
