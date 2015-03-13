package com.iluwatar;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * The Event Aggregator pattern channels events from multiple objects 
 * into a single object to simplify registration for clients.
 * 
 * In the example LordBaelish, LordVarys and Scout deliver events to
 * KingsHand. KingsHand, the event aggregator, then delivers the events
 * to KingJoffrey.
 *
 */
public class App {

	public static void main(String[] args) {
		
		KingJoffrey kingJoffrey = new KingJoffrey();
		KingsHand kingsHand = new KingsHand(kingJoffrey);

		List<EventEmitter> emitters = new ArrayList<>();
		emitters.add(kingsHand);
		emitters.add(new LordBaelish(kingsHand));
		emitters.add(new LordVarys(kingsHand));
		emitters.add(new Scout(kingsHand));
		
		for (Weekday day: Weekday.values()) {
			for (EventEmitter emitter: emitters) {
				emitter.timePasses(day);
			}
		}
	}
}
