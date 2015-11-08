package com.iluwatar.callback;

/**
 * 
 * Callback pattern is more native for functional languages where functions are treated as first-class citizens.
 * Prior to Java 8 callbacks can be simulated using simple (alike command) interfaces.
 * 
 */
public class App {

	public static void main(String[] args) {
		Task task = new SimpleTask();
		Callback callback = new Callback() {
			@Override
			public void call() {
				System.out.println("I'm done now.");
			}
		};
		task.executeWith(callback);
	}
}
