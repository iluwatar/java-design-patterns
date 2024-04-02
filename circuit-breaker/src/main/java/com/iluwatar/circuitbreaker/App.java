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
package com.iluwatar.circuitbreaker;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * The intention of the Circuit Builder pattern is to handle remote failures robustly, which is to
 * mean that if a service is dependent on n number of other services, and m of them fail, we should
 * be able to recover from that failure by ensuring that the user can still use the services that
 * are actually functional, and resources are not tied up by uselessly by the services which are not
 * working. However, we should also be able to detect when any of the m failing services become
 * operational again, so that we can use it
 * </p>
 * <p>
 * In this example, the circuit breaker pattern is demonstrated by using three services: {@link
 * DelayedRemoteService}, {@link QuickRemoteService} and {@link MonitoringService}. The monitoring
 * service is responsible for calling three services: a local service, a quick remove service
 * {@link QuickRemoteService} and a delayed remote service {@link DelayedRemoteService} , and by
 * using the circuit breaker construction we ensure that if the call to remote service is going to
 * fail, we are going to save our resources and not make the function call at all, by wrapping our
 * call to the remote services in the {@link DefaultCircuitBreaker} implementation object.
 * </p>
 * <p>
 * This works as follows: The {@link DefaultCircuitBreaker} object can be in one of three states:
 * <b>Open</b>, <b>Closed</b> and <b>Half-Open</b>, which represents the real world circuits. If
 * the state is closed (initial), we assume everything is alright and perform the function call.
 * However, every time the call fails, we note it and once it crosses a threshold, we set the state
 * to Open, preventing any further calls to the remote server. Then, after a certain retry period
 * (during which we expect thee service to recover), we make another call to the remote server and
 * this state is called the Half-Open state, where it stays till the service is down, and once it
 * recovers, it goes back to the closed state and the cycle continues.
 * </p>
 */
@Slf4j
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {

    var serverStartTime = System.nanoTime();

    var delayedService = new DelayedRemoteService(serverStartTime, 5);
    var delayedServiceCircuitBreaker = new DefaultCircuitBreaker(delayedService, 3000, 2,
        2000 * 1000 * 1000);

    var quickService = new QuickRemoteService();
    var quickServiceCircuitBreaker = new DefaultCircuitBreaker(quickService, 3000, 2,
        2000 * 1000 * 1000);

    //Create an object of monitoring service which makes both local and remote calls
    var monitoringService = new MonitoringService(delayedServiceCircuitBreaker,
        quickServiceCircuitBreaker);

    //Fetch response from local resource
    LOGGER.info(monitoringService.localResourceResponse());

    //Fetch response from delayed service 2 times, to meet the failure threshold
    LOGGER.info(monitoringService.delayedServiceResponse());
    LOGGER.info(monitoringService.delayedServiceResponse());

    //Fetch current state of delayed service circuit breaker after crossing failure threshold limit
    //which is OPEN now
    LOGGER.info(delayedServiceCircuitBreaker.getState());

    //Meanwhile, the delayed service is down, fetch response from the healthy quick service
    LOGGER.info(monitoringService.quickServiceResponse());
    LOGGER.info(quickServiceCircuitBreaker.getState());

    //Wait for the delayed service to become responsive
    try {
      LOGGER.info("Waiting for delayed service to become responsive");
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      LOGGER.error("An error occurred: ", e);
    }
    //Check the state of delayed circuit breaker, should be HALF_OPEN
    LOGGER.info(delayedServiceCircuitBreaker.getState());

    //Fetch response from delayed service, which should be healthy by now
    LOGGER.info(monitoringService.delayedServiceResponse());
    //As successful response is fetched, it should be CLOSED again.
    LOGGER.info(delayedServiceCircuitBreaker.getState());
  }
}
