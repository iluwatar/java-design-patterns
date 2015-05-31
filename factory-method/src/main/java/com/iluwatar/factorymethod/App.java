package com.iluwatar.factorymethod;

/**
 * 
 * In Factory Method we have an interface (Blacksmith) with a method for
 * creating objects (manufactureWeapon). The concrete subclasses (OrcBlacksmith,
 * ElfBlacksmith) then override the method to produce objects of their liking.
 * 
 */
public class App {

	public static void main(String[] args) {
		Blacksmith blacksmith;
		Weapon weapon;

		blacksmith = new OrcBlacksmith();
		weapon = blacksmith.manufactureWeapon(WeaponType.SPEAR);
		System.out.println(weapon);
		weapon = blacksmith.manufactureWeapon(WeaponType.AXE);
		System.out.println(weapon);

		blacksmith = new ElfBlacksmith();
		weapon = blacksmith.manufactureWeapon(WeaponType.SHORT_SWORD);
		System.out.println(weapon);
		weapon = blacksmith.manufactureWeapon(WeaponType.SPEAR);
		System.out.println(weapon);
	}
}
