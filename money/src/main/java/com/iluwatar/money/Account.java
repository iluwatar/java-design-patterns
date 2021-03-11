package com.iluwatar.money;

import com.iluwatar.money.exception.BalanceDoesNotExistForAccountException;
import com.iluwatar.money.exception.CurrencyCannotBeExchangedException;
import com.iluwatar.money.exception.InsufficientFundsException;
import com.iluwatar.money.exception.SubtractionCannotOccurException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a User Account, which holds all the {@link Balance}s
 * that this user has created.
 */
public class Account {

  private final int accountId;
  private final Set<Balance> accountBalances = new HashSet<>();

  public Account(int accountId) {
    this.accountId = accountId;
  }

  /**
   * Creates a new Balance object for the account.
   *
   * @param money Contains the information required to create the balance.
   */
  public Balance createBalance(Money money) {
    Balance createdBalance = new Balance(this, money);
    this.accountBalances.add(createdBalance);
    return createdBalance;
  }

  /**
   * Deposits funds into the corresponding {@link Balance} within the {@link Account}.
   *
   * @param amountToDeposit The {@link Money} object containing the amount to deposit
   *                        and the {@link Currency}.
   */
  public void depositFundsIntoBalance(Balance balance, Money amountToDeposit) {
    balance.getMoney().addMoney(amountToDeposit);
  }

  /**
   * Withdraws funds from an accounts balance.
   *
   * @param moneyToWithdraw The {@link Money} containing the amount and {@link Currency}
   *                        representation to be withdrawn.
   *
   * @throws InsufficientFundsException If the {@link Account} doesn't have sufficient funds.
   */
  public void withdrawFromBalance(Balance balance, Money moneyToWithdraw)
          throws InsufficientFundsException {


    if (balance.getMoney().getAmount() > moneyToWithdraw.getAmount()) {

      try {
        balance.getMoney().subtractMoneyBy(moneyToWithdraw);
      } catch (SubtractionCannotOccurException e) {
        System.err.println("Insufficient funds in balance.");
      }

    } else {

      try {
        Balance combinedBalance = combineAllAccountBalances(moneyToWithdraw.getCurrency());

        if (combinedBalance.getMoney().getAmount() > moneyToWithdraw.getAmount()) {
          balance.getMoney().subtractMoneyBy(moneyToWithdraw);
        } else {
          throw new InsufficientFundsException("Not enough funds on the account.");
        }
      } catch (SubtractionCannotOccurException e) {
        System.err.println("Error while trying to withdraw currency.");
      }
    }
  }

  /**
   * Combines all of the account balances and adds them to the balance that is to be withdrawn from.
   *
   * @param currencyToBeCombinedInto The currency that the accounts balances should
   *                                 be combined into.
   * @return The combined {@link Balance} that contains all the converted funds.
   */
  private Balance combineAllAccountBalances(Currency currencyToBeCombinedInto) {
    Balance requestedBalance = null;
    try {
      requestedBalance = retrieveRequestedBalance(currencyToBeCombinedInto);
    } catch (BalanceDoesNotExistForAccountException e) {
      System.err.println(currencyToBeCombinedInto + "Balance non-existent");
    }

    Balance finalRequestedBalance = requestedBalance;
    this.getAccountBalances().forEach(balance -> {

      if (balance.getMoney().getCurrency() != currencyToBeCombinedInto) {
        try {
          Money convertedCurrency = CurrencyExchange.convertCurrency(balance.getMoney(),
                  ExchangeMethod.assignExchangeMethodBasedOnInput(
                          finalRequestedBalance.getMoney().getCurrency()));

          finalRequestedBalance.getMoney().addMoney(convertedCurrency);
        } catch (CurrencyCannotBeExchangedException e) {
          System.err.println("Currency must not be the same to convert.");
        }
      }
    });

    return requestedBalance;
  }

  /**
   * Retrieves the {@link Balance} corresponding with the {@link Currency}.
   *
   * @param currency The {@link Currency} of the {@link Balance} which is to be retrieved.
   *
   * @return The {@link Balance} that corresponds to the {@link Currency}.
   *
   * @throws BalanceDoesNotExistForAccountException If the {@link Balance} which was requested
   *        doesn't exist.
   */
  private Balance retrieveRequestedBalance(Currency currency)
          throws BalanceDoesNotExistForAccountException {

    return this.getAccountBalances().stream()
            .filter(balance -> balance.getMoney().getCurrency() == currency).findFirst()
            .orElseThrow(BalanceDoesNotExistForAccountException::new);
  }

  public void addExistingBalancesToAccount(Balance... balances) {
    this.accountBalances.addAll(Arrays.asList(balances));
  }

  public int getAccountId() {
    return accountId;
  }

  public Set<Balance> getAccountBalances() {
    return accountBalances;
  }
}
