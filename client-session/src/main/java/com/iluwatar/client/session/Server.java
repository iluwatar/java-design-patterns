package com.iluwatar.client.session;

import java.util.UUID;

/**
 * The Server class. The client communicates with the server and request processing and getting a new session.
 */
public class Server {
  private String host;

  private int port;

  public Server(String host, int port) {
    this.host = host;
    this.port = port;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  /**
   * Creates a new session.
   *
   * @param name name of the client
   *
   * @return Session Object
   */
  public Session getSession(String name) {
    return new Session(UUID.randomUUID().toString(), name);
  }

  /**
   * Processes a request based on the session.
   *
   * @param request Request object with data and Session
   */
  public void process(Request request) {
    System.out.println(request.getSession().getClientName());
  }

}
