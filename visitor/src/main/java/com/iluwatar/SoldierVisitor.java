package com.iluwatar;

public class SoldierVisitor implements UnitVisitor {

	@Override
	public void visitSoldier(Soldier soldier) {
		System.out.println("Greetings " + soldier);
	}

	@Override
	public void visitSergeant(Sergeant sergeant) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitCommander(Commander commander) {
		// TODO Auto-generated method stub

	}

}
