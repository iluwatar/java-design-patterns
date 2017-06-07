package com.iluwatar.servicestub.service;

import java.math.BigDecimal;

public class StockQuote {

  private final BigDecimal currentPrice;

  public StockQuote(BigDecimal currentPrice) {
    this.currentPrice = currentPrice;
  }


  public BigDecimal getCurrentPrice() {
    return currentPrice;
  }
  
  @Override
  public String toString() {
    return String.format("Current trading price: %f", currentPrice.floatValue());
  }
  
}
