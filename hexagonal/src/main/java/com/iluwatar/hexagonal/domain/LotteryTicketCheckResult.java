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

package com.iluwatar.hexagonal.domain;

/**
 * Represents lottery ticket check result.
 */
public class LotteryTicketCheckResult {

  /**
   * Enumeration of Type of Outcomes of a Lottery.
   */
  public enum CheckResult {
    WIN_PRIZE,
    NO_PRIZE,
    TICKET_NOT_SUBMITTED
  }

  private final CheckResult checkResult;
  private final int prizeAmount;

  /**
   * Constructor.
   */
  public LotteryTicketCheckResult(CheckResult result) {
    checkResult = result;
    prizeAmount = 0;
  }

  /**
   * Constructor.
   */
  public LotteryTicketCheckResult(CheckResult result, int amount) {
    checkResult = result;
    prizeAmount = amount;
  }

  /**
   * Get result.
   *
   * @return check result
   */
  public CheckResult getResult() {
    return checkResult;
  }

  /**
   * Get prize amount.
   *
   * @return prize amount
   */
  public int getPrizeAmount() {
    return prizeAmount;
  }

  @Override
  public int hashCode() {
    final var prime = 31;
    var result = 1;
    result = prime * result + ((checkResult == null) ? 0 : checkResult.hashCode());
    result = prime * result + prizeAmount;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    var other = (LotteryTicketCheckResult) obj;
    return checkResult == other.checkResult && prizeAmount == other.prizeAmount;
  }
}
