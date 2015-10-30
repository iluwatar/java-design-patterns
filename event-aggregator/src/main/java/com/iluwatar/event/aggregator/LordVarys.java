package com.iluwatar.event.aggregator;

/**
 * 
 * LordVarys produces events.
 *
 */
public class LordVarys extends EventEmitter {
	
	public LordVarys() {
		super();
	}

	public LordVarys(EventObserver obs) {
		super(obs);
	}
	
	@Override
	public void timePasses(Weekday day) {
		if (day.equals(Weekday.SATURDAY)) {
			notifyObservers(Event.TRAITOR_DETECTED);
		}
	}
}
