package com.iluwatar.business.delegate;

/**
 * 
 * Client utilizes BusinessDelegate to call the business tier
 *
 */
public class Client {

	private BusinessDelegate businessDelegate;

	public Client(BusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}

	public void doTask() {
		businessDelegate.doTask();
	}
}
