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

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.iluwatar.throttling.timer.Throttler;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

/**
 * B2BServiceTest class to test the B2BService
 */
public class B2BServiceTest {

  private CallsCount callsCount = new CallsCount();

  @Test
  public void dummyCustomerApiTest() {
    var tenant = new Tenant("testTenant", 2, callsCount);
    // In order to assure that throttling limits will not be reset, we use an empty throttling implementation
    var timer = (Throttler) () -> {
    };
    var service = new B2BService(timer, callsCount);

    IntStream.range(0, 5).mapToObj(i -> tenant).forEach(service::dummyCustomerApi);
    var counter = callsCount.getCount(tenant.getName());
    assertEquals(2, counter, "Counter limit must be reached");
  }
}
