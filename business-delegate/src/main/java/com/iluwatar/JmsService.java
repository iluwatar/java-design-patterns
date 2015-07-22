package com.iluwatar;

/**
 * 
 * Service JMS implementation
 *
 */
public class JmsService implements BusinessService {

	@Override
	public void doProcessing() {
		System.out.println("JmsService is now processing");
	}
}
