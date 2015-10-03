package com.iluwatar.prototype;

/**
 * 
 * The Prototype pattern is a creational design pattern in software development. It is 
 * used when the type of objects to create is determined by a prototypical instance, 
 * which is cloned to produce new objects. This pattern is used to:
 * - avoid subclasses of an object creator in the client application, like the abstract factory pattern does.
 * - avoid the inherent cost of creating a new object in the standard way (e.g., using the 'new' keyword)
 * <p>
 * In this example we have a factory class ({@link HeroFactoryImpl}) producing objects by
 * cloning the existing ones. The factory's prototype objects are given as constructor parameters.
 * 
 */
public class App {

	/**
	 * Program entry point
	 * @param args command line args
	 */
	public static void main(String[] args) {
		HeroFactory factory;
		Mage mage;
		Warlord warlord;
		Beast beast;

		factory = new HeroFactoryImpl(new ElfMage(), new ElfWarlord(),
				new ElfBeast());
		mage = factory.createMage();
		warlord = factory.createWarlord();
		beast = factory.createBeast();
		System.out.println(mage);
		System.out.println(warlord);
		System.out.println(beast);

		factory = new HeroFactoryImpl(new OrcMage(), new OrcWarlord(),
				new OrcBeast());
		mage = factory.createMage();
		warlord = factory.createWarlord();
		beast = factory.createBeast();
		System.out.println(mage);
		System.out.println(warlord);
		System.out.println(beast);
	}
}
