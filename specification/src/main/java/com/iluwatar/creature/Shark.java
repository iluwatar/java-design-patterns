package com.iluwatar.creature;

import com.iluwatar.property.Color;
import com.iluwatar.property.Movement;
import com.iluwatar.property.Size;

public class Shark extends AbstractCreature {

	public Shark() {
		super("Shark", Size.NORMAL, Movement.SWIMMING, Color.LIGHT);
	}
}
