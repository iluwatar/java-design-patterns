package com.iluwatar;

/**
 * 
 * Action enumeration.
 *
 */
public enum Action {

	HUNT("hunted a rabbit"), TALE("tells a tale"), GOLD("found gold"), ENEMY("spotted enemies"), NONE("");

    private String title;

    Action(String title) {
        this.title = title;
    }

    public String toString() {
		return title;
	}
}
