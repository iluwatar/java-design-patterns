package com.iluwatar.command;

/**
 * 
 * Enumeration for target visibility.
 *
 */
public enum Visibility {

	VISIBLE("visible"), INVISIBLE("invisible"), UNDEFINED("");

    private String title;

    Visibility(String title) {
        this.title = title;
    }

    @Override
	public String toString() {
		return title;
	}
}
