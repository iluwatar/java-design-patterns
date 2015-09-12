package com.iluwatar.bridge;

/**
 * 
 * SoulEatingMagicWeapon
 *
 */
public class SoulEatingMagicWeapon extends MagicWeapon {

	public SoulEatingMagicWeapon(SoulEatingMagicWeaponImpl imp) {
		super(imp);
	}

	@Override
	public SoulEatingMagicWeaponImpl getImp() {
		return (SoulEatingMagicWeaponImpl) imp;
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

	public void eatSoul() {
		getImp().eatSoulImp();
	}

}
