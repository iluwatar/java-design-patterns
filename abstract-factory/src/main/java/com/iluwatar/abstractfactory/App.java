package com.iluwatar.abstractfactory;

/**
 * 
 * The essence of the Abstract Factory pattern is a factory interface
 * ({@link KingdomFactory}) and its implementations ({@link ElfKingdomFactory},
 * {@link OrcKingdomFactory}).
 * <p>
 * The example uses both concrete implementations to create a king, a castle and
 * an army.
 * 
 */
public class App {

	/**
	 * Program entry point
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		createKingdom(new ElfKingdomFactory());
		createKingdom(new OrcKingdomFactory());
	}

	/**
	 * Creates kingdom
	 * @param factory
	 */
	public static void createKingdom(KingdomFactory factory) {
		King king = factory.createKing();
		Castle castle = factory.createCastle();
		Army army = factory.createArmy();
		System.out.println("The kingdom was created.");
		System.out.println(king);
		System.out.println(castle);
		System.out.println(army);
	}
}
