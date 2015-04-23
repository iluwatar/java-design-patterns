package com.iluwatar;

import java.util.function.Predicate;

public class MovementSelector implements Predicate<Creature> {
	
	private final Movement m;

	public MovementSelector(Movement m) {
		this.m = m;
	}

	@Override
	public boolean test(Creature t) {
		return t.getMovement().equals(m);
	}
}
