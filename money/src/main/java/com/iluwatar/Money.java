package com.iluwatar;
/*
 * This project is licensed under the MIT license. Module model-view-viewmodel
 is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
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
 * The Money class represents a financial amount with a specific currency.
 * It provides methods for
 * performing operations on money values, such as addition, subtraction, and
 * multiplication. Additionally,
 * it supports the allocation of money to an array of financial accounts.
 */

public class Money {
    /**
     * The amount of money, expressed as a long value.
     */

    private long amount;

    /**
     * The currency in which the money amount is denominated.
     */

    private Currency currency;

    /**
     * The default cent factor for the currency.
     */


    private static final double DEFAULT_AMOUNT = 100.0;

    /**
     * Creates a new Money object with the specified amount and currency.
     *
     * @param amon   The amount of money.
     * @param curr The currency associated with the money amount.
     */

    public Money(final long amon, final Currency curr) {
        this.amount = amon;
        this.currency = curr;
    }

    /**
     * Gets the amount of money.
     *
     * @return The amount of money.
     */
    public long getAmount() {
        return amount;
    }

    /**
     * Gets the currency associated with the money amount.
     *
     * @return The currency of the money.
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * Adds another Money object to this Money object.
     *
     * @param other The Money object to add.
     * @return A new Money object representing the result of the addition.
     * @throws IllegalArgumentException if the currencies of the two Money
     * objects are not the same.
     */
    public Money add(final Money other) {
        validateSameCurrency(other);
        return new Money(this.amount + other.amount, this.currency);
    }

    /**
     * Subtracts another Money object from this Money object.
     *
     * @param other The Money object to subtract.
     * @return A new Money object representing the result of the subtraction.
     * @throws IllegalArgumentException if the currencies of the two Money
     * objects are not the same,
     * or if the subtraction would result in a negative amount.
     */
    public Money subtract(final Money other) {
        validateSameCurrency(other);
        if (this.amount < other.amount) {
            throw new IllegalArgumentException("Subtracted money is more than "
                    + "what we have");
        }
        return new Money(this.amount - other.amount, this.currency);
    }

    /**
     * Multiplies the money amount by a specified multiplier.
     *
     * @param multiplier The multiplier to apply to the money amount.
     * @return A new Money object representing the result of the multiplication.
     */

    public Money multiplyBy(final double multiplier) {
        long newAmount = (long) (this.amount * multiplier);
        return new Money(newAmount, this.currency);
    }

    /**
     * Allocates the money amount to an array of financial accounts based on
     * specified percentages.
     *
     * @param accounts     An array of Account objects to allocate the money to.
     * @param percentages  The percentages to allocate to each account.
     * @throws IllegalArgumentException if the sum of percentages is not 100%
     * or if the currencies of Money and accounts are not the same.
     */

    public void allocate(final Account[] accounts, final int... percentages) {
        long remainingAmount = this.amount;
        for (int i = 0; i < accounts.length; i++) {
            long allocation = (long) (this.amount * percentages[i]
                    / DEFAULT_AMOUNT);
            if (i == accounts.length - 1) {
                allocation = remainingAmount;
            }
            accounts[i].deposit(new Money(allocation, this.currency));
            remainingAmount -= allocation;
        }
    }

    /**
     * Validates that the specified Money object has the same currency as this
     * Money object.
     *
     * @param other The Money object to validate.
     * @throws IllegalArgumentException if the currencies of the two Money
     * objects are not the same.
     */

    private void validateSameCurrency(final Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Money objects must have the "
                    + "same currency");
        }
    }
}
