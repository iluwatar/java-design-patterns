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
package com.iluwatar.event.sourcing.domain;

import java.math.BigDecimal;

/**
 * Created by Serdar Hamzaogullari on 06.08.2017.
 */
public class Transaction {

  private final int accountNo;
  private final BigDecimal moneyIn;
  private final BigDecimal moneyOut;
  private final BigDecimal lastBalance;

  /**
   * Instantiates a new Transaction.
   *
   * @param accountNo the account no
   * @param moneyIn the money in
   * @param moneyOut the money out
   * @param lastBalance the last balance
   */
  public Transaction(int accountNo, BigDecimal moneyIn, BigDecimal moneyOut,
      BigDecimal lastBalance) {
    this.accountNo = accountNo;
    this.moneyIn = moneyIn;
    this.moneyOut = moneyOut;
    this.lastBalance = lastBalance;
  }

  /**
   * Gets account no.
   *
   * @return the account no
   */
  public int getAccountNo() {
    return accountNo;
  }

  /**
   * Gets money in.
   *
   * @return the money in
   */
  public BigDecimal getMoneyIn() {
    return moneyIn;
  }

  /**
   * Gets money out.
   *
   * @return the money out
   */
  public BigDecimal getMoneyOut() {
    return moneyOut;
  }

  /**
   * Gets last balance.
   *
   * @return the last balance
   */
  public BigDecimal getLastBalance() {
    return lastBalance;
  }

  @Override
  public String toString() {
    return "Transaction{"
        + "accountNo=" + accountNo
        + ", moneyIn=" + moneyIn
        + ", moneyOut=" + moneyOut
        + ", lastBalance=" + lastBalance
        + '}';
  }
}
