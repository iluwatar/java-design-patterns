package com.iluwatar;

public class SergeantVisitor implements UnitVisitor {

	@Override
	public void visitSoldier(Soldier soldier) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitSergeant(Sergeant sergeant) {
		System.out.println("Hello " + sergeant);
	}

	@Override
	public void visitCommander(Commander commander) {
		// TODO Auto-generated method stub

	}

}
