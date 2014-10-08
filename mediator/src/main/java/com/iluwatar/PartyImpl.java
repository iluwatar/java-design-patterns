package com.iluwatar;

import java.util.ArrayList;
import java.util.List;

public class PartyImpl implements Party {

	private List<PartyMember> members;

	public PartyImpl() {
		members = new ArrayList<>();
	}

	@Override
	public void act(PartyMember actor, Action action) {
		for (PartyMember member : members) {
			if (member != actor) {
				member.partyAction(action);
			}
		}
	}

	@Override
	public void addMember(PartyMember member) {
		members.add(member);
		member.joinedParty(this);
	}

	// somebody hunts for food, call for dinner

	// somebody spots enemy, alert everybody

	// somebody finds gold, deal the gold with everybody

	// somebody tells a tale, call everybody to listen

}
