package com.iluwatar;

/**
 * 
 * Abstract base class for party members.
 *
 */
public abstract class PartyMemberBase implements PartyMember {

	protected Party party;

	@Override
	public void joinedParty(Party party) {
		System.out.println(this + " joins the party");
		this.party = party;
	}

	@Override
	public void partyAction(Action action) {
		String s = this + " ";
		switch (action) {
		case ENEMY:
			s = s + "runs for cover";
			break;
		case GOLD:
			s = s + "takes his share of the gold";
			break;
		case HUNT:
			s = s + "arrives for dinner";
			break;
		case TALE:
			s = s + "comes to listen";
			break;
		default:
			break;
		}
		System.out.println(s);
	}

	@Override
	public void act(Action action) {
		if (party != null) {
			System.out.println(this + " " + action.toString());
			party.act(this, action);
		}
	}

	@Override
	public abstract String toString();

}
