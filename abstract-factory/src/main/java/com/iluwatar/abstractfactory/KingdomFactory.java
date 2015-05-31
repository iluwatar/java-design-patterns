package com.iluwatar.abstractfactory;

/**
 * 
 * The factory interface.
 * 
 */
public interface KingdomFactory {

	Castle createCastle();

	King createKing();

	Army createArmy();

}
