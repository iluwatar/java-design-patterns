package com.iluwatar.specification.creature;

import com.iluwatar.specification.property.Color;
import com.iluwatar.specification.property.Movement;
import com.iluwatar.specification.property.Size;

/**
 * 
 * Goblin creature.
 *
 */
public class Goblin extends AbstractCreature {

	public Goblin() {
		super("Goblin", Size.SMALL, Movement.WALKING, Color.GREEN);
	}
}
