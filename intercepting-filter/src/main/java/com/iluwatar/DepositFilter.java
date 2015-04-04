package com.iluwatar;
/**
 * Concrete implementation of filter
*
 * This checks for the deposit code, returns null when deposit field is empty 
 * @author joshzambales
 *
 */
public class DepositFilter implements Filter{
	public String execute(String[] request){
		if(request[3].equals("")){
			return null;
		}else return request[3];
	}
}
