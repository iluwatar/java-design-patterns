package com.iluwatar.money;

import com.iluwatar.money.exception.SubtractionCannotOccurException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    public void testIfMoneyObjectCanBeCreated() {
        Money moneyObject = new Money(100, Currency.USD);

        assertNotNull(moneyObject);
        assertEquals(100, moneyObject.getAmount());
        assertEquals(Currency.USD, moneyObject.getCurrency());
    }

    @Test
    void testIfMoneyCanBeAdded() {
        Money moneyOne = new Money(100, Currency.USD);
        Money moneyTow = new Money(100, Currency.USD);
        Money moneySum = new Money(moneyOne.addMoney(moneyTow), Currency.USD);

        assertEquals(200, moneySum.getAmount());
    }

    @Test
    void testSubtractMoneyBy_ThrowsSubtractionCannotOccurException() {
        Money moneyOne = new Money(100, Currency.USD);
        Money moneyTwo = new Money(120, Currency.USD);

        assertThrows(SubtractionCannotOccurException.class, () -> {
            moneyOne.subtractMoneyBy(moneyTwo);
        });
    }

    @Test
    void testMoneyCanBeSubtracted_Sucessfully() throws SubtractionCannotOccurException {
        Money moneyOne = new Money(100, Currency.USD);
        Money moneyTwo = new Money(80, Currency.USD);

        assertEquals(20, moneyOne.subtractMoneyBy(moneyTwo));
    }

    @Test
    void testIfMoneyObjectCanBeMultiplied() {
        Money moneyOne = new Money(100, Currency.USD);

        assertEquals(300, moneyOne.multiplyMoneyBy(3));
    }
}