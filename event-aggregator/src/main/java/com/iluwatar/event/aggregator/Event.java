package com.iluwatar.event.aggregator;

/**
 * 
 * Event enumeration.
 *
 */
public enum Event {

	STARK_SIGHTED("Stark sighted"), WARSHIPS_APPROACHING("Warships approaching"), TRAITOR_DETECTED("Traitor detected");
	
	private String description;
	
	Event(String description) {
		this.description = description;	
	}
	
    public String toString() {
        return description;
    }
}
