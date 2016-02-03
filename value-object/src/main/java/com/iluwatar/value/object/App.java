package com.iluwatar.value.object;

/**
 * App Class.
 */
public class App {
  /**
   * main method.
   * A Value Object must check equality with equals() not == <br>
   * This practice creates three HeroStats(Value object) and checks equality between those.
   */
  public static void main(String[] args) {
    HeroStat statA = HeroStat.valueOf(10, 5, 0);
    HeroStat statB = HeroStat.valueOf(10, 5, 0);
    HeroStat statC = HeroStat.valueOf(5, 1, 8);

    System.out.println(statA.toString());
    
    System.out.println("Is statA and statB equal : " + statA.equals(statB));
    System.out.println("Is statA and statC equal : " + statA.equals(statC));
  }
}
