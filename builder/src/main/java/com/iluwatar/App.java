package com.iluwatar;

import com.iluwatar.Hero.HeroBuilder;

public class App 
{
    public static void main( String[] args )
    {
    	
    	Hero mage = new HeroBuilder(Profession.MAGE, "Riobard")
    		.withHairColor(HairColor.BLACK)
    		.withWeapon(Weapon.DAGGER)
    		.build();
    	System.out.println(mage);

		Hero warrior = new HeroBuilder(Profession.WARRIOR, "Amberjill")
			.withHairColor(HairColor.BLOND)
			.withHairType(HairType.LONG_CURLY)
			.withArmor(Armor.CHAIN_MAIL)
			.withWeapon(Weapon.SWORD)
			.build();
		System.out.println(warrior);

		Hero thief = new HeroBuilder(Profession.THIEF, "Desmond")
			.withHairType(HairType.BOLD)
			.withWeapon(Weapon.BOW)
			.build();
		System.out.println(thief);
	
    }
}
