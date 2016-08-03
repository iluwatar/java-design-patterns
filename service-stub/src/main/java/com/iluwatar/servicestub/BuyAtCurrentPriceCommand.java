package com.iluwatar.servicestub;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * User buys the stock provided on current trading price and portfolio is updated to reflect that.
 * 
 * @author npathai
 *
 */

public class BuyAtCurrentPriceCommand {

  private StockService service;
  private Portfolio portfolio;
  private Stock stock;
  private int quantity;

  public BuyAtCurrentPriceCommand(StockService service, Portfolio portfolio, Stock stock, int quantity) {
    this.service = service;
    this.portfolio = portfolio;
    this.stock = stock;
    this.quantity = quantity;
  }
  
  public Future<Void> execute() {
    try {
      StockQuote stockQuote = service.getQuote(stock);
      portfolio.add(stock, quantity, stockQuote.getCurrentPrice());
      return CompletableFuture.completedFuture(null);
    } catch (Exception e) {
      CompletableFuture<Void> future = new CompletableFuture<>();
      future.completeExceptionally(e);
      return future;
    }    
  }
}
