package com.iluwatar.mediator;

/**
 * 
 * Interface for party members interacting with Party.
 * 
 */
public interface PartyMember {

	void joinedParty(Party party);

	void partyAction(Action action);

	void act(Action action);
}
