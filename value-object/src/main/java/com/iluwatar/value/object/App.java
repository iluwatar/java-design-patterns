package com.iluwatar.value.object;

/**
 * A Value Object are objects which follow value semantics rather than reference semantics. This
 * means value objects' equality are not based on identity. Two value objects are equal when they
 * have the same value, not necessarily being the same object..
 * 
 * Value Objects must override equals(), hashCode() to check the equality with values. 
 * Value Objects should be immutable so declare members final.
 * Obtain instances by static factory methods.
 * The elements of the state must be other values, including primitive types. 
 * Provide methods, typically simple getters, to get the elements of the state.
 * A Value Object must check equality with equals() not == 
 * 
 * For more specific and strict rules to implement value objects check the rules from Stephen
 * Colebourne's term VALJO : http://blog.joda.org/2014/03/valjos-value-java-objects.html 
 *  
 */
public class App {
  /**
   * 
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
