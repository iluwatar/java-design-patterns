package com.iluwatar.callback;

/**
 *
 * This example generates the exact same output as {@link App} however the callback has been
 * defined as a Lambdas expression.
 *
 */
public class LambdasApp {

  /**
   * Program entry point
   */
  public static void main(String[] args){
    Task task = new SimpleTask();
    Callback c = () -> System.out.println("I'm done now.");
    task.executeWith(c);
  }
}
