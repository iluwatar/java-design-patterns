/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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
package com.iluwatar.tls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.AccessDeniedException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A service which accepts a tenant and throttles the resource based on the time given to the tenant.
 */
class B2BService {

  private Tenant tenant;
  private int callsCounter;

  private static final Logger LOGGER = LoggerFactory.getLogger(B2BService.class);

  /**
   * A timer is initiated as soon as the Service is initiated. The timer runs every minute and resets the
   * counter.
   * @param tenant the Tenant which will consume the service.
   */
  public B2BService(Tenant tenant) {
    this.tenant = tenant;
    Timer timer = new Timer(true);

    timer.schedule(new TimerTask() {
      @Override
      public void run() {
          callsCounter = 0;
      }
    }, 0, 1000);
  }

  /**
   *
   * @return customer id which is randomly generated
   * @throws AccessDeniedException when the limit is reached
   */
  public int dummyCustomerApi() throws AccessDeniedException {
    LOGGER.debug("Counter for {} : {} ", tenant.getName(), callsCounter);

    if (callsCounter >= tenant.getAllowedCallsPerSecond()) {
      throw new AccessDeniedException("API access per second limit reached for: " + tenant.getName());
    }
    callsCounter++;
    return getRandomCustomerId();
  }

  private int getRandomCustomerId() {
    return ThreadLocalRandom.current().nextInt(1, 10000);
  }

  /**
   *
   * @return current count of the calls made.
   */
  public int getCurrentCallsCount() {
    return callsCounter;
  }
}
