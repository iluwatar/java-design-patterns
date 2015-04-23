package com.iluwatar.selector;

import java.util.function.Predicate;

import com.iluwatar.creature.Creature;
import com.iluwatar.property.Size;

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
