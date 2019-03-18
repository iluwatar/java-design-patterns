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



/**
 * 
 * The MonoState pattern ensures that all instances of the class will have the same state. This can
 * be used a direct replacement of the Singleton pattern.
 * 
 * <p>
 * In the following example, The {@link LoadBalancer} class represents the app's logic. It contains
 * a series of Servers, which can handle requests of type {@link Request}. Two instances of
 * LoadBalacer are created. When a request is made to a server via the first LoadBalancer the state
 * change in the first load balancer affects the second. So if the first LoadBalancer selects the
 * Server 1, the second LoadBalancer on a new request will select the Second server. If a third
 * LoadBalancer is created and a new request is made to it, then it will select the third server as
 * the second load balancer has already selected the second server.
 * <p>
 * .
 * 
 */
public class App {
  /**
   * Program entry point
   * 
   * @param args command line args
   */
  public static void main(String[] args) {
    LoadBalancer loadBalancer1 = new LoadBalancer();
    LoadBalancer loadBalancer2 = new LoadBalancer();
    loadBalancer1.serverRequest(new Request("Hello"));
    loadBalancer2.serverRequest(new Request("Hello World"));
  }

}
