package com.iluwatar.servicestub;

import java.math.BigDecimal;

public class Portfolio {
	
	Rule rule;
	
	public void addRule(Rule rule){
		BigDecimal priceRule = null;
		String stockSymbol = null;
		
		rule = new StopLoss(stockSymbol, priceRule);
	}
	
	public void evaluate(TickerService service){
		rule.evaluate(service);
	}

}
