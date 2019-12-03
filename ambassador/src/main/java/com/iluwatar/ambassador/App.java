/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.ambassador;

/**
 * The ambassador pattern creates a helper service that sends network requests on behalf of a
 * client. It is often used in cloud-based applications to offload features of a remote service.
 *
 * <p>An ambassador service can be thought of as an out-of-process proxy that is co-located with
 * the client. Similar to the proxy design pattern, the ambassador service provides an interface for
 * another remote service. In addition to the interface, the ambassador provides extra functionality
 * and features, specifically offloaded common connectivity tasks. This usually consists of
 * monitoring, logging, routing, security etc. This is extremely useful in legacy applications where
 * the codebase is difficult to modify and allows for improvements in the application's networking
 * capabilities.
 *
 * <p>In this example, we will the ({@link ServiceAmbassador}) class represents the ambassador while
 * the
 * ({@link RemoteService}) class represents a remote application.
 */
public class App {

  /**
   * Entry point.
   */
  public static void main(String[] args) {
    var host1 = new Client();
    var host2 = new Client();
    host1.useService(12);
    host2.useService(73);
  }
}
