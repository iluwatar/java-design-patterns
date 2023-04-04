package com.iluwatar.domainmodel;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;

/**
 * Account class handles all the account related methods of the customer.
 */
@Slf4j
@Getter
@Setter
@Builder
public class Account {

  private String name;
  private Money money;

  /**
   * Print customer's money balance.
   */
  public void showBalance() {
    LOGGER.info(name + " balance: " + money);
  }

  /**
   * For customer to withdraw their money.
   * @param amount Money object.
   * @throws IllegalArgumentException it throws exception
   */
  public void withdraw(Money amount) throws IllegalArgumentException {
    if (money.compareTo(amount) < 0) {
      throw new IllegalArgumentException("Not enough money!");
    }
    money = money.minus(amount);
  }

  /**
   * For customer to receive money.
   * @param amount Money object.
   */
  public void receiveMoney(Money amount) {
    money = money.plus(amount);
  }
}
