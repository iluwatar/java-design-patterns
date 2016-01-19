package com.iluwatar.decorator;

/**
 * 
 * The Decorator pattern is a more flexible alternative to subclassing. The Decorator class
 * implements the same interface as the target and uses composition to "decorate" calls to the
 * target. Using the Decorator pattern it is possible to change the behavior of the class during
 * runtime.
 * <p>
 * In this example we show how the simple {@link Troll} first attacks and then flees the battle.
 * Then we decorate the {@link Troll} with a {@link SmartHostile} and perform the attack again. You
 * can see how the behavior changes after the decoration.
 * 
 */
public class App {

  /**
   * Program entry point
   * 
   * @param args command line args
   */
  public static void main(String[] args) {

    // simple troll
    System.out.println("A simple looking troll approaches.");
    Hostile troll = new Troll();
    troll.attack();
    troll.fleeBattle();
    System.out.printf("Simple troll power %d.\n", troll.getAttackPower());

    // change the behavior of the simple troll by adding a decorator
    System.out.println("\nA smart looking troll surprises you.");
    Hostile smart = new SmartHostile(troll);
    smart.attack();
    smart.fleeBattle();
    System.out.printf("Smart troll power %d.\n", smart.getAttackPower());
  }
}
