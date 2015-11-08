package com.iluwatar.specification.creature;

import com.iluwatar.specification.property.Color;
import com.iluwatar.specification.property.Movement;
import com.iluwatar.specification.property.Size;

/**
 * 
 * Shark creature.
 *
 */
public class Shark extends AbstractCreature {

	public Shark() {
		super("Shark", Size.NORMAL, Movement.SWIMMING, Color.LIGHT);
	}
}
