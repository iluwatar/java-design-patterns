package com.iluwatar;

import java.util.function.Predicate;

public class SizeSelector implements Predicate<Creature> {

	private final Size s;

	public SizeSelector(Size s) {
		this.s = s;
	}
	
	@Override
	public boolean test(Creature t) {
		return t.getSize().equals(s);
	}
}
