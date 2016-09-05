package com.iluwatar.servicestub;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Sell N counts of the stock provided with stoploss price of X. 
 * This command will not make a trade unless the stock price tumbles down to stoploss value or below it. 
 * It keeps watch on the current price in background and when the stoploss is crossed, it completes the trade and updates the portfolio.
 * 
 * @author npathai
 *
 */

public class SellAtStopLossCommand {
	
	  private StockService service;
	  private Portfolio portfolio;
	  private Stock stock;
	  private int quantity;
	  private BigDecimal stopLoss;
	  private Executor executor;

	  public SellAtStopLossCommand(StockService service, Portfolio portfolio, Stock stock, 
			  						int quantity, BigDecimal stopLoss, Executor executor) {
	    this.service = service;
	    this.portfolio = portfolio;
	    this.stock = stock;
	    this.quantity = quantity;
	    this.stopLoss = stopLoss;
	    this.executor = executor;
	  }

	  public Future<Void> execute() {
	    CompletableFuture<Void> future = new CompletableFuture<>();
	    executor.execute(() -> {
	      try {
	        while (true) {
	          StockQuote stockQuote = service.getQuote(stock);
	          
	          if (stockQuote.getCurrentPrice().compareTo(stopLoss) <= 0) {
	            portfolio.subtract(stock, quantity, stockQuote.getCurrentPrice());
	            future.complete(null);
	            break;
	          } else {
	            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
	          }
	        }
	        
	      } catch (Exception e) {
	        future.completeExceptionally(e);
	      }    
	      
	    });
	    return future;
	  }

}
