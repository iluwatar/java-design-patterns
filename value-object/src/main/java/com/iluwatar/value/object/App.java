package com.iluwatar.value.object;

/**
 * App Class.
 *
 */
public class App {
  /**
   * main method.
   */
  public static void main(String[] args) {
    HeroStat statA = HeroStat.valueOf(10, 5, 0);
    HeroStat statB = HeroStat.valueOf(5, 1, 8);

    System.out.println(statA.toString());
    // When using Value Objects do not use ==, only compare using equals().
    System.out.println("is statA and statB equal : " + statA.equals(statB));
  }
}
