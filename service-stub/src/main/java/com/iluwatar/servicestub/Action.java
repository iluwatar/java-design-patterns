package com.iluwatar.servicestub;

import java.math.BigDecimal;

public class Action {
	
	public void sell(){
		System.out.println("Stock price reached maximum, SELL!");
	}
	
	public void error(){
		System.out.println("Info is not available for given stock.");
	}

}
