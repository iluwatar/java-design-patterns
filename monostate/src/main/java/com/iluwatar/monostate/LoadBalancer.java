/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
  private static int lastServedId;

  static {
    int id = 0;
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
  public synchronized void serverRequest(Request request) {
    if (lastServedId >= servers.size()) {
      lastServedId = 0;
    }
    Server server = servers.get(lastServedId++);
    server.serve(request);
  }
  
}
