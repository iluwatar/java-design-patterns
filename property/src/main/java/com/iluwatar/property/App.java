package com.iluwatar.property;

import com.iluwatar.property.Character.Type;

/**
 * 
 * Example of {@link Character} instantiation using the Property pattern (also known as Prototype inheritance).
 * <p>
 * In prototype inheritance instead of classes, as opposite to Java class inheritance,
 * objects are used to create another objects and object hierarchies. Hierarchies are created using prototype chain 
 * through delegation: every object has link to parent object. Any base (parent) object can be amended at runtime 
 * (by adding or removal of some property), and all child objects will be affected as result.
 * 
 */
public class App {

	/**
	 * Program entry point
	 * @param args command line args
	 */
	public static void main(String[] args) {
		/* set up */
		Prototype charProto = new Character();
		charProto.set(Stats.STRENGTH, 10);
		charProto.set(Stats.AGILITY, 10);
		charProto.set(Stats.ARMOR, 10);
		charProto.set(Stats.ATTACK_POWER, 10);

		Character mageProto = new Character(Type.MAGE, charProto);
		mageProto.set(Stats.INTELLECT, 15);
		mageProto.set(Stats.SPIRIT, 10);

		Character warProto = new Character(Type.WARRIOR, charProto);
		warProto.set(Stats.RAGE, 15);
		warProto.set(Stats.ARMOR, 15); // boost default armor for warrior

		Character rogueProto = new Character(Type.ROGUE, charProto);
		rogueProto.set(Stats.ENERGY, 15);
		rogueProto.set(Stats.AGILITY, 15); // boost default agility for rogue

		/* usage */
		Character mag = new Character("Player_1", mageProto);
		mag.set(Stats.ARMOR, 8);
		System.out.println(mag);

		Character warrior = new Character("Player_2", warProto);
		System.out.println(warrior);

		Character rogue = new Character("Player_3", rogueProto);
		System.out.println(rogue);

		Character rogueDouble = new Character("Player_4", rogue);
		rogueDouble.set(Stats.ATTACK_POWER, 12);
		System.out.println(rogueDouble);
	}
}
