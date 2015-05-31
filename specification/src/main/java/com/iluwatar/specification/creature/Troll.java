package com.iluwatar.specification.creature;

import com.iluwatar.specification.property.Color;
import com.iluwatar.specification.property.Movement;
import com.iluwatar.specification.property.Size;

/**
 * 
 * Troll creature.
 *
 */
public class Troll extends AbstractCreature {
	
	public Troll() {
		super("Troll", Size.LARGE, Movement.WALKING, Color.DARK);
	}
}
