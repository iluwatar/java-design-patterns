package com.iluwatar.monostate;

/**
 * 
 * The Server class. Each Server sits behind a LoadBalancer which delegates the call to the servers
 * in a simplistic Round Robin fashion.
 *
 */
public class Server {
  public final String host;
  public final int port;
  public final int id;

  public Server(String host, int port, int id) {
    this.host = host;
    this.port = port;
    this.id = id;
  }

  public String getHost() {
    return host;
  }

  public int getPort() {
    return port;
  }

  public void serve(Request request) {
    System.out.println("Server ID " + id + " associated to host : " + getHost() + " and Port "
        + getPort() + " Processed request with value  " + request.value);
  }
}
