package com.iluwatar.model.view.controller.with.observer;

/**
 * 
 * GiantView displays the giant
 *
 */
public class GiantView implements GiantModelObserver {

	public void displayGiant(GiantModel giant) {
		System.out.println(giant);
	}

	@Override
	public void modelChanged(GiantModel model) {
		displayGiant(model);
	}
}
