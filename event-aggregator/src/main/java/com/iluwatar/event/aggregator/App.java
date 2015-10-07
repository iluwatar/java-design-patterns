package com.iluwatar.event.aggregator;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * A system with lots of objects can lead to complexities when a client wants to subscribe 
 * to events. The client has to find and register for each object individually, if each 
 * object has multiple events then each event requires a separate subscription.
 * <p>
 * An Event Aggregator acts as a single source of events for many objects. It registers 
 * for all the events of the many objects allowing clients to register with just the aggregator.
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
