package com.iluwatar;

/**
 * 
 * Visitor pattern defines mechanism to apply operations on nodes
 * in hierarchy. New operations can be added without altering the node
 * interface.
 * 
 * In this example there is a unit hierarchy beginning from Commander.
 * This hierarchy is traversed by visitors. SoldierVisitor applies
 * its operation on Soldiers, SergeantVisitor on Sergeants and so
 * on.
 * 
 */
public class App {

	public static void main(String[] args) {

		Commander commander = new Commander(new Sergeant(new Soldier(),
				new Soldier(), new Soldier()), new Sergeant(new Soldier(),
				new Soldier(), new Soldier()));
		commander.accept(new SoldierVisitor());
		commander.accept(new SergeantVisitor());
		commander.accept(new CommanderVisitor());

	}
}
