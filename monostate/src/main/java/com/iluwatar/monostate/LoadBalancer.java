/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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
 */

public class LoadBalancer {
  private static final List<Server> SERVERS = new ArrayList<>();
  private static int lastServedId;

  static {
    var id = 0;
    for (var port : new int[]{8080, 8081, 8082, 8083, 8084}) {
      SERVERS.add(new Server("localhost", port, ++id));
    }
  }

  /**
   * Add new server.
   */
  public final void addServer(Server server) {
    synchronized (SERVERS) {
      SERVERS.add(server);
    }

  }

  public final int getNoOfServers() {
    return SERVERS.size();
  }

  public int getLastServedId() {
    return lastServedId;
  }

  /**
   * Handle request.
   */
  public synchronized void serverRequest(Request request) {
    if (lastServedId >= SERVERS.size()) {
      lastServedId = 0;
    }
    var server = SERVERS.get(lastServedId++);
    server.serve(request);
  }

}
