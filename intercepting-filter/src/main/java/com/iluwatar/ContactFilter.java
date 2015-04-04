package com.iluwatar;

/**
 * Concrete implementation of filter
 * This filter checks for the contact field in which it checks if the input consist of numbers and it also checks if the input follows the length constraint (11 digits)
 * @author joshzambales
 *
 */
public class ContactFilter implements Filter {
	public String execute(String[] request) {
		if (request[1].equals("") || request[1].matches(".*[^\\d]+.*")
				|| request[1].length() != 11) {
			return null;
		} else
			return request[1];
	}
}
