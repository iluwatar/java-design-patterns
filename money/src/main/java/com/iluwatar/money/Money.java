package com.iluwatar.money;

import com.iluwatar.money.exception.CurrencyMismatchException;
import com.iluwatar.money.exception.SubtractionCannotOccurException;
import java.util.Objects;

/**
 * This class represents a unit-pair representation of money, which holds the {@link Currency}
 * and amount. It implements addition, subtraction and multiplication on money.
 */
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
   * @param moneyToAdd The {@link Money} object which is to be added to this object.
   * @return The sum of the amounts of both of the {@link Money} objects.
   */
  public Money addMoneyBy(Money moneyToAdd) throws CurrencyMismatchException {
    this.ensureSameCurrencyWith(moneyToAdd);
    return new Money(this.amount + moneyToAdd.getAmount(), this.currency);
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
  public Money subtractMoneyBy(Money moneyToSubtractBy)
      throws SubtractionCannotOccurException, CurrencyMismatchException {
    this.ensureSameCurrencyWith(moneyToSubtractBy);
    if (this.amount < moneyToSubtractBy.getAmount()) {
      throw new SubtractionCannotOccurException(
          "The amount to subtract is more than what we currently have.");
    }
    return new Money(this.amount - moneyToSubtractBy.getAmount(), this.currency);
  }

  /**
   * Multiplies this object with the multiplier provided in the method parameter.
   *
   * @param multiplier Represents the multiplier of which this objects amount
   *                   is to be multiplied by.
   * @return A new {@link Money} object with the product of {@code this.amount * multiplier}.
   */
  public Money multiplyMoneyBy(double multiplier) {
    var product = Math.round(this.amount * multiplier);
    return new Money(product, this.currency);
  }

  public long getAmount() {
    return amount;
  }

  public Currency getCurrency() {
    return currency;
  }

  private void ensureSameCurrencyWith(Money other) throws CurrencyMismatchException {
    if (this.currency != other.getCurrency()) {
      throw new CurrencyMismatchException("Both Moneys must be of same currency");
    }
  }

  /**
   * Allocate {@link Money} between two accounts with different percentage.
   */
  public void allocate(Account a1, Account a2, int a1Percent, int a2Percent) throws
      CurrencyMismatchException {
    var exactA1Balance = this.amount * a1Percent / 100.0;
    var exactA2Balance = this.amount * a2Percent / 100.0;
    var allocatedA1Balance = 0;
    var allocatedA2balance = 0;

    while (this.amount > 0) {
      if (allocatedA1Balance < exactA1Balance) {
        allocatedA1Balance += 1;
        this.amount -= 1;
      }
      if (this.amount <= 0) {
        break;
      }
      if (allocatedA2balance < exactA2Balance) {
        allocatedA2balance += 1;
        this.amount -= 1;
      }
    }

    a1.deposit(new Money(allocatedA1Balance, this.currency));
    a2.deposit(new Money(allocatedA2balance, this.currency));
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    var comparedMoney = (Money) obj;
    return this.amount == comparedMoney.getAmount() && this.currency == comparedMoney.getCurrency();
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount, currency);
  }
}
