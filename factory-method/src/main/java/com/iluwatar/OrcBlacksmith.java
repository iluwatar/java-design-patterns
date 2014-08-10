package com.iluwatar;

public class OrcBlacksmith implements Blacksmith {

	public Weapon manufactureWeapon(WeaponType weaponType) {
		return new OrcWeapon(weaponType);
	}

}
