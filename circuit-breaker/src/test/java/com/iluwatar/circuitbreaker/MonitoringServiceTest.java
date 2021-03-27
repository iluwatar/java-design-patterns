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

import org.junit.jupiter.api.Test;

/**
 * Monitoring Service test
 */
class MonitoringServiceTest {

  //long timeout, int failureThreshold, long retryTimePeriod
  @Test
  void testLocalResponse() {
    var monitoringService = new MonitoringService(null,null);
    var response = monitoringService.localResourceResponse();
    assertEquals(response, "Local Service is working");
  }

  @Test
  void testDelayedRemoteResponseSuccess() {
    var delayedService = new DelayedRemoteService(System.nanoTime()-2*1000*1000*1000, 2);
    var delayedServiceCircuitBreaker = new DefaultCircuitBreaker(delayedService, 3000,
        1,
        2 * 1000 * 1000 * 1000);

    var monitoringService = new MonitoringService(delayedServiceCircuitBreaker,null);
    //Set time in past to make the server work
    var response = monitoringService.delayedServiceResponse();
    assertEquals(response, "Delayed service is working");
  }

  @Test
  void testDelayedRemoteResponseFailure() {
    var delayedService = new DelayedRemoteService(System.nanoTime(), 2);
    var delayedServiceCircuitBreaker = new DefaultCircuitBreaker(delayedService, 3000,
        1,
        2 * 1000 * 1000 * 1000);
    var monitoringService = new MonitoringService(delayedServiceCircuitBreaker,null);
    //Set time as current time as initially server fails
    var response = monitoringService.delayedServiceResponse();
    assertEquals(response, "Delayed service is down");
  }

  @Test
  void testQuickRemoteServiceResponse() {
    var delayedService = new QuickRemoteService();
    var delayedServiceCircuitBreaker = new DefaultCircuitBreaker(delayedService, 3000,
        1,
        2 * 1000 * 1000 * 1000);
    var monitoringService = new MonitoringService(delayedServiceCircuitBreaker,null);
    //Set time as current time as initially server fails
    var response = monitoringService.delayedServiceResponse();
    assertEquals(response, "Quick Service is working");
  }
}
