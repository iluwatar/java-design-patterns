package com.iluwatar;

/**
 * 
 * Template Method defines a skeleton for an algorithm. The algorithm subclasses 
 * provide implementation for the blank parts.
 * 
 * In this example HalflingThief contains StealingMethod that can be changed. 
 * First the thief hits with HitAndRunMethod and then with SubtleMethod.
 * 
 */
public class App {

	public static void main(String[] args) {
		HalflingThief thief = new HalflingThief(new HitAndRunMethod());
		thief.steal();
		thief.changeMethod(new SubtleMethod());
		thief.steal();
	}
}
