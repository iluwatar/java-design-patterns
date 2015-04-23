package com.iluwatar.creature;

import com.iluwatar.property.Color;
import com.iluwatar.property.Movement;
import com.iluwatar.property.Size;

/**
 * 
 * Dragon creature.
 *
 */
public class Dragon extends AbstractCreature {

	public Dragon() {
		super("Dragon", Size.LARGE, Movement.FLYING, Color.RED);
	}
}
