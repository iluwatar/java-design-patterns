/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
package com.iluwatar.rpi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Remote Procedure Invocation (RPI) is a communication pattern used in microservices architectures
 * where a client sends a synchronous request to a service and waits for a response. Unlike
 * message-based communication, RPI uses a direct request/reply protocol — typically REST over HTTP.
 *
 * <p>In this example, the {@link RpiService} exposes a REST endpoint that returns a greeting
 * message. The {@link RpiClient} uses Spring's {@link org.springframework.web.client.RestTemplate}
 * to make a synchronous HTTP GET call to the service and retrieve the response.
 *
 * <p>Key characteristics of RPI:
 *
 * <ul>
 *   <li>Synchronous communication — the client blocks until the response arrives.
 *   <li>Simple and familiar — leverages standard HTTP/REST protocols.
 *   <li>No intermediate broker — direct client-to-service communication.
 *   <li>Both client and service must be available at the time of the call (tight runtime coupling).
 * </ul>
 *
 * @see <a href="https://microservices.io/patterns/communication-style/rpi.html">RPI Pattern</a>
 */
@SpringBootApplication
public class App {

  /** Program entry point. */
  public static void main(String[] args) {
    var context = SpringApplication.run(App.class, args);
    if (args.length > 0 && "test".equals(args[0])) {
      context.close();
    }
  }
}