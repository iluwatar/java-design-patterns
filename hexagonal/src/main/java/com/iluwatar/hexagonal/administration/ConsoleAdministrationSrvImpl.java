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
package com.iluwatar.hexagonal.administration;

import com.iluwatar.hexagonal.domain.LotteryAdministration;
import org.slf4j.Logger;

/**
 * Console implementation for lottery administration.
 */
public class ConsoleAdministrationSrvImpl implements ConsoleAdministrationSrv {
  private final LotteryAdministration administration;
  private final Logger logger;

  /**
   * Constructor.
   */
  public ConsoleAdministrationSrvImpl(LotteryAdministration administration, Logger logger) {
    this.administration = administration;
    this.logger = logger;
  }

  @Override
  public void getAllSubmittedTickets() {
    administration.getAllSubmittedTickets()
        .forEach((k, v) -> logger.info("Key: {}, Value: {}", k, v));
  }

  @Override
  public void performLottery() {
    var numbers = administration.performLottery();
    logger.info("The winning numbers: {}", numbers.getNumbersAsString());
    logger.info("Time to reset the database for next round, eh?");
  }

  @Override
  public void resetLottery() {
    administration.resetLottery();
    logger.info("The lottery ticket database was cleared.");
  }
}
