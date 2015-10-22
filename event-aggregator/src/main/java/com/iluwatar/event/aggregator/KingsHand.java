package com.iluwatar.event.aggregator;

/**
 * 
 * KingsHand observes events from multiple sources and delivers them
 * to listeners.
 *
 */
public class KingsHand extends EventEmitter implements EventObserver {

	public KingsHand() {
		super();
	}

	public KingsHand(EventObserver obs) {
		super(obs);
	}
	
	@Override
	public void onEvent(Event e) {
		notifyObservers(e);
	}

	@Override
	public void timePasses(Weekday day) {
		// NOP
	}
}
