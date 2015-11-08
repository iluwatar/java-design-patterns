package com.iluwatar.event.aggregator;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * EventEmitter is the base class for event producers that can be observed.
 *
 */
public abstract class EventEmitter {

	private List<EventObserver> observers;

	public EventEmitter() {
		observers = new LinkedList<>();
	}

	public EventEmitter(EventObserver obs) {
		this();
		registerObserver(obs);
	}
	
	public void registerObserver(EventObserver obs) {
		observers.add(obs);
	}
	
	protected void notifyObservers(Event e) {
		for (EventObserver obs: observers) {
			obs.onEvent(e);
		}
	}
	
	public abstract void timePasses(Weekday day);
}
