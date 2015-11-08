package com.iluwatar.visitor;

/**
 * 
 * SoldierVisitor
 *
 */
public class SoldierVisitor implements UnitVisitor {

	@Override
	public void visitSoldier(Soldier soldier) {
		System.out.println("Greetings " + soldier);
	}

	@Override
	public void visitSergeant(Sergeant sergeant) {
	}

	@Override
	public void visitCommander(Commander commander) {
	}

}
