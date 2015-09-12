package com.iluwatar.bridge;

/**
 * 
 * MagicWeapon
 * 
 */
public abstract class MagicWeapon {

	protected MagicWeaponImpl imp;

	public MagicWeapon(MagicWeaponImpl imp) {
		this.imp = imp;
	}

	public abstract void wield();

	public abstract void swing();

	public abstract void unwield();

	public MagicWeaponImpl getImp() {
		return imp;
	}

}
