package com.iluwatar.servicestub.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {

  public static void main(String[] args) throws Exception {
    ExecutorService executor = Executors.newSingleThreadExecutor();

    try {
      YahooStockService service = new YahooStockService();

      Portfolio portfolio = new Portfolio("iluwatar");
      BuyAtCurrentPriceCommand googleCurrentPriceBuy = new BuyAtCurrentPriceCommand(service, portfolio, 
          new Stock("GOOG", "Google"), 100);
      googleCurrentPriceBuy.execute();

      BuyAtCurrentPriceCommand yahooCurrentPriceBuy = new BuyAtCurrentPriceCommand(service, portfolio, 
          new Stock("YHOO", "Yahoo"), 100);
      yahooCurrentPriceBuy.execute();

      BuyAtLimitPriceCommand yahooLimitPriceBuy = new BuyAtLimitPriceCommand(service, portfolio, 
          new Stock("YHOO", "Yahoo"), 100,service.getQuote(new Stock("YHOO", "Yahoo")).getCurrentPrice(),
          executor);

      yahooLimitPriceBuy.execute().get();

      System.out.println(portfolio);
    } finally {
      executor.shutdownNow();
    }
  }
}
