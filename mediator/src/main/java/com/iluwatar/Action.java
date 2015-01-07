package com.iluwatar;

/**
 * 
 * Action enumeration.
 *
 */
public enum Action {

	HUNT, TALE, GOLD, ENEMY;

	public String toString() {

		switch (this) {
		case ENEMY:
			return "spotted enemies";
		case GOLD:
			return "found gold";
		case HUNT:
			return "hunted a rabbit";
		case TALE:
			return "tells a tale";
		}
		return "";
	}
}
