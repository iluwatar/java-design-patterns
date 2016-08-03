package com.iluwatar.servicestub;

import java.io.IOException;
import java.math.BigDecimal;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class YahooTickerService implements TickerService{
	
	Stock stock;
	BigDecimal price;

	/**
	 * @param stock
	 * @param price
	 */
	public YahooTickerService(Stock stock, BigDecimal price) {
		super();
		this.stock = stock;
		this.price = price;
	}

	@Override
	public BigDecimal currentPrice(String stockSymbol) {
		// TODO Auto-generated method stub
		try {
			stock = YahooFinance.get(stockSymbol);
			price = stock.getQuote().getPrice();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return price;
	}

	@Override
	public BigDecimal yearLow(String stockSymbol) {
		// TODO Auto-generated method stub
		try {
			stock = YahooFinance.get(stockSymbol);
			price = stock.getQuote().getYearLow();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return price;
	}

}
