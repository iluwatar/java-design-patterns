package com.iluwatar.creature;

import com.iluwatar.property.Color;
import com.iluwatar.property.Movement;
import com.iluwatar.property.Size;

public class Troll extends AbstractCreature {
	
	public Troll() {
		super("Troll", Size.LARGE, Movement.WALKING, Color.DARK);
	}
}
