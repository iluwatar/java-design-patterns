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

package com.iluwatar.circuitbreaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * The intention of the Circuit Builder pattern is to handle remote failures robustly, which is to
 * mean that if a service is dependant on n number of other services, and m of them fail, we should
 * be able to recover from that failure by ensuring that the user can still use the services that
 * are actually functional, and resources are not tied up by uselessly by the services which are not
 * working. However, we should also be able to detect when any of the m failing services become
 * operational again, so that we can use it
 * </p>
 * <p>
 * In this example, the circuit breaker pattern is demonstrated by using two services: {@link
 * MonitoringService} and {@link DelayedService}. The monitoring service is responsible for calling
 * two services: a local service and a remote service {@link DelayedService} , and by using the
 * circuit breaker construction we ensure that if the call to remote service is going to fail, we
 * are going to save our resources and not make the function call at all, by wrapping our call to
 * the remote service in the circuit breaker object.
 * </p>
 * <p>
 * This works as follows: The {@link CircuitBreaker} object can be in one of three states:
 * <b>Open</b>, <b>Closed</b> and <b>Half-Open</b>, which represents the real world circuits. If the
 * state is closed (initial), we assume everything is alright and perform the function call.
 * However, every time the call fails, we note it and once it crosses a threshold, we set the state
 * to Open, preventing any further calls to the remote server. Then, after a certain retry period
 * (during which we expect thee service to recover), we make another call to the remote server and
 * this state is called the Half-Open state, where it stays till the service is down, and once it
 * recovers, it goes back to the closed state and the cycle continues.
 * </p>
 */
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  @SuppressWarnings("squid:S2189")
  public static void main(String[] args) {
    //Create an object of monitoring service which makes both local and remote calls
    var obj = new MonitoringService();
    //Set the circuit Breaker parameters
    var circuitBreaker = new CircuitBreaker(3000, 1, 2000 * 1000 * 1000);
    var serverStartTime = System.nanoTime();
    while (true) {
      LOGGER.info(obj.localResourceResponse());
      LOGGER.info(obj.remoteResourceResponse(circuitBreaker, serverStartTime));
      LOGGER.info(circuitBreaker.getState());
      try {
        Thread.sleep(5 * 1000);
      } catch (InterruptedException e) {
        LOGGER.error(e.getMessage());
      }
    }
  }
}
