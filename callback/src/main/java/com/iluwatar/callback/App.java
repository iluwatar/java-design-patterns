package com.iluwatar.callback;

/**
 * Callback pattern is more native for functional languages where function is treated as first-class citizen.
 * Prior to Java8 can be simulated using simple (alike command) interfaces.
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
