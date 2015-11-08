package com.iluwatar.specification.selector;

import java.util.function.Predicate;

import com.iluwatar.specification.creature.Creature;
import com.iluwatar.specification.property.Color;

/**
 * 
 * Color selector.
 *
 */
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
