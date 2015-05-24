package com.iluwatar;

public class Oliphaunt {
	
	private static int counter = 1;
	
	private final int id;
	
	public Oliphaunt() {
		id = counter++;
	}
	
	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return String.format("Oliphaunt id=%d", id);
	}
}
