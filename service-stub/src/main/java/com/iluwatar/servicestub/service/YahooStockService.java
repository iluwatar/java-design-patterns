package com.iluwatar.servicestub.service;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class YahooStockService implements StockService {

  @Override
  public StockQuote getQuote(com.iluwatar.servicestub.service.Stock stock) throws Exception {
    Stock yahooStock = YahooFinance.get(stock.getSymbol());
    return new StockQuote(yahooStock.getQuote().getPrice());
  }

}
