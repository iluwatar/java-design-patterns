package com.iluwatar.event.aggregator;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * The Event Aggregator pattern channels events from multiple objects 
 * into a single object to simplify registration for clients.
 * <p>
 * In the example {@link LordBaelish}, {@link LordVarys} and {@link Scout} deliver events to
 * {@link KingsHand}. {@link KingsHand}, the event aggregator, then delivers the events
 * to {@link KingJoffrey}.
 *
 */
public class App {

	/**
	 * Program entry point
	 * @param args command line args
	 */
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
