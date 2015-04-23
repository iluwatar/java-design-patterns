package com.iluwatar.creature;

import com.iluwatar.property.Color;
import com.iluwatar.property.Movement;
import com.iluwatar.property.Size;

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
