package com.iluwatar.business.delegate;

/**
 * 
 * Service EJB implementation
 *
 */
public class EjbService implements BusinessService {

	@Override
	public void doProcessing() {
		System.out.println("EjbService is now processing");
	}
}
