package com.iluwatar;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * The class that needs many objects.
 *
 */
public class AlchemistShop {

	List<Potion> topShelf;
	List<Potion> bottomShelf;
	
	public AlchemistShop() {
		topShelf = new ArrayList<>();
		bottomShelf = new ArrayList<>();
		fillShelves();
	}

	private void fillShelves() {
		
		PotionFactory factory = new PotionFactory();
		
		topShelf.add(factory.createPotion(PotionType.INVISIBILITY));
		topShelf.add(factory.createPotion(PotionType.INVISIBILITY));
		topShelf.add(factory.createPotion(PotionType.STRENGTH));
		topShelf.add(factory.createPotion(PotionType.HEALING));
		topShelf.add(factory.createPotion(PotionType.INVISIBILITY));
		topShelf.add(factory.createPotion(PotionType.STRENGTH));
		topShelf.add(factory.createPotion(PotionType.HEALING));
		topShelf.add(factory.createPotion(PotionType.HEALING));
		
		bottomShelf.add(factory.createPotion(PotionType.POISON));
		bottomShelf.add(factory.createPotion(PotionType.POISON));
		bottomShelf.add(factory.createPotion(PotionType.POISON));
		bottomShelf.add(factory.createPotion(PotionType.HOLY_WATER));
		bottomShelf.add(factory.createPotion(PotionType.HOLY_WATER));
	}
	
	public void enumerate() {

		System.out.println("Enumerating top shelf potions\n");
		
		for (Potion p: topShelf) {
			p.drink();
		}
		
		System.out.println("\nEnumerating bottom shelf potions\n");
		
		for (Potion p: bottomShelf) {
			p.drink();
		}
		
	}
}
