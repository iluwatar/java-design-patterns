package com.iluwatar;

import java.util.ArrayList;
import java.util.List;

public class AlchemistShop {

	List<Potion> topShelf;
	List<Potion> bottomShelf;
	
	public AlchemistShop() {
		topShelf = new ArrayList<>();
		bottomShelf = new ArrayList<>();
		fillShelves();
	}

	private void fillShelves() {
		topShelf.add(new InvisibilityPotion());
		topShelf.add(new InvisibilityPotion());
		topShelf.add(new StrengthPotion());
		topShelf.add(new HealingPotion());
		topShelf.add(new InvisibilityPotion());
		topShelf.add(new StrengthPotion());
		topShelf.add(new HealingPotion());
		topShelf.add(new HealingPotion());
		
		bottomShelf.add(new PoisonPotion());
		bottomShelf.add(new PoisonPotion());
		bottomShelf.add(new PoisonPotion());
		bottomShelf.add(new HolyWaterPotion());
		bottomShelf.add(new HolyWaterPotion());
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
