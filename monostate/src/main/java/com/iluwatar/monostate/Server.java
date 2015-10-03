package com.iluwatar.monostate;

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
  public final void serve(Request request) {
    System.out.println("Server ID "+id + "  processed request with value  "+request.value);
  }
}
