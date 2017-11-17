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

package com.iluwatar.throttling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iluwatar.throttling.timer.Throttler;
import com.iluwatar.throttling.timer.ThrottleTimerImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Throttling pattern is a design pattern to throttle or limit the use of resources or even a complete service by
 * users or a particular tenant. This can allow systems to continue to function and meet service level agreements,
 * even when an increase in demand places load on resources.
 * <p>
 *     In this example we have ({@link App}) as the initiating point of the service.
 *     This is a time based throttling, i.e. only a certain number of calls are allowed per second.
 * </p>
 * ({@link Tenant}) is the Tenant POJO class with which many tenants can be created
 * ({@link B2BService}) is the service which is consumed by the tenants and is throttled.
 */
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * Application entry point
   * @param args main arguments
   */
  public static void main(String[] args) {

    Tenant adidas = new Tenant("Adidas", 5);
    Tenant nike = new Tenant("Nike", 6);

    ExecutorService executorService = Executors.newFixedThreadPool(2);
    
    executorService.execute(() -> makeServiceCalls(adidas));
    executorService.execute(() -> makeServiceCalls(nike));
    
    executorService.shutdown();
    try {
      executorService.awaitTermination(10, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      LOGGER.error("Executor Service terminated: {}", e.getMessage());
    }
  }

  /**
   * Make calls to the B2BService dummy API
   * @param service an instance of B2BService
   */
  private static void makeServiceCalls(Tenant tenant) {
    Throttler timer = new ThrottleTimerImpl(10);
    B2BService service = new B2BService(timer);
    for (int i = 0; i < 20; i++) {
      service.dummyCustomerApi(tenant);
//    Sleep is introduced to keep the output in check and easy to view and analyze the results.
      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
        LOGGER.error("Thread interrupted: {}", e.getMessage());
      }
    }
  }
}
