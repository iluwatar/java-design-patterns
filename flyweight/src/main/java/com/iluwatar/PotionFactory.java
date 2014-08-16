package com.iluwatar;

import java.util.EnumMap;

public class PotionFactory {

	private EnumMap<PotionType, Potion> potions;
	
	public PotionFactory() {
		potions = new EnumMap<>(PotionType.class);
	}
	
	Potion createPotion(PotionType type) {
		Potion potion = potions.get(type);
		if (potion == null) {
			switch (type) {
			case HEALING:
				potion = new HealingPotion();
				break;
			case HOLY_WATER:
				potion = new HolyWaterPotion();
				break;
			case INVISIBILITY:
				potion = new InvisibilityPotion();
				break;
			case POISON:
				potion = new PoisonPotion();
				break;
			case STRENGTH:
				potion = new StrengthPotion();
				break;
			default:
				break;
			}
		}
		return potion;
	}
	
}
