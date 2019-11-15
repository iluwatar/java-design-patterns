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

package com.iluwatar.saga.orchestration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This pattern is used in distributed services to perform
 * a group of operations atomically.
 * This is an analog of transaction in a database but in terms
 * of microservices architecture this is executed
 * in a distributed environment
 *
 * <p>A saga is a sequence of local transactions in a certain context.
 * If one transaction fails for some reason,
 * the saga executes compensating transactions(rollbacks)
 * to undo the impact of the preceding transactions.
 *
 * <p>In this approach, there is an orchestrator @see {@link SagaOrchestrator}
 * that manages all the transactions and directs
 * the participant services to execute local transactions based on events.
 * The major difference with choreography saga is an ability to handle crashed services
 * (otherwise in choreography services very hard to prevent a saga
 * if one of them has been crashed)
 *
 * @see Saga
 * @see SagaOrchestrator
 * @see Service
 */
public class SagaApplication {
  private static final Logger LOGGER = LoggerFactory.getLogger(SagaApplication.class);

  /**
   * method to show common saga logic.
   */
  public static void main(String[] args) {
    SagaOrchestrator sagaOrchestrator = new SagaOrchestrator(newSaga(), serviceDiscovery());

    Saga.Result goodOrder = sagaOrchestrator.execute("good_order");
    Saga.Result badOrder = sagaOrchestrator.execute("bad_order");
    Saga.Result crashedOrder = sagaOrchestrator.execute("crashed_order");

    LOGGER.info("orders: goodOrder is {}, badOrder is {},crashedOrder is {}",
        goodOrder, badOrder, crashedOrder);
  }


  private static Saga newSaga() {
    return Saga
        .create()
        .chapter("init an order")
        .chapter("booking a Fly")
        .chapter("booking a Hotel")
        .chapter("withdrawing Money");
  }

  private static ServiceDiscoveryService serviceDiscovery() {
    return
        new ServiceDiscoveryService()
            .discover(new OrderService())
            .discover(new FlyBookingService())
            .discover(new HotelBookingService())
            .discover(new WithdrawMoneyService());
  }
}
