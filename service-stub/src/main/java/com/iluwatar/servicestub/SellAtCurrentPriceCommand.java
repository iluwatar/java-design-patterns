package com.iluwatar.servicestub;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Sell N counts of the stock provided at current price and update the portfolio to reflect that.
 * 
 * @author npathai
 *
 */

public class SellAtCurrentPriceCommand {
	
	private StockService service;
	  private Portfolio portfolio;
	  private Stock stock;
	  private int quantity;

	  public SellAtCurrentPriceCommand(StockService service, Portfolio portfolio, Stock stock, int quantity) {
	    this.service = service;
	    this.portfolio = portfolio;
	    this.stock = stock;
	    this.quantity = quantity;
	  }
	  
	  public Future<Void> execute() {
	    try {
	      StockQuote stockQuote = service.getQuote(stock);
	      portfolio.subtract(stock, quantity, stockQuote.getCurrentPrice());
	      return CompletableFuture.completedFuture(null);
	    } catch (Exception e) {
	      CompletableFuture<Void> future = new CompletableFuture<>();
	      future.completeExceptionally(e);
	      return future;
	    }    
	  }

}
