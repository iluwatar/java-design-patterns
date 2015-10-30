package com.iluwatar.mediator;

/**
 * 
 * Interface for party members interacting with {@link Party}.
 * 
 */
public interface PartyMember {

	void joinedParty(Party party);

	void partyAction(Action action);

	void act(Action action);
}
