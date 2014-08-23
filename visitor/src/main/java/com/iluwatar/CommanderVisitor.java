package com.iluwatar;

public class CommanderVisitor implements UnitVisitor {

	@Override
	public void visitSoldier(Soldier soldier) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitSergeant(Sergeant sergeant) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitCommander(Commander commander) {
		System.out.println("Good to see you " + commander);
	}

}
