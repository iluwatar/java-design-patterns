package com.iluwatar;
/**
 * Concrete implementation of filter
 * This checks for the order field, returns null when order field is empty 
 * 
 * @author joshzambales
 *
 */
public class OrderFilter implements Filter{
	public String execute(String[] request){
		if(request[4].equals("")){
			return null;
		}else return request[4];
	}
}