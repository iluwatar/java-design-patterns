package com.iluwatar.creature;

import com.iluwatar.property.Color;
import com.iluwatar.property.Movement;
import com.iluwatar.property.Size;

public class Octopus extends AbstractCreature {

	public Octopus() {
		super("Octopus", Size.NORMAL, Movement.SWIMMING, Color.DARK);
	}
}
