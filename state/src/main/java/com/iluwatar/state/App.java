package com.iluwatar.state;

/**
 * 
 * In State pattern the container object has an internal state object that defines the current
 * behavior. The state object can be changed to alter the behavior.
 * <p>
 * This can be a cleaner way for an object to change its behavior at runtime without resorting to
 * large monolithic conditional statements and thus improves maintainability.
 * <p>
 * In this example the {@link Mammoth} changes its behavior as time passes by.
 * 
 */
public class App {

  /**
   * Program entry point
   */
  public static void main(String[] args) {

    Mammoth mammoth = new Mammoth();
    mammoth.observe();
    mammoth.timePasses();
    mammoth.observe();
    mammoth.timePasses();
    mammoth.observe();

  }
}
