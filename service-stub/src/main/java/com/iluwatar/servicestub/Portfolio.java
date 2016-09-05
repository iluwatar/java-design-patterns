package com.iluwatar.servicestub;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * It contains user stock information. What stock user holds and in what amount and average price. 
 * Every time user does a trade the stock information is updated.
 * 
 * @author npathai
 *
 */

public class Portfolio {

  private final String username;
  private final Map<Stock, StockInformation> stockToStockInformation = new HashMap<>();
  private BigDecimal totalAssets;
  
  public Portfolio(String username) {
    this.username = username;
    this.totalAssets = new BigDecimal(0);
  }

  public void add(Stock stock, int quantity, BigDecimal price) {
    StockInformation stockInformation = stockToStockInformation.get(stock);
    if (stockInformation == null) {
      stockInformation = new StockInformation(stock, price, quantity);
      stockToStockInformation.put(stock, stockInformation);
    } else {
      stockInformation.add(quantity, price);
    }
    totalAssets = totalAssets.add(price.multiply(new BigDecimal(quantity)));
  }
  
  public void subtract(Stock stock, int quantity, BigDecimal price) {
	    StockInformation stockInformation = stockToStockInformation.get(stock);
	    if (stockInformation != null) {
	    	stockInformation.subtract(quantity, price);
	    } else {
	    	System.out.println("Stock is not in the portfolio!");
	    }
	    totalAssets = totalAssets.subtract(price.multiply(new BigDecimal(quantity)));
	  }
  
  @Override
  public String toString() {
    
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(String.format("User: %s | Total assets worth: %f%n", username, totalAssets.floatValue()));
    for (StockInformation stockInformation : stockToStockInformation.values()) {
      stringBuilder.append(stockInformation.toString()).append("\n");
    }
    
    return stringBuilder.toString();
  }

  public StockInformation get(Stock stock) {
    return stockToStockInformation.get(stock);
  }
}
