package com.iluwatar.money;

import com.iluwatar.money.exception.CurrencyCannotBeExchangedException;

public class CurrencyExchange {

  /**
   * Converts the given {@link Money} objects {@link Currency} to another Currency.
   *
   * @param moneyToExchangeCurrency The {@link Money} object which is to be converted.
   * @param exchangeMethod          The {@link ExchangeMethod} which is to be used
   *                                in the conversion.
   * @return A new {@link Money} object with the converted amount and {@link Currency}.
   * @throws CurrencyCannotBeExchangedException If both {@link Currency} objects are the same.
   */
  public static Money convertCurrency(Money moneyToExchangeCurrency, ExchangeMethod exchangeMethod)
          throws CurrencyCannotBeExchangedException {
    if (moneyToExchangeCurrency.getCurrency() != exchangeMethod.getExchangedCurrency()) {
      return new Money(moneyToExchangeCurrency.multiplyMoneyBy(exchangeMethod.getExchangeRatio()),
              exchangeMethod.getExchangedCurrency());
    } else {
      throw new CurrencyCannotBeExchangedException(
              "Currency cannot be the same in order to exchange.");
    }
  }

}
