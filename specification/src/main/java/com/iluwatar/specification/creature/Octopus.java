package com.iluwatar.specification.creature;

import com.iluwatar.specification.property.Color;
import com.iluwatar.specification.property.Movement;
import com.iluwatar.specification.property.Size;

/**
 * 
 * Octopus creature.
 *
 */
public class Octopus extends AbstractCreature {

	public Octopus() {
		super("Octopus", Size.NORMAL, Movement.SWIMMING, Color.DARK);
	}
}
