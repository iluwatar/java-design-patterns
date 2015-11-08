package com.iluwatar.intercepting.filter;

/**
 * Concrete implementation of filter
 * This filter checks for the contact field in which it checks if the input consist of numbers 
 * and it also checks if the input follows the length constraint (11 digits)
 * @author joshzambales
 *
 */
public class ContactFilter extends AbstractFilter {
	
	@Override
	public String execute(Order order) {
		String result = super.execute(order);
		if (order.getContactNumber() == null
				|| order.getContactNumber().isEmpty()
				|| order.getContactNumber().matches(".*[^\\d]+.*")
				|| order.getContactNumber().length() != 11) {
			return result + "Invalid contact number! ";
		} else {
			return result;
		}
	}
}
