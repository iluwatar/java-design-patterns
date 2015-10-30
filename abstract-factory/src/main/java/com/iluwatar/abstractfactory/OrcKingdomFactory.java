package com.iluwatar.abstractfactory;

/**
 * 
 * OrcKingdomFactory concrete factory.
 * 
 */
public class OrcKingdomFactory implements KingdomFactory {

	public Castle createCastle() {
		return new OrcCastle();
	}

	public King createKing() {
		return new OrcKing();
	}

	public Army createArmy() {
		return new OrcArmy();
	}

}
