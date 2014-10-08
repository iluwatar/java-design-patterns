package com.iluwatar;

public enum Profession {

	WARRIOR, THIEF, MAGE, PRIEST;

	@Override
	public String toString() {
		String s = "";
		switch (this) {
		case WARRIOR:
			s = "Warrior";
			break;
		case THIEF:
			s = "Thief";
			break;
		case MAGE:
			s = "Mage";
			break;
		case PRIEST:
			s = "Priest";
			break;
		}
		return s;
	}

}
