package com.iluwatar.servicestub;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import yahoofinance.*;
import yahoofinance.histquotes.*;

public class Rule {
	
	private Stock stock;
	
	public Rule(){
	}
	
	public boolean checkPrice(String stockName){
		boolean eval = false;
		try {
			stock = YahooFinance.get(stockName);
			BigDecimal currentPrice = stock.getQuote().getDayHigh();
			List<HistoricalQuote> histStocks = stock.getHistory(Interval.WEEKLY);
			for (int i = 0; i < histStocks.size(); i++) {
				HistoricalQuote histStock = histStocks.get(i);
				BigDecimal histStockPrice = histStock.getHigh();
				if(currentPrice.intValue() > histStockPrice.intValue())
					eval = true;
				else
					eval = false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			System.out.println("Thank you for using stock alert service");
		}
		return eval;
	}
}