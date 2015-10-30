package com.iluwatar.prototype;

/**
 * 
 * OrcWarlord
 *
 */
public class OrcWarlord extends Warlord {

	public OrcWarlord() {
	}

	public OrcWarlord(OrcWarlord warlord) {
	}

	@Override
	public Warlord clone() throws CloneNotSupportedException {
		return new OrcWarlord(this);
	}

	@Override
	public String toString() {
		return "Orcish warlord";
	}

}
