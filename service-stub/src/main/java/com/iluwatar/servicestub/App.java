/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.servicestub;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Service stub pattern is a design pattern used for replacing third-party services, 
 * such as credit scoring, tax rate lookups and pricing engines, 
 * which are often not available locally for testing.
 * 
 * In this example we use Service stub pattern calling stock alert service for a given stock name.
 * 
 * @author jdoetricksy
 *
 */

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