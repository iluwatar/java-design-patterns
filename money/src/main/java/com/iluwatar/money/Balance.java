package com.iluwatar.money;

public class Balance {

  private final Money money;
  private final Account account;
  private boolean isPrimaryBalance;

  /**
   * Constructs a {@link Balance} and links it to the account.
   *
   * @param account The account this balance belongs to.
   * @param money   The money that this balance has.
   */
  public Balance(Account account, Money money) {
    this.money = money;
    this.account = account;

    account.addExistingBalancesToAccount(this);
  }

  /**
   * Constructs a {@link Balance} and links it to the account.
   * Also sets the balance to the accounts primary balance.
   *
   * @param account        The account this balance blongs to.
   * @param money          The money that this balance has.
   * @param primaryBalance Is this balance the accounts primary balance.
   */
  public Balance(Account account, Money money, boolean primaryBalance) {
    this.isPrimaryBalance = primaryBalance;
    this.account = account;
    this.money = money;

    account.addExistingBalancesToAccount(this);
  }

  public Money getMoney() {
    return money;
  }

  public Account getAccount() {
    return account;
  }

  public boolean isPrimaryBalance() {
    return isPrimaryBalance;
  }

  /**
   * Converts the {@link Balance} that contains the type of {@link Currency}
   * provided in the method parameters, into a primary balance.
   *
   * @param currency The type of {@link Currency} to assign primary.
   */
  public void convertBalanceToPrimary(Currency currency) {

    account.getAccountBalances().stream().filter(balance ->
            balance.isPrimaryBalance && balance.getMoney().getCurrency() != currency)
            .findFirst().ifPresent(alreadyExistingPrimaryBalance ->
            alreadyExistingPrimaryBalance.isPrimaryBalance = false);

    this.isPrimaryBalance = true;
  }
}
