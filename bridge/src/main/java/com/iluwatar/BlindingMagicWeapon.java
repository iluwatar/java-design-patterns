package com.iluwatar;

public class BlindingMagicWeapon extends MagicWeapon {

	public BlindingMagicWeapon(BlindingMagicWeaponImp imp) {
		super(imp);
	}

	@Override
	public BlindingMagicWeaponImp getImp() {
		return (BlindingMagicWeaponImp) imp;
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
