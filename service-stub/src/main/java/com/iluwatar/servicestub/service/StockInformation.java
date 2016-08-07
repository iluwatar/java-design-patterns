package com.iluwatar.servicestub.service;

import java.math.BigDecimal;

public class StockInformation {

  private final Stock stock;
  private BigDecimal totalStockPrice;
  private int quantity;
  
  
  public StockInformation(Stock stock, BigDecimal buyPrice, int quantity) {
    this.stock = stock;
    this.totalStockPrice = buyPrice.multiply(new BigDecimal(quantity));
    this.quantity = quantity;
  }
  
  public Stock getStock() {
    return stock;
  }
  
  public BigDecimal getAverageStockPrice() {
    return totalStockPrice.divide(new BigDecimal(quantity));
  }
  
  public int getQuantity() {
    return quantity;
  }

  public void add(int quantity, BigDecimal price) {
    this.quantity += quantity;
    this.totalStockPrice = totalStockPrice.add(price.multiply(new BigDecimal(quantity)));
  } 
  
  @Override
  public String toString() {
    return String.format("Stock: %s | Quantity: %d | Avg.Price: %f", stock.getName(), quantity, getAverageStockPrice().floatValue());
  }
}
