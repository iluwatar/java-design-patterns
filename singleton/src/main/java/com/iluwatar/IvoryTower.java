package com.iluwatar;

/**
 * 
 * Singleton class.
 *
 */
public class IvoryTower {

	private static IvoryTower instance;
	
	private IvoryTower() {}

	public static IvoryTower getInstance() {
		if(instance ==  null){
			instance = new IvoryTower();	
		}
		return instance;
	}
}
