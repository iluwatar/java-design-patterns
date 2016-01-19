package com.iluwatar.singleton;

/**
 * Double check locking
 * <p/>
 * http://www.cs.umd.edu/~pugh/java/memoryModel/DoubleCheckedLocking.html
 * <p/>
 * Broken under Java 1.4.
 *
 * @author mortezaadi@gmail.com
 */
public class ThreadSafeDoubleCheckLocking {

  private static volatile ThreadSafeDoubleCheckLocking instance;

  /**
   * private constructor to prevent client from instantiating.
   */
  private ThreadSafeDoubleCheckLocking() {
    // to prevent instantiating by Reflection call
    if (instance != null) {
      throw new IllegalStateException("Already initialized.");
    }
  }

  /**
   * Public accessor.
   *
   * @return an instance of the class.
   */
  public static ThreadSafeDoubleCheckLocking getInstance() {
    // local variable increases performance by 25 percent
    // Joshua Bloch "Effective Java, Second Edition", p. 283-284
    ThreadSafeDoubleCheckLocking result = instance;
    if (result == null) {
      synchronized (ThreadSafeDoubleCheckLocking.class) {
        result = instance;
        if (result == null) {
          instance = result = new ThreadSafeDoubleCheckLocking();
        }
      }
    }
    return result;
  }
}
