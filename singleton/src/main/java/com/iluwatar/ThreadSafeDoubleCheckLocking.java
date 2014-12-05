package com.iluwatar;

/**
 * Double check locking
 * 
 * http://www.cs.umd.edu/~pugh/java/memoryModel/DoubleCheckedLocking.html
 * 
 * Broken under Java 1.4.
 * @author mortezaadi@gmail.com
 *
 */
public class ThreadSafeDoubleCheckLocking {
	
	private static volatile ThreadSafeDoubleCheckLocking INSTANCE;

	/**
	 * private constructor to prevent client from instantiating.
	 * 
	 */
	private ThreadSafeDoubleCheckLocking() {
		//to prevent instantiating by Reflection call 
		if(INSTANCE != null)
			throw new IllegalStateException("Already initialized.");
	}
	
	public static ThreadSafeDoubleCheckLocking getInstance() {
		//local variable increases performance by 25 percent 
		//Joshua Bloch "Effective Java, Second Edition", p. 283-284
		ThreadSafeDoubleCheckLocking result = INSTANCE;
		if (result == null) {
			synchronized (ThreadSafeDoubleCheckLocking.class) {
				result = INSTANCE;
				if (result == null) {
					INSTANCE = result = new ThreadSafeDoubleCheckLocking();
				}
			}
		}
		return result;
	}
}
