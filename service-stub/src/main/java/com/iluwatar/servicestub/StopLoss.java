package com.iluwatar.servicestub;
import java.math.BigDecimal;

public class StopLoss implements Rule{
	
	String stockSymbol;
	BigDecimal stopLoss;	
	
	/**
	 * @param stockSymbol
	 * @param stopLoss
	 */
	public StopLoss(String stockSymbol, BigDecimal stopLoss) {
		super();
		this.stockSymbol = stockSymbol;
		this.stopLoss = stopLoss;
	}

	@Override
	public void evaluate(TickerService service) {
		// TODO Auto-generated method stub
		
		BigDecimal currentPrice = service.currentPrice(stockSymbol);
		stopLoss = service.yearLow(stockSymbol);
		if(currentPrice.intValue() <= stopLoss.intValue()){
			System.out.println("Stock price is going down. SELL!");
		}else{
			System.out.println("Stock price is OK.");
		}
		
	}
	

}
