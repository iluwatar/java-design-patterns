package com.iluwatar;

public class Stormbringer extends SoulEatingMagicWeaponImp {

	@Override
	public void wieldImp() {
		System.out.println("wielding Stormbringer");
	}

	@Override
	public void swingImp() {
		System.out.println("swinging Stormbringer");
	}

	@Override
	public void unwieldImp() {
		System.out.println("unwielding Stormbringer");
	}

	@Override
	public void eatSoulImp() {
		System.out.println("Stormbringer devours the enemy's soul");
	}

}
