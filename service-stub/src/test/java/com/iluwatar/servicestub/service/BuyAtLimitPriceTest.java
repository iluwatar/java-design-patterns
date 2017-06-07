package com.iluwatar.servicestub.service;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class BuyAtLimitPriceTest {
  

  private StockServiceStub stub = new StockServiceStub(new BigDecimal(101));
  private Portfolio portfolio = new Portfolio("iluwatar");
  private Stock stock = new Stock("GOOG", "Google");
  private BuyAtLimitPriceCommand buyAtLimitPriceCommand;
  private Future<Void> future;
  private ExecutorService executor = Executors.newSingleThreadExecutor();

  @Test
  public void test() throws InterruptedException, ExecutionException {
    stockIsTradingAt(101);
    
    whenBuyingAtLimitPriceOf(100);
    
    tradeDoesNotOccur();
    
    afterAWhile();
    
    stockIsTradingAt(99);
    
    tradeOccurs();
  }

  private void afterAWhile() throws InterruptedException {
    Thread.sleep(1000);
  }

  private void tradeOccurs() throws InterruptedException, ExecutionException {
    future.get();
    
    Assert.assertEquals(100, portfolio.get(stock).getQuantity());
  }

  private void tradeDoesNotOccur() {
    Assert.assertFalse(future.isDone());
    Assert.assertNull(portfolio.get(stock));
  }

  private void whenBuyingAtLimitPriceOf(int limitPrice) {
    buyAtLimitPriceCommand = new BuyAtLimitPriceCommand(stub, portfolio, 
        stock, 100, new BigDecimal(limitPrice), executor);
    future = buyAtLimitPriceCommand.execute();
  }

  private void stockIsTradingAt(int currentPrice) {
    stub.setQuotePrice(new BigDecimal(currentPrice));
  }
  
  @After
  public void tearDown() {
    executor.shutdownNow();
  }
}
