package com.iluwatar;

public enum Weapon {

	DAGGER, SWORD, AXE, WARHAMMER, BOW;
	
	@Override
	public String toString() {
		String s = "";
		switch(this) {
		case DAGGER: s = "dagger"; break;
		case SWORD: s = "sword"; break;
		case AXE: s = "axe"; break;
		case WARHAMMER: s = "warhammer"; break;
		case BOW: s = "bow"; break;
		}
		return s;
	}
	
}
