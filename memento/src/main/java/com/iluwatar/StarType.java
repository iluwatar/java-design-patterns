package com.iluwatar;

public enum StarType {

	SUN, RED_GIANT, WHITE_DWARF, SUPERNOVA, DEAD;

	@Override
	public String toString() {
		String s = "";
		switch (this) {
		case RED_GIANT:
			s = "red giant";
			break;
		case SUN:
			s = "sun";
			break;
		case SUPERNOVA:
			s = "supernova";
			break;
		case WHITE_DWARF:
			s = "white dwarf";
			break;
		case DEAD:
			s = "dead star";
			break;
		default:
			break;
		}
		return s;
	}

}
