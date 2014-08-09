package com.iluwatar;

public enum Armor {

	CLOTHES, LEATHER, CHAIN_MAIL, PLATE_MAIL;
	
	@Override
	public String toString() {
		String s = "";
		switch(this) {
		case CLOTHES: s = "clothes"; break;
		case LEATHER: s = "leather armor"; break;
		case CHAIN_MAIL: s = "chain mail"; break;
		case PLATE_MAIL: s = "plate mail"; break;
		}
		return s;
	}
	
}
