package com.iluwatar.servicestub;

import java.math.BigDecimal;

/**
 * It just contains the current price for now. 
 * But it may as well contain day low, day high, year low, year high, day highest point etc information.
 * 
 * @author npathai
 *
 */

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
