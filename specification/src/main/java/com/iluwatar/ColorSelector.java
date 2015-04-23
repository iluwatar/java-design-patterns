package com.iluwatar;

import java.util.function.Predicate;

public class ColorSelector implements Predicate<Creature> {

	private final Color c;

	public ColorSelector(Color c) {
		this.c = c;
	}
	
	@Override
	public boolean test(Creature t) {
		return t.getColor().equals(c);
	}
}
