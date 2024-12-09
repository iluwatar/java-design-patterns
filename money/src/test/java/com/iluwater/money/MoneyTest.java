package com.iluwater.money;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.iluwatar.CannotAddTwoCurrienciesException;
import com.iluwatar.CannotSubtractException;
import com.iluwatar.Money;
import com.iluwatar.App;


 class MoneyTest {

  @Test
   void testConstructor() {
    // Test the constructor
    Money money = new Money(100.00, "USD");
    assertEquals(100.00, money.getAmount());
    assertEquals("USD", money.getCurrency());
  }

  @Test
   void testAddMoney_SameCurrency() throws CannotAddTwoCurrienciesException {
    // Test adding two Money objects with the same currency
    Money money1 = new Money(100.00, "USD");
    Money money2 = new Money(50.25, "USD");

    money1.addMoney(money2);

    assertEquals(150.25, money1.getAmount(), "Amount after addition should be 150.25");
  }

  @Test
   void testAddMoney_DifferentCurrency() {
    // Test adding two Money objects with different currencies
    Money money1 = new Money(100.00, "USD");
    Money money2 = new Money(50.25, "EUR");

    assertThrows(CannotAddTwoCurrienciesException.class, () -> {
      money1.addMoney(money2);
    });
  }

  @Test
   void testSubtractMoney_SameCurrency() throws CannotSubtractException {
    // Test subtracting two Money objects with the same currency
    Money money1 = new Money(100.00, "USD");
    Money money2 = new Money(50.25, "USD");

    money1.subtractMoney(money2);

    assertEquals(49.75, money1.getAmount(), "Amount after subtraction should be 49.75");
  }

  @Test
   void testSubtractMoney_DifferentCurrency() {
    // Test subtracting two Money objects with different currencies
    Money money1 = new Money(100.00, "USD");
    Money money2 = new Money(50.25, "EUR");

    assertThrows(CannotSubtractException.class, () -> {
      money1.subtractMoney(money2);
    });
  }

  @Test
   void testSubtractMoney_AmountTooLarge() {
    // Test subtracting an amount larger than the current amount
    Money money1 = new Money(50.00, "USD");
    Money money2 = new Money(60.00, "USD");

    assertThrows(CannotSubtractException.class, () -> {
      money1.subtractMoney(money2);
    });
  }

  @Test
   void testMultiply() {
    // Test multiplying the money amount by a factor
    Money money = new Money(100.00, "USD");

    money.multiply(3);

    assertEquals(300.00, money.getAmount(), "Amount after multiplication should be 300.00");
  }

  @Test
   void testMultiply_NegativeFactor() {
    // Test multiplying by a negative factor
    Money money = new Money(100.00, "USD");

    assertThrows(IllegalArgumentException.class, () -> {
      money.multiply(-2);
    });
  }

  @Test
   void testExchangeCurrency() {
    // Test converting currency using an exchange rate
    Money money = new Money(100.00, "USD");

    money.exchangeCurrency("EUR", 0.85);

    assertEquals("EUR", money.getCurrency(), "Currency after conversion should be EUR");
    assertEquals(85.00, money.getAmount(), "Amount after conversion should be 85.00");
  }

  @Test
   void testExchangeCurrency_NegativeExchangeRate() {
    // Test converting currency with a negative exchange rate
    Money money = new Money(100.00, "USD");

    assertThrows(IllegalArgumentException.class, () -> {
      money.exchangeCurrency("EUR", -0.85);
    });
  }


  @Test
     void testAppExecution() {
       assertDoesNotThrow(() -> {
         App.main(new String[]{});
       }, "App execution should not throw any exceptions");
     }

}
