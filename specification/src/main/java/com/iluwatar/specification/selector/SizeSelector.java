package com.iluwatar.specification.selector;

import java.util.function.Predicate;

import com.iluwatar.specification.creature.Creature;
import com.iluwatar.specification.property.Size;

/**
 * 
 * Size selector.
 *
 */
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
