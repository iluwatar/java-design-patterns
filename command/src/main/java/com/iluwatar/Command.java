package com.iluwatar;

/**
 * 
 * Interface for Commands.
 * 
 */
public abstract class Command {

	public abstract void execute(Target target);

	public abstract void undo();

	@Override
	public abstract String toString();

}
