package com.iluwatar.creature;

import com.iluwatar.property.Color;
import com.iluwatar.property.Movement;
import com.iluwatar.property.Size;

public abstract class AbstractCreature implements Creature {

	private String name;
	private Size size;
	private Movement movement;
	private Color color;

	public AbstractCreature(String name, Size size, Movement movement, Color color) {
		this.name = name;
		this.size = size;
		this.movement = movement;
		this.color = color;
	}	
	
	@Override
	public String toString() {
		return String.format("%s [size=%s, movement=%s, color=%s]", name, size, movement, color);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public Size getSize() {
		return size;
	}
	
	@Override
	public Movement getMovement() {
		return movement;
	}
	
	@Override
	public Color getColor() {
		return color;
	}
}
