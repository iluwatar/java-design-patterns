package com.iluwatar;

public class App {
	
	public static void main(String[] args) {
		
		BusinessDelegate businessDelegate = new BusinessDelegate();
		businessDelegate.setServiceType(ServiceType.EJB);

		Client client = new Client(businessDelegate);
		client.doTask();

		businessDelegate.setServiceType(ServiceType.JMS);
		client.doTask();
	}
}
