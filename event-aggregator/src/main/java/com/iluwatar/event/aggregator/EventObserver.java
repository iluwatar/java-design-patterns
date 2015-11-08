package com.iluwatar.event.aggregator;

/**
 * 
 * Observers of events implement this interface.
 *
 */
public interface EventObserver {
	
	void onEvent(Event e);

}
