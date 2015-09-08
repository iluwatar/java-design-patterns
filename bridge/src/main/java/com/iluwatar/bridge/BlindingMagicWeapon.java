package com.iluwatar.bridge;

/**
 * 
 * BlindingMagicWeapon
 *
 */
public class BlindingMagicWeapon extends MagicWeapon {

	public BlindingMagicWeapon(BlindingMagicWeaponImpl imp) {
		super(imp);
	}

	@Override
	public BlindingMagicWeaponImpl getImp() {
		return (BlindingMagicWeaponImpl) imp;
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

	public void blind() {
		getImp().blindImp();
	}

}
