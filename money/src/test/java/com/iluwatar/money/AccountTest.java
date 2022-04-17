package com.iluwatar.money;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.iluwatar.money.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;


class AccountTest {

    private Account testAccount;

    @BeforeEach
    void setup() {
        this.testAccount = new Account(123);
    }

    @Test
    public void testAccountCanBeCreated() {
        assertEquals(543254, new Account(543254).getAccountId());
    }

    @Test
    public void testCurrencies() {
        this.testAccount.setPrimaryCurrency(Currency.USD);
        this.testAccount.setSecondaryCurrency(Currency.EUR);

        assertEquals(Currency.USD, this.testAccount.getPrimaryCurrency());
        assertEquals(Currency.EUR, this.testAccount.getSecondaryCurrency());
        assertEquals(new ArrayList<>(Arrays.asList(Currency.USD, Currency.EUR)), this.testAccount.getCurrencies());
    }

    @Test
    public void testBalances() {
        this.testAccount.setPrimaryCurrency(Currency.USD);
        this.testAccount.setPrimaryBalance(new Money(100, Currency.USD));
        this.testAccount.setSecondaryCurrency(Currency.EUR);
        this.testAccount.setSecondaryBalance(new Money(100, Currency.EUR));

        assertEquals(new Money(100, Currency.USD), this.testAccount.getPrimaryBalance());
        assertEquals(new Money(100, Currency.EUR), this.testAccount.getSecondaryBalance());
    }

    @Test
    public void testAccountCanDepositMoney() throws CurrencyMismatchException {
        this.testAccount.setPrimaryCurrency(Currency.EUR);
        var money = new Money(100, Currency.EUR);
        this.testAccount.deposit(money);

        assertEquals(money, this.testAccount.getPrimaryBalance());
    }

    @Test
    public void testSubsequentDepositsAddUpTheMoney() throws CurrencyMismatchException {
        this.testAccount.setPrimaryCurrency(Currency.EUR);
        var money = new Money(100, Currency.EUR);
        this.testAccount.deposit(money);
        this.testAccount.deposit(money);

        assertEquals(money.multiplyMoneyBy(2), this.testAccount.getPrimaryBalance());
    }

    @Test
    public void testAccountCanWithdrawMoneyOfSameCurrency()
        throws CurrencyMismatchException, BalanceDoesNotExistForAccountException,
        InsufficientFundsException, CurrencyCannotBeExchangedException
        {
        this.testAccount.setPrimaryCurrency(Currency.EUR);
        var money = new Money(100, Currency.EUR);
        this.testAccount.deposit(money);
        this.testAccount.withdraw(new Money(70, Currency.EUR));

        assertEquals(new Money(30, Currency.EUR), this.testAccount.getPrimaryBalance());
    }

    @Test
    public void testThrowsExceptionForNonexistentCurrencyOnWithdraw() throws CurrencyMismatchException {
        this.testAccount.setPrimaryCurrency(Currency.EUR);
        var money = new Money(100, Currency.EUR);
        this.testAccount.deposit(money);

        assertThrows(BalanceDoesNotExistForAccountException.class, () -> this.testAccount.withdraw(new Money(70, Currency.USD)));
    }

    @Test
    public void testThrowsExceptionForCurrencyCanNotBeExchanged() throws CurrencyMismatchException {
        this.testAccount.setPrimaryCurrency(Currency.EUR);
        this.testAccount.setSecondaryCurrency(Currency.CNY);
        var money = new Money(100, Currency.EUR);
        this.testAccount.deposit(money);
        money = new Money(100, Currency.CNY);
        this.testAccount.deposit(money);

        assertThrows(CurrencyCannotBeExchangedException.class, () -> this.testAccount.withdraw(new Money(200, Currency.CNY)));
    }

    @Test
    public void testThrowsExceptionForCurrencyMismatch() throws CurrencyMismatchException {
        this.testAccount.setPrimaryCurrency(Currency.EUR);
        this.testAccount.setSecondaryCurrency(Currency.CNY);
        var money = new Money(100, Currency.EUR);
        this.testAccount.deposit(money);

        assertThrows(CurrencyMismatchException.class, () -> this.testAccount.deposit(new Money(200, Currency.USD)));
    }

    @Test
    public void testItThrowsExceptionIfWeTryToSubtractMoreMoneyThanWeHave() throws CurrencyMismatchException {
        this.testAccount.setPrimaryCurrency(Currency.EUR);
        var money = new Money(100, Currency.EUR);
        this.testAccount.deposit(money);

        this.testAccount.setSecondaryCurrency(Currency.USD);
        money = new Money(0, Currency.USD);
        this.testAccount.deposit(money);

        assertThrows(InsufficientFundsException.class, () -> this.testAccount.withdraw(new Money(150, Currency.EUR)));
    }
}