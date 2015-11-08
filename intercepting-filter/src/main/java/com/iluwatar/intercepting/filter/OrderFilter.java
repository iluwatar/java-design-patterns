package com.iluwatar.intercepting.filter;

/**
 * Concrete implementation of filter. This checks for the order field.
 * 
 * @author joshzambales
 *
 */
public class OrderFilter extends AbstractFilter {
	
	@Override
	public String execute(Order order) {
		String result = super.execute(order);
		if (order.getOrder() == null || order.getOrder().isEmpty()) {
			return result + "Invalid order! ";
		} else {
			return result;
		}
	}
}
