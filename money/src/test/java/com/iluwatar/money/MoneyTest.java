package com.iluwatar.money;

import com.iluwatar.money.exception.CurrencyMismatchException;
import com.iluwatar.money.exception.SubtractionCannotOccurException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    public void testIfMoneyObjectCanBeCreated() {
        var moneyObject = new Money(100, Currency.USD);

        assertNotNull(moneyObject);
        assertEquals(100, moneyObject.getAmount());
        assertEquals(Currency.USD, moneyObject.getCurrency());
    }

    @Test
    public void testTwoMoneyEqual() {
        var moneyOne = new Money(100, Currency.USD);
        var moneyTwo = new Money(100, Currency.USD);

        assertEquals(moneyOne, moneyTwo);
    }

    @Test
    public void testIfMoneyCanBeAdded() throws CurrencyMismatchException {
        var moneyOne = new Money(100, Currency.USD);
        var moneyTow = new Money(100, Currency.USD);
        var moneySum = moneyOne.addMoneyBy(moneyTow);

        assertEquals(200, moneySum.getAmount());
    }

    @Test
    public void testAddMoneyByThrowingException() {
        var moneyOne = new Money(100, Currency.USD);
        var moneyTwo = new Money(100, Currency.EUR);

        assertThrows(CurrencyMismatchException.class, () -> moneyOne.addMoneyBy(moneyTwo));
    }

    @Test
    public void testSubtractMoneyByThrowingException() {
        var moneyOne = new Money(100, Currency.USD);
        var moneyTwo = new Money(120, Currency.USD);

        assertThrows(SubtractionCannotOccurException.class, () -> moneyOne.subtractMoneyBy(moneyTwo));
    }

    @Test
    public void testMoneyCanBeSubtractedSuccessfully() throws SubtractionCannotOccurException, CurrencyMismatchException {
        var moneyOne = new Money(100, Currency.USD);
        var moneyTwo = new Money(80, Currency.USD);

        assertEquals(20, moneyOne.subtractMoneyBy(moneyTwo).getAmount());
    }

    @Test
    public void testIfMoneyObjectCanBeMultiplied() {
        var moneyOne = new Money(100, Currency.USD);

        assertEquals(300, moneyOne.multiplyMoneyBy(3).getAmount());
    }

    @Test
    public void testMoneyCanBeAllocated() throws CurrencyMismatchException {
        var a1 = this.createAccount(1);
        var a2 = this.createAccount(2);
        var money = new Money(5, Currency.USD);
        money.allocate(a1, a2, 30, 70);

        assertEquals(new Money(2, Currency.USD), a1.getPrimaryBalance());
        assertEquals(new Money(3, Currency.USD), a2.getPrimaryBalance());
    }

    private Account createAccount(int accountId) throws CurrencyMismatchException {
        var account = new Account(accountId);
        account.setPrimaryCurrency(Currency.USD);
        account.deposit(new Money(0, Currency.USD));
        return account;
    }
}