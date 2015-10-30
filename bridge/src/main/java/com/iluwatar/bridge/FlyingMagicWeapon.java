package com.iluwatar.bridge;

/**
 * 
 * FlyingMagicWeapon
 *
 */
public class FlyingMagicWeapon extends MagicWeapon {

	public FlyingMagicWeapon(FlyingMagicWeaponImpl imp) {
		super(imp);
	}

	public FlyingMagicWeaponImpl getImp() {
		return (FlyingMagicWeaponImpl) imp;
	}

	@Override
	public void wield() {
		getImp().wieldImp();
	}

	@Override
	public void swing() {
		getImp().swingImp();
	}

	@Override
	public void unwield() {
		getImp().unwieldImp();
	}

	public void fly() {
		getImp().flyImp();
	}

}
