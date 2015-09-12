package com.iluwatar.iterator;

/**
 * 
 * Iterator ({@link ItemIterator}) adds abstraction layer on top of a collection
 * ({@link TreasureChest}). This way the collection can change its internal
 * implementation without affecting its clients.
 * 
 */
public class App {

	/**
	 * Program entry point
	 * @param args command line args
	 */
	public static void main(String[] args) {
		TreasureChest chest = new TreasureChest();

		ItemIterator ringIterator = chest.Iterator(ItemType.RING);
		while (ringIterator.hasNext()) {
			System.out.println(ringIterator.next());
		}

		System.out.println("----------");

		ItemIterator potionIterator = chest.Iterator(ItemType.POTION);
		while (potionIterator.hasNext()) {
			System.out.println(potionIterator.next());
		}

		System.out.println("----------");

		ItemIterator weaponIterator = chest.Iterator(ItemType.WEAPON);
		while (weaponIterator.hasNext()) {
			System.out.println(weaponIterator.next());
		}

		System.out.println("----------");

		ItemIterator it = chest.Iterator(ItemType.ANY);
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}
}
