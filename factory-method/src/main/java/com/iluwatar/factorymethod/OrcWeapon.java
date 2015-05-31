package com.iluwatar.factorymethod;

public class OrcWeapon implements Weapon {

	private WeaponType weaponType;

	public OrcWeapon(WeaponType weaponType) {
		this.weaponType = weaponType;
	}

	@Override
	public String toString() {
		return "Orcish " + weaponType;
	}

}
