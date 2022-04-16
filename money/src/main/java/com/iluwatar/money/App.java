package com.iluwatar.money;

import com.iluwatar.money.exception.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Money is representative object for value-unit pairs.
 * It encapsulates the specifics of the money concept.
 * This encapsulation alleviates the burden of computations from Account.
 * Account can concentrate on representing the concept from a higher level,
 * from the point of view of the bank, where methods like connection with account holders,
 * IDs, transactions and money are implemented with the underlying
 * calculations handled by Money Class.
 *
 * <p>In this example an account deposit and withdraw money.
 * The account has two balances of different currencies, USD and EUR.
 * Each of the Balance is firstly deposited with 1 USD and 2 EUR separately.
 * Then 2 EUR is withdrawn from the account.
 */

@Slf4j
public class App {
  /**
   * Program entry point.
   */
  public static void main(final String[] args) throws
      CurrencyMismatchException, BalanceDoesNotExistForAccountException,
      CurrencyCannotBeExchangedException, SubtractionCannotOccurException {
    LOGGER.info("Here you are, the money pattern.");
    final var account = new Account(123);
    LOGGER.info("An account with ID " + 123 + " is created.");
    account.setPrimaryCurrency(Currency.USD);
    account.setSecondaryCurrency(Currency.EUR);
    account.deposit(new Money(100, Currency.USD));
    account.deposit(new Money(200, Currency.EUR));
    account.withdraw(new Money(200, Currency.USD));
    LOGGER.info("Now the account has balance, USD: " + account.getPrimaryBalance().getAmount()
            + ", EUR: " + account.getSecondaryBalance().getAmount());
  }
}
