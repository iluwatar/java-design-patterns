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
package com.iluwatar.hexagonal.eventlog;

import com.iluwatar.hexagonal.domain.PlayerDetails;
import lombok.extern.slf4j.Slf4j;

/**
 * Standard output event log.
 */
@Slf4j
public class StdOutEventLog implements LotteryEventLog {

  @Override
  public void ticketSubmitted(PlayerDetails details) {
    LOGGER.info("Lottery ticket for {} was submitted. Bank account {} was charged for 3 credits.",
        details.email(), details.bankAccount());
  }

  @Override
  public void ticketDidNotWin(PlayerDetails details) {
    LOGGER.info("Lottery ticket for {} was checked and unfortunately did not win this time.",
        details.email());
  }

  @Override
  public void ticketWon(PlayerDetails details, int prizeAmount) {
    LOGGER.info("Lottery ticket for {} has won! The bank account {} was deposited with {} credits.",
        details.email(), details.bankAccount(), prizeAmount);
  }

  @Override
  public void prizeError(PlayerDetails details, int prizeAmount) {
    LOGGER.error("Lottery ticket for {} has won! Unfortunately the bank credit transfer of"
        + " {} failed.", details.email(), prizeAmount);
  }

  @Override
  public void ticketSubmitError(PlayerDetails details) {
    LOGGER.error("Lottery ticket for {} could not be submitted because the credit transfer"
        + " of 3 credits failed.", details.email());
  }
}
