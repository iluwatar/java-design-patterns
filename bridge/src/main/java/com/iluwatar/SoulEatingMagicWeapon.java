package com.iluwatar;

public class SoulEatingMagicWeapon extends MagicWeapon {

	public SoulEatingMagicWeapon(SoulEatingMagicWeaponImp imp) {
		super(imp);
	}
	
	@Override
	public SoulEatingMagicWeaponImp getImp() {
		return (SoulEatingMagicWeaponImp) imp;
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
