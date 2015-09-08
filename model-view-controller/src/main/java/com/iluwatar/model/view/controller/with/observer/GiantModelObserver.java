package com.iluwatar.model.view.controller.with.observer;

/**
 * 
 * GiantModelObserver is the interface for delivering update notifications.
 *
 */
public interface GiantModelObserver {
	
	void modelChanged(GiantModel model);

}
