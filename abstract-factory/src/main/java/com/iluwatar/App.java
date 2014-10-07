package com.iluwatar;

/**
 *
 * The essence of the Abstract Factory pattern is a factory interface
 * (KingdomFactory) and its implementations (ElfKingdomFactory,
 * OrcKingdomFactory).
 *
 * The example uses both concrete implementations to create a king, a castle and
 * an army.
 *
 */
public class App {

    public static void main(String[] args) {
        createKingdom(new ElfKingdomFactory());
        createKingdom(new OrcKingdomFactory());
    }

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
