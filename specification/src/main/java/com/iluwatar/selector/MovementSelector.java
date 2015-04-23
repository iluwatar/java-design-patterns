package com.iluwatar.selector;

import java.util.function.Predicate;

import com.iluwatar.creature.Creature;
import com.iluwatar.property.Movement;

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
