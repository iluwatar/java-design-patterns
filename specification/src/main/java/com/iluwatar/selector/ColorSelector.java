package com.iluwatar.selector;

import java.util.function.Predicate;

import com.iluwatar.creature.Creature;
import com.iluwatar.property.Color;

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
