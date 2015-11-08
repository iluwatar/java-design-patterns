package com.iluwatar.intercepting.filter;

/**
 * Concrete implementation of filter
 * This checks for the deposit code 
 * @author joshzambales
 *
 */
public class DepositFilter extends AbstractFilter {
	
	@Override
	public String execute(Order order) {
		String result = super.execute(order);
		if (order.getDepositNumber() == null || order.getDepositNumber().isEmpty()) {
			return result + "Invalid deposit number! ";
		} else {
			return result;
		}
	}
}
