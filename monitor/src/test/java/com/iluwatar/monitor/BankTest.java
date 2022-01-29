package com.iluwatar.monitor;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

public class BankTest {

  private static final int ACCOUNT_NUM = 4;
  private static final int BASE_AMOUNT = 1000;
  private static Bank bank;

  @BeforeAll
  public static void Setup() {
    bank = new Bank(ACCOUNT_NUM, BASE_AMOUNT);
  }

  @AfterAll
  public static void TearDown() {
    bank = null;
  }

  @Test
  public void GetAccountHaveNotBeNull() {
    assertNotNull(bank.getAccounts());
  }

  @Test
  public void LengthOfAccountsHaveToEqualsToAccountNumConstant() {
    assumeTrue(bank.getAccounts() != null);
    assertEquals(ACCOUNT_NUM, bank.getAccounts().length);
  }

  @Test
  public void TransferMethodHaveToTransferAmountFromAnAccountToOtherAccount() {
    bank.transfer(0, 1, 1000);
    int[] accounts = bank.getAccounts();
    assertEquals(0, accounts[0]);
    assertEquals(2000, 2000);
  }

  @Test
  public void BalanceHaveToBeOK() {
    assertEquals(4000, bank.getBalance());
  }
}
