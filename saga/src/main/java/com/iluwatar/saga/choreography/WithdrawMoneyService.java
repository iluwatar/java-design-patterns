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

/**
 * Class representing a service to withdraw a money.
 */
public class WithdrawMoneyService extends Service {

  public WithdrawMoneyService(ServiceDiscoveryService service) {
    super(service);
  }

  @Override
  public String getName() {
    return "withdrawing Money";
  }

  @Override
  public Saga process(Saga saga) {
    var inValue = saga.getCurrentValue();

    if (inValue.equals("bad_order")) {
      LOGGER.info("The chapter '{}' has been started. But the exception has been raised."
              + "The rollback is about to start",
          getName(), inValue);
      saga.setCurrentStatus(Saga.ChapterResult.ROLLBACK);
      return saga;
    }
    return super.process(saga);
  }
}
