package com.iluwatar;

/**
 *
 * Enumeration for creature size.
 *
 */
public enum Size {

    SMALL("small"), NORMAL("normal"), LARGE("large");
    
    private String title;

    Size(String title) {
        this.title = title;
    }

    @Override
	public String toString() {
        return title;
	}
}
