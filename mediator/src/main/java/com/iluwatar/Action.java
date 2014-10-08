package com.iluwatar;

public enum Action {

	HUNT, TALE, GOLD, ENEMY;

	public String toString() {

		String s = "";
		switch (this) {
		case ENEMY:
			s = "spotted enemies";
			break;
		case GOLD:
			s = "found gold";
			break;
		case HUNT:
			s = "hunted a rabbit";
			break;
		case TALE:
			s = "tells a tale";
			break;
		default:
			break;
		}
		return s;
	};
}
