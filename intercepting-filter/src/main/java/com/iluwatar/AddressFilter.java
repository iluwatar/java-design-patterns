package com.iluwatar;

/**
 * Concrete implementation of filter
 * This filter is responsible for checking/filtering the input in the address field, returns null if field is empty
 * @author joshzambales
 *
 */
public class AddressFilter implements Filter {
	public String execute(String[] request) {
		if (request[2].equals("")) {
			return null;
		} else
			return request[2];
	}
}