package com.iluwatar.singleton;

/**
 * 
 * Singleton class.
 * Eagerly initialized static instance guarantees thread
 * safety.
 * 
 */
public class IvoryTower {

	private static IvoryTower instance = new IvoryTower();

	private IvoryTower() {
	}

	public static IvoryTower getInstance() {
		return instance;
	}
}
