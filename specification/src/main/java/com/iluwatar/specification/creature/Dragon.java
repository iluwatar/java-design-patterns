package com.iluwatar.specification.creature;

import com.iluwatar.specification.property.Color;
import com.iluwatar.specification.property.Movement;
import com.iluwatar.specification.property.Size;

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
