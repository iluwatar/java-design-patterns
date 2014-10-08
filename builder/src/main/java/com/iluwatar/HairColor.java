package com.iluwatar;

public enum HairColor {

	WHITE, BLOND, RED, BROWN, BLACK;

	@Override
	public String toString() {
		String s = "";
		switch (this) {
		case WHITE:
			s = "white";
			break;
		case BLOND:
			s = "blond";
			break;
		case RED:
			s = "red";
			break;
		case BROWN:
			s = "brown";
			break;
		case BLACK:
			s = "black";
			break;
		}
		return s;
	}

}
