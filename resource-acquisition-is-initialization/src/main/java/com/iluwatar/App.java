package com.iluwatar;

public class App {
	
	public static void main( String[] args ) throws Exception {
		
    	try (SlidingDoor slidingDoor = new SlidingDoor()) {
    		System.out.println("Walking in.");
    	}
    	
    	try (TreasureChest treasureChest = new TreasureChest()) {
    		System.out.println("Looting contents.");
    	}
    }
}
