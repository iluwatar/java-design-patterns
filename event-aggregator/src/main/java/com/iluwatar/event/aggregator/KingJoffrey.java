package com.iluwatar.event.aggregator;

/**
 * 
 * KingJoffrey observes events from {@link KingsHand}.
 *
 */
public class KingJoffrey implements EventObserver {

	@Override
	public void onEvent(Event e) {
		System.out.println("Received event from the King's Hand: " + e.toString());
	}
}
