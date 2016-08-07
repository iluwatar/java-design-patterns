package com.iluwatar.servicestub.service;

import java.math.BigDecimal;

public class StockServiceStub implements StockService {
  private StockQuote quote;

  public StockServiceStub(BigDecimal quotePrice) {
    this.quote = new StockQuote(quotePrice);
  }

  @Override
  public StockQuote getQuote(Stock stock) throws Exception {
    return quote;
  }
  
  public void setQuotePrice(BigDecimal quotePrice) {
    this.quote = new StockQuote(quotePrice);
  }
}
