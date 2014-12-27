package com.iluwatar;

/**
 * 
 * Enumeration for target size.
 *
 */
public enum Size {

	SMALL, NORMAL, LARGE;

	@Override
	public String toString() {

		String s = "";

		switch (this) {
		case LARGE:
			s = "large";
			break;
		case NORMAL:
			s = "normal";
			break;
		case SMALL:
			s = "small";
			break;
		default:
			break;
		}
		return s;
	}
}
