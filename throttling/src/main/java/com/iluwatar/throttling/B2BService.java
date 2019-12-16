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

package com.iluwatar.throttling;

import com.iluwatar.throttling.timer.Throttler;
import java.util.concurrent.ThreadLocalRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A service which accepts a tenant and throttles the resource based on the time given to the
 * tenant.
 */
class B2BService {

  private static final Logger LOGGER = LoggerFactory.getLogger(B2BService.class);
  private final CallsCount callsCount;

  public B2BService(Throttler timer, CallsCount callsCount) {
    this.callsCount = callsCount;
    timer.start();
  }

  /**
   * Calls dummy customer api.
   *
   * @return customer id which is randomly generated
   */
  public int dummyCustomerApi(Tenant tenant) {
    var tenantName = tenant.getName();
    var count = callsCount.getCount(tenantName);
    LOGGER.debug("Counter for {} : {} ", tenant.getName(), count);
    if (count >= tenant.getAllowedCallsPerSecond()) {
      LOGGER.error("API access per second limit reached for: {}", tenantName);
      return -1;
    }
    callsCount.incrementCount(tenantName);
    return getRandomCustomerId();
  }

  private int getRandomCustomerId() {
    return ThreadLocalRandom.current().nextInt(1, 10000);
  }
}
