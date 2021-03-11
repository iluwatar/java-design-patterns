package com.iluwatar.money;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class BalanceTest {

  @Test
  void testOnBalanceCreationBalanceAddedToAccount() {
    Account account = new Account(123);
    Balance balance = new Balance(account, new Money(500, Currency.USD));

    assertTrue(account.getAccountBalances().contains(balance));
  }

}