package com.iluwatar;

import lombok.Getter;

/**
 * Represents a monetary value with an associated currency.
 * Provides operations for basic arithmetic (addition, subtraction, multiplication),
 * as well as currency conversion while ensuring proper rounding.
 */
@Getter
public class Money {
  private @Getter double amount;
  private @Getter String currency;

  /**
   * Constructs a Money object with the specified amount and currency.
   *
   * @param amnt the amount of money (as a double).
   * @param curr the currency code (e.g., "USD", "EUR").
   */
  public Money(double amnt, String curr) {
    this.amount = amnt;
    this.currency = curr;
  }

  /**
   * Rounds the given value to two decimal places.
   *
   * @param value the value to round.
   * @return the rounded value, up to two decimal places.
   */
  private double roundToTwoDecimals(double value) {
    return Math.round(value * 100.0) / 100.0;
  }

  /**
   * Adds another Money object to the current instance.
   *
   * @param moneyToBeAdded the Money object to add.
   * @throws CannotAddTwoCurrienciesException if the currencies do not match.
   */
  public void addMoney(Money moneyToBeAdded) throws CannotAddTwoCurrienciesException {
    if (!moneyToBeAdded.getCurrency().equals(this.currency)) {
      throw new CannotAddTwoCurrienciesException("You are trying to add two different currencies");
    }
    this.amount = roundToTwoDecimals(this.amount + moneyToBeAdded.getAmount());
  }

  /**
   * Subtracts another Money object from the current instance.
   *
   * @param moneyToBeSubtracted the Money object to subtract.
   * @throws CannotSubtractException if the currencies do not match or if the amount to subtract is larger than the current amount.
   */
  public void subtractMoney(Money moneyToBeSubtracted) throws CannotSubtractException {
    if (!moneyToBeSubtracted.getCurrency().equals(this.currency)) {
      throw new CannotSubtractException("You are trying to subtract two different currencies");
    } else if (moneyToBeSubtracted.getAmount() > this.amount) {
      throw new CannotSubtractException("The amount you are trying to subtract is larger than the amount you have");
    }
    this.amount = roundToTwoDecimals(this.amount - moneyToBeSubtracted.getAmount());
  }

  /**
   * Multiplies the current amount of money by a factor.
   *
   * @param factor the factor to multiply by.
   * @throws IllegalArgumentException if the factor is negative.
   */
  public void multiply(int factor) {
    if (factor < 0) {
      throw new IllegalArgumentException("Factor must be non-negative");
    }
    this.amount = roundToTwoDecimals(this.amount * factor);
  }

  /**
   * Converts the current amount of money to another currency using the provided exchange rate.
   *
   * @param currencyToChangeTo the new currency to convert to.
   * @param exchangeRate the exchange rate to convert from the current currency to the new currency.
   * @throws IllegalArgumentException if the exchange rate is negative.
   */
  public void exchangeCurrency(String currencyToChangeTo, double exchangeRate) {
    if (exchangeRate < 0) {
      throw new IllegalArgumentException("Exchange rate must be non-negative");
    }
    this.amount = roundToTwoDecimals(this.amount * exchangeRate);
    this.currency = currencyToChangeTo;
  }
}
