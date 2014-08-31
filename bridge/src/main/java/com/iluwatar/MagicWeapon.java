package com.iluwatar;

/**
 * 
 * Abstraction interface.
 *
 */
public abstract class MagicWeapon {

	protected MagicWeaponImp imp;
	
	public MagicWeapon(MagicWeaponImp imp) {
		this.imp = imp;
	}
	
	public abstract void wield();
	
	public abstract void swing();
	
	public abstract void unwield();
	
	public MagicWeaponImp getImp() {
		return imp;
	}
	
}
