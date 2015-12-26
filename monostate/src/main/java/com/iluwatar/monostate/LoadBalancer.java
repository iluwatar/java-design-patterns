package com.iluwatar.monostate;

import java.util.ArrayList;
import java.util.List;

/**
 * The LoadBalancer class. This implements the MonoState pattern. It holds a series of servers. Upon
 * receiving a new Request, it delegates the call to the servers in a Round Robin Fashion. Since all
 * instances of the class share the same state, all instances will delegate to the same server on
 * receiving a new Request.
 * 
 */

public class LoadBalancer {
  private static List<Server> servers = new ArrayList<>();
  private static int id = 0;
  private static int lastServedId = 0;

  static {
    servers.add(new Server("localhost", 8081, ++id));
    servers.add(new Server("localhost", 8080, ++id));
    servers.add(new Server("localhost", 8082, ++id));
    servers.add(new Server("localhost", 8083, ++id));
    servers.add(new Server("localhost", 8084, ++id));
  }

  /**
   * Add new server
   */
  public final void addServer(Server server) {
    synchronized (servers) {
      servers.add(server);
    }

  }

  public final int getNoOfServers() {
    return servers.size();
  }

  public static int getLastServedId() {
    return lastServedId;
  }

  /**
   * Handle request
   */
  public void serverRequest(Request request) {
    if (lastServedId >= servers.size()) {
      lastServedId = 0;
    }
    Server server = servers.get(lastServedId++);
    server.serve(request);
  }



}
