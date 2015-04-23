package com.iluwatar.creature;

import com.iluwatar.property.Color;
import com.iluwatar.property.Movement;
import com.iluwatar.property.Size;

public class Goblin extends AbstractCreature {

	public Goblin() {
		super("Goblin", Size.SMALL, Movement.WALKING, Color.GREEN);
	}
}
