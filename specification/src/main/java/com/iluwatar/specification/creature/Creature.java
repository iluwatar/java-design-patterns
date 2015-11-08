package com.iluwatar.specification.creature;

import com.iluwatar.specification.property.Color;
import com.iluwatar.specification.property.Movement;
import com.iluwatar.specification.property.Size;

/**
 * 
 * Creature interface.
 *
 */
public interface Creature {

	String getName();
	
	Size getSize();
	
	Movement getMovement();
	
	Color getColor();
}
