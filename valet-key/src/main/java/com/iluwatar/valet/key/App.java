package com.iluwatar.valet.key;

public class App {
  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    Application application = new Application();
    Resource resource = new Resource();
    User user = new User(null, application, resource);
    user.requestResource(true, 1);
  }
}
