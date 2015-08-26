package com.iluwatar.servicelocator;

/**
 * This is going to be the parent service interface which we will
 * use to create our services. All services will have a
 * <li>service name</li>
 * <li>unique id</li>
 * <li>execution work flow</li>
 * 
 * @author saifasif
 *
 */
public interface Service {
	
	/*
	 * The human readable name of the service
	 */
	String getName();
	
	/*
	 * Unique ID of the particular service
	 */
	int getId();
	
	/*
	 * The workflow method that defines what this service does
	 */
	void execute();
}
