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
package com.iluwatar.saga.choreography;

import org.junit.Assert;
import org.junit.Test;

/**
 * test to check choreography saga
 */
public class SagaChoreographyTest {


  @Test
  public void executeTest() {
    ServiceDiscoveryService sd = serviceDiscovery();
    ChoreographyChapter service = sd.findAny();
    Saga badOrderSaga = service.execute(newSaga("bad_order"));
    Saga goodOrderSaga = service.execute(newSaga("good_order"));

    Assert.assertEquals(badOrderSaga.getResult(), Saga.SagaResult.ROLLBACKED);
    Assert.assertEquals(goodOrderSaga.getResult(), Saga.SagaResult.FINISHED);
  }

  private static Saga newSaga(Object value) {
    return Saga
        .create()
        .chapter("init an order").setInValue(value)
        .chapter("booking a Fly")
        .chapter("booking a Hotel")
        .chapter("withdrawing Money");
  }

  private static ServiceDiscoveryService serviceDiscovery() {
    ServiceDiscoveryService sd = new ServiceDiscoveryService();
    return sd
        .discover(new OrderService(sd))
        .discover(new FlyBookingService(sd))
        .discover(new HotelBookingService(sd))
        .discover(new WithdrawMoneyService(sd));
  }
}
