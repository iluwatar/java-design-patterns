package com.iluwatar.eventaggregator;

/**
 * 
 * Observers of events implement this interface.
 *
 */
public interface EventObserver {
	
	void onEvent(Event e);

}
