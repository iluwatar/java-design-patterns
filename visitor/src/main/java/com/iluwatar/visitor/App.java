package com.iluwatar.visitor;

/**
 * 
 * Visitor pattern defines mechanism to apply operations on nodes
 * in hierarchy. New operations can be added without altering the node
 * interface.
 * <p>
 * In this example there is a unit hierarchy beginning from {@link Commander}.
 * This hierarchy is traversed by visitors. {@link SoldierVisitor} applies
 * its operation on {@link Soldier}s, {@link SergeantVisitor} on {@link Sergeant}s and so
 * on.
 * 
 */
public class App {

	/**
	 * Program entry point
	 * @param args command line args
	 */
	public static void main(String[] args) {

		Commander commander = new Commander(new Sergeant(new Soldier(),
				new Soldier(), new Soldier()), new Sergeant(new Soldier(),
				new Soldier(), new Soldier()));
		commander.accept(new SoldierVisitor());
		commander.accept(new SergeantVisitor());
		commander.accept(new CommanderVisitor());

	}
}
