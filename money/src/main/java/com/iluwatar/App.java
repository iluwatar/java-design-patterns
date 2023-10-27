package com.iluwatar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Main class is an entry point for the application that demonstrates
 * financial account operations using the Account, Currency, and Money classes.
 * It creates accounts, performs deposit and allocation operations, and
 * displays the account balances.
 */
public final class App {
  private App() {
    // Private constructor to hide the default public constructor
  }

  /**
   * Entry point of the application.
   *
   * @param args Command-line arguments.
   */
  public static void main(final String[] args) {
    final Logger logger = LoggerFactory.getLogger(App.class);
    final Currency usd = Currency.usd();
    final Currency eur = Currency.eur();

    final Money money1 = new Money(10_000, usd);
    final Money money2 = new Money(5_000, eur);

    final Account account = new Account(usd, eur);

    account.deposit(money1);
    account.deposit(money2);

    logger.info("Primary Balance: {} {}",
            account.getPrimaryBalance().getAmount(),
            account.getPrimaryBalance().getCurrency().getStringRepresentation());

    logger.info("Secondary Balance: {} {}",
            account.getSecondaryBalance().getAmount(),
            account.getSecondaryBalance().getCurrency().getStringRepresentation());

    final Money allocationMoney = new Money(6_000, usd);

    final Account[] accounts = new Account[2];

    // Allocate the money equally among the accounts
    final long allocatedAmount = allocationMoney.getAmount() / accounts.length;

    accounts[0] = account;
    accounts[1] = new Account(usd, eur);

    // Deposit the allocated money into accounts
    for (Account acc : accounts) {
      acc.deposit(new Money(allocatedAmount, usd));
    }

    logger.info("Allocated Balances:");
    for (int i = 0; i < accounts.length; i++) {
      logger.info("Account {}: {} {}",
              i + 1,
              accounts[i].getPrimaryBalance().getAmount(),
              accounts[i].getPrimaryBalance().getCurrency().getStringRepresentation());
    }
  }
}
