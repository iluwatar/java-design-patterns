package com.iluwatar.money;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.money.exception.BalanceCouldNotBeCreatedException;
import com.iluwatar.money.exception.InsufficientFundsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class AccountTest {

  private static Account testAccount;

  @BeforeAll
  static void beforeAll() {
    testAccount = new Account(123);
  }

  @AfterEach
  void tearDown() {
    testAccount.getAccountBalances().clear();
  }

  @Test
  public void testAccountCanBeCreated() {
    assertEquals(543254, new Account(543254).getAccountId());
  }

  @Test
  public void testCreateBalanceForExistingAccount_Successfully() {
    Money testMoney = new Money(541, Currency.USD);

    Balance testBalance = testAccount.createBalance(testMoney);

    assertNotNull(testBalance);
    assertTrue(testAccount.getAccountBalances().contains(testBalance));
  }

  @Test
  public void testDepositFundsIntoAccount_Successfully() {
    Money testMoneyToDeposit = new Money(654, Currency.USD);
    Money moneyToInitiallyCreateBalance = new Money(0, Currency.USD);


    Balance testBalance = testAccount.createBalance(moneyToInitiallyCreateBalance);
    testAccount.depositFundsIntoBalance(testBalance, testMoneyToDeposit);

    assertEquals(654, testBalance.getMoney().getAmount());
  }

  @Test
  public void testWithdrawFromBalance_Successfully() throws InsufficientFundsException {
    Money testMoneyToWithdraw = new Money(500, Currency.USD);
    Money moneyToCreateBalance = new Money(1000, Currency.USD);

    Balance testBalance = testAccount.createBalance(moneyToCreateBalance);

    assertEquals(1000, testBalance.getMoney().getAmount());
    testAccount.withdrawFromBalance(testBalance, testMoneyToWithdraw);
    assertEquals(500, testBalance.getMoney().getAmount());
  }

  @Test
  public void testWithdrawFundsFromAccount_FailedWithInsufficientFundsException() {
    Money testMoney = new Money(500, Currency.USD);
    Money testMoneyToWithdraw = new Money(700, Currency.USD);

    Balance testBalance = testAccount.createBalance(testMoney);

    assertThrows(InsufficientFundsException.class,
            () -> testAccount.withdrawFromBalance(testBalance, testMoneyToWithdraw));
  }
}