package com.iluwatar;

/*
 * This project is licensed under the MIT license. Module model-view-viewmodel
 * is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
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

/**
 * The Account class represents a financial account with balances in two
 * different currencies. It allows deposits and withdrawals in the account's
 * primary and secondary currencies.
 */
public class Account {

    /**
     * The primary currency for the account.
     */
  private final Currency primaryCurrency;

    /**
     * The secondary currency for the account.
     */
  private final Currency secondaryCurrency;

    /**
     * The balance in the primary currency.
     */
  private Money primaryBalance;

    /**
     * The balance in the secondary currency.
     */
    private Money secondaryBalance;

    /**
     * Constructs an Account with the specified primary and secondary
     * currencies.
     *
     * @param pCurr The primary currency for the account.
     * @param sCurr The secondary currency for the account.
     */
  public Account(final Currency pCurr, final Currency sCurr) {
    this.primaryCurrency = pCurr;
    this.secondaryCurrency = sCurr;
    this.primaryBalance = new Money(0, primaryCurrency);
    this.secondaryBalance = new Money(0, secondaryCurrency);
    }

    /**
     * Deposits the specified amount of money into the account.
     *
     * @param money The Money object to deposit into the account.
     * @throws IllegalArgumentException if the deposited money has an invalid
     * currency.
     */
  public void deposit(final Money money) {
    validateCurrency(money);
    if (money.getCurrency().equals(primaryCurrency)) {
        primaryBalance = primaryBalance.add(money);
    } else {
        secondaryBalance = secondaryBalance.add(money);
    }
    }

    /**
     * Withdraws the specified amount of money from the account.
     *
     * @param money The Money object to withdraw from the account.
     * @throws IllegalArgumentException if the withdrawn money has an invalid
     * currency or if there is insufficient balance.
     */
  public void withdraw(final Money money) {
    validateCurrency(money);
    if (money.getCurrency().equals(primaryCurrency)) {
        if (primaryBalance.getAmount() < money.getAmount()) {
            throw new IllegalArgumentException("Insufficient balance in "
                    + "primary currency");
        }
        primaryBalance = primaryBalance.subtract(money);
    } else {
        if (secondaryBalance.getAmount() < money.getAmount()) {
            throw new IllegalArgumentException("Insufficient balance in "
                    + "secondary currency");
        }
        secondaryBalance = secondaryBalance.subtract(money);
    }
    }

    /**
     * Validates that the specified Money object has a valid currency for this
     * account.
     *
     * @param money The Money object to validate.
     * @throws IllegalArgumentException if the currency is invalid for this
     * account.
     */
  private void validateCurrency(final Money money) {
    if (!money.getCurrency().equals(primaryCurrency)
            && !money.getCurrency().equals(secondaryCurrency)) {
        throw new IllegalArgumentException("Invalid currency for this "
                + "account");
    }
    }

    /**
     * Gets the primary currency balance of the account.
     *
     * @return The primary currency balance.
     */
  public Money getPrimaryBalance() {
        return primaryBalance;
    }

    /**
     * Gets the secondary currency balance of the account.
     *
     * @return The secondary currency balance.
     */
  public Money getSecondaryBalance() {
        return secondaryBalance;
    }
}
