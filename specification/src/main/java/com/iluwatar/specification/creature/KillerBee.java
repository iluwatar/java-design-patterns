package com.iluwatar.specification.creature;

import com.iluwatar.specification.property.Color;
import com.iluwatar.specification.property.Movement;
import com.iluwatar.specification.property.Size;

/**
 * 
 * KillerBee creature.
 *
 */
public class KillerBee extends AbstractCreature {

	public KillerBee() {
		super("KillerBee", Size.SMALL, Movement.FLYING, Color.LIGHT);
	}
}
