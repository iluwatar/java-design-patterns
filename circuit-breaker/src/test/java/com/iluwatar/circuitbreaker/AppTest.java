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

package com.iluwatar.circuitbreaker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * App Test showing usage of circuit breaker.
 */
public class AppTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(AppTest.class);

  //Startup delay for delayed service (in seconds)
  private static final int STARTUP_DELAY = 4;

  //Number of failed requests for circuit breaker to open
  private static final int FAILURE_THRESHOLD = 1;

  //Time period in seconds for circuit breaker to retry service
  private static final int RETRY_PERIOD = 2;

  private MonitoringService monitoringService;

  private CircuitBreaker delayedServiceCircuitBreaker;

  private CircuitBreaker quickServiceCircuitBreaker;

  /**
   * Setup the circuit breakers and services, where {@link DelayedRemoteService} will be start with
   * a delay of 4 seconds and a {@link QuickRemoteService} responding healthy. Both services are
   * wrapped in a {@link DefaultCircuitBreaker} implementation with failure threshold of 1 failure
   * and retry time period of 2 seconds.
   */
  @BeforeEach
  public void setupCircuitBreakers() {
    var delayedService = new DelayedRemoteService(System.nanoTime(), STARTUP_DELAY);
    //Set the circuit Breaker parameters
    delayedServiceCircuitBreaker = new DefaultCircuitBreaker(delayedService, 3000,
        FAILURE_THRESHOLD,
        RETRY_PERIOD * 1000 * 1000 * 1000);

    var quickService = new QuickRemoteService();
    //Set the circuit Breaker parameters
    quickServiceCircuitBreaker = new DefaultCircuitBreaker(quickService, 3000, FAILURE_THRESHOLD,
        RETRY_PERIOD * 1000 * 1000 * 1000);

    monitoringService = new MonitoringService(delayedServiceCircuitBreaker,
        quickServiceCircuitBreaker);

  }

  @Test
  public void testFailure_OpenStateTransition() {
    //Calling delayed service, which will be unhealthy till 4 seconds
    assertEquals("Delayed service is down", monitoringService.delayedServiceResponse());
    //As failure threshold is "1", the circuit breaker is changed to OPEN
    assertEquals("OPEN", delayedServiceCircuitBreaker.getState());
    //As circuit state is OPEN, we expect a quick fallback response from circuit breaker.
    assertEquals("Delayed service is down", monitoringService.delayedServiceResponse());

    //Meanwhile, the quick service is responding and the circuit state is CLOSED
    assertEquals("Quick Service is working", monitoringService.quickServiceResponse());
    assertEquals("CLOSED", quickServiceCircuitBreaker.getState());

  }

  @Test
  public void testFailure_HalfOpenStateTransition() {
    //Calling delayed service, which will be unhealthy till 4 seconds
    assertEquals("Delayed service is down", monitoringService.delayedServiceResponse());
    //As failure threshold is "1", the circuit breaker is changed to OPEN
    assertEquals("OPEN", delayedServiceCircuitBreaker.getState());

    //Waiting for recovery period of 2 seconds for circuit breaker to retry service.
    try {
      LOGGER.info("Waiting 2s for delayed service to become responsive");
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    //After 2 seconds, the circuit breaker should move to "HALF_OPEN" state and retry fetching response from service again
    assertEquals("HALF_OPEN", delayedServiceCircuitBreaker.getState());

  }

  @Test
  public void testRecovery_ClosedStateTransition() {
    //Calling delayed service, which will be unhealthy till 4 seconds
    assertEquals("Delayed service is down", monitoringService.delayedServiceResponse());
    //As failure threshold is "1", the circuit breaker is changed to OPEN
    assertEquals("OPEN", delayedServiceCircuitBreaker.getState());

    //Waiting for 4 seconds, which is enough for DelayedService to become healthy and respond successfully.
    try {
      LOGGER.info("Waiting 4s for delayed service to become responsive");
      Thread.sleep(4000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    //As retry period is 2 seconds (<4 seconds of wait), hence the circuit breaker should be back in HALF_OPEN state.
    assertEquals("HALF_OPEN", delayedServiceCircuitBreaker.getState());
    //Check the success response from delayed service.
    assertEquals("Delayed service is working", monitoringService.delayedServiceResponse());
    //As the response is success, the state should be CLOSED
    assertEquals("CLOSED", delayedServiceCircuitBreaker.getState());
  }

}
