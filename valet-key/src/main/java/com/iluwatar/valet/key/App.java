package com.iluwatar.valet.key;

public final class App {
  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    var application = new Application();
    var resource = new Resource();
    var user = new User(null, application, resource);
    user.requestResource(true, 1);
  }
}
