package com.iluwatar.specification.property;

/**
 * 
 * Color property.
 *
 */
public enum Color {

    DARK("dark"), LIGHT("light"), GREEN("green"), RED("red");
    
    private String title;

    Color(String title) {
        this.title = title;
    }

    @Override
	public String toString() {
        return title;
	}		
}
