package com.iluwatar.money;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.iluwatar.money.exception.CurrencyCannotBeExchangedException;
import org.junit.jupiter.api.Test;

class CurrencyExchangeTest {

  @Test
  void testConvertCurrency_Successfully() throws CurrencyCannotBeExchangedException {
    Money money = new Money(100, Currency.USD);

    Money convertedMoney = CurrencyExchange.convertCurrency(
            money, ExchangeMethod.assignExchangeMethodBasedOnInput(Currency.EUR));

    assertEquals(money.multiplyMoneyBy(
            ExchangeMethod.USD_TO_EUR.getExchangeRatio()), convertedMoney.getAmount());

    assertEquals(Currency.EUR, convertedMoney.getCurrency());
  }
}