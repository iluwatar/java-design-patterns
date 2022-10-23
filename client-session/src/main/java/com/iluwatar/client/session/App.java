package com.iluwatar.client.session;

public class App {

  /**
   * Program entry point.
   *
   * @param args Command line args
   */
  public static void main(String[] args) {
    Server server = new Server("localhost", 8080);
    Session session1 = server.getSession("Session1");
    Session session2 = server.getSession("Session2");
    Request request1 = new Request("Data1", session1);
    Request request2 = new Request("Data2", session2);
    server.process(request1);
    server.process(request2);
  }
}
