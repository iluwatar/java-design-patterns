package com.iluwatar.money;

import com.iluwatar.money.exception.SubtractionCannotOccurException;

public class Money {

  private long amount;
  private final Currency currency;


  public Money(long amount, Currency currency) {
    this.amount = amount;
    this.currency = currency;
  }

  /**
   * Adds both {@link Money} objects {@code amount} to each other and returns a new
   * {@link Money} object with the sum of both amounts.
   *
   * @param moneyToAdd The {@link Money} object which is the be added to this object.
   * @return The sum of the amounts of both of the {@link Money} objects.
   */
  public long addMoney(Money moneyToAdd) {
    this.amount = this.amount + moneyToAdd.getAmount();
    return this.amount;
  }

  /**
   * Subtracts from this objects amount with the {@link Money}
   * objects amount provided in the method params.
   *
   * @param moneyToSubtractBy The {@link Money} object containing the amount
   *                          that is to be subtracted from this object.
   * @return The difference between the amounts of the {@link Money} objects.
   * @throws SubtractionCannotOccurException If this objects amount is less than the {@link Money}
   *                                         objects amount which this object
   *                                         is to be subtracted by.
   */
  public long subtractMoneyBy(Money moneyToSubtractBy) throws SubtractionCannotOccurException {
    if (this.amount < moneyToSubtractBy.amount) {
      throw new SubtractionCannotOccurException(
              "The amount to subtract is more than what we currently have.");
    }
    this.amount = this.amount - moneyToSubtractBy.amount;
    return this.amount;
  }

  /**
   * Multiplies this object with the multiplier provided in the method parameter.
   *
   * @param multiplier Represents the multiplier of which this objects amount
   *                   is to be multiplied by.
   * @return A new {@link Money} object with the product of {@code this.amount * multiplier}.
   */
  public long multiplyMoneyBy(double multiplier) {
    return Math.round(this.amount * multiplier);
  }

  public long getAmount() {
    return amount;
  }

  public Currency getCurrency() {
    return currency;
  }
}
