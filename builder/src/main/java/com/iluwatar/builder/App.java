package com.iluwatar.builder;

import com.iluwatar. builder.Hero.HeroBuilder;

/**
 * 
 * The intention of the Builder pattern is to find a solution to the telescoping 
 * constructor anti-pattern. The telescoping constructor anti-pattern occurs when the 
 * increase of object constructor parameter combination leads to an exponential list 
 * of constructors. Instead of using numerous constructors, the builder pattern uses 
 * another object, a builder, that receives each initialization parameter step by step 
 * and then returns the resulting constructed object at once.
 * <p>
 * The Builder pattern has another benefit. It can be used for objects that contain 
 * flat data (html code, SQL query, X.509 certificate...), that is to say, data that 
 * can't be easily edited. This type of data cannot be edited step by step and must 
 * be edited at once. The best way to construct such an object is to use a builder 
 * class.
 * <p>
 * In this example we have the Builder pattern variation as described by Joshua Bloch in
 * Effective Java 2nd Edition.
 * <p>
 * We want to build {@link Hero} objects, but its construction is complex because of the
 * many parameters needed. To aid the user we introduce {@link HeroBuilder} class.
 * {@link HeroBuilder} takes the minimum parameters to build {@link Hero} object in its
 * constructor. After that additional configuration for the {@link Hero} object can be
 * done using the fluent {@link HeroBuilder} interface. When configuration is ready the
 * build method is called to receive the final {@link Hero} object.
 * 
 */
public class App {

	/**
	 * Program entry point
	 * @param args command line args
	 */
	public static void main(String[] args) {

		Hero mage = new HeroBuilder(Profession.MAGE, "Riobard")
				.withHairColor(HairColor.BLACK).withWeapon(Weapon.DAGGER)
				.build();
		System.out.println(mage);

		Hero warrior = new HeroBuilder(Profession.WARRIOR, "Amberjill")
				.withHairColor(HairColor.BLOND)
				.withHairType(HairType.LONG_CURLY).withArmor(Armor.CHAIN_MAIL)
				.withWeapon(Weapon.SWORD).build();
		System.out.println(warrior);

		Hero thief = new HeroBuilder(Profession.THIEF, "Desmond")
				.withHairType(HairType.BALD).withWeapon(Weapon.BOW).build();
		System.out.println(thief);

	}
}
