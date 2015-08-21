package com.iluwatar.factory.method;

/**
 * 
 * In Factory Method we have an interface ({@link Blacksmith}) with a method for
 * creating objects ({@link Blacksmith#manufactureWeapon}). The concrete subclasses 
 * ({@link OrcBlacksmith}, {@link ElfBlacksmith}) then override the method to produce 
 * objects of their liking.
 * 
 */
public class App {

	/**
	 * Program entry point
	 * @param args command line args
	 */
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
