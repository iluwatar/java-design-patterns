package com.iluwatar;
/**
 * Concrete implementation of filter
 * This filter checks if the input in the Name field is valid. (alphanumeric) 
 * @author joshzambales
 *
 */
public class NameFilter implements Filter{
	public String execute(String[] request){
		if(request[0].equals("") || request[0].matches(".*[^\\w|\\s]+.*")){
			return null;
		}else return request[0];
	}
}