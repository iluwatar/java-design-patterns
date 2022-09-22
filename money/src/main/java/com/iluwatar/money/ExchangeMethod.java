package com.iluwatar.money;

import lombok.Getter;

import java.util.Arrays;

/**
 * This class implements the exchange method invoked by {@link CurrencyExchange}.
 */
public enum ExchangeMethod {
  USD_TO_EUR(0.67, Currency.EUR),
  EUR_TO_USD(1.5, Currency.USD);

  @Getter
  private final double exchangeRatio;
  @Getter
  private final Currency exchangedCurrency;

  ExchangeMethod(double exchangeRatio, Currency resultingCurrency) {
    this.exchangeRatio = exchangeRatio;
    this.exchangedCurrency = resultingCurrency;
  }

  public static ExchangeMethod assignExchangeMethodBasedOnInput(Currency currencyToExchangeTo) {
    return Arrays.stream(values()).filter(value ->
        value.getExchangedCurrency() == currencyToExchangeTo).findFirst().orElse(null);
  }
}
