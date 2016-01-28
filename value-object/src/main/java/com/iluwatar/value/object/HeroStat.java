package com.iluwatar.value.object;

/**
 * The Discount Coupon only discounts by percentage.
 *
 */
public class HeroStat {


  // stats for a hero

  private final int strength;
  private final int intelligence;
  private final int luck;


  // All constructors must be private.
  private HeroStat(int strength, int intelligence, int luck) {
    super();
    this.strength = strength;
    this.intelligence = intelligence;
    this.luck = luck;
  }

  public static HeroStat valueOf(int strength, int intelligence, int luck) {
    return new HeroStat(strength, intelligence, luck);
  }

  public int getStrength() {
    return strength;
  }

  public int getIntelligence() {
    return intelligence;
  }

  public int getLuck() {
    return luck;
  }

  /*
   * recommended to provide a static factory method capable of creating an instance from the formal
   * string representation declared like this. public static Juice parse(String string) {}
   */

  // toString, hashCode, equals

  @Override
  public String toString() {
    return "HeroStat [strength=" + strength + ", intelligence=" + intelligence + ", luck=" + luck
        + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + intelligence;
    result = prime * result + luck;
    result = prime * result + strength;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    HeroStat other = (HeroStat) obj;
    if (intelligence != other.intelligence) {
      return false;
    }
    if (luck != other.luck) {
      return false;
    }
    if (strength != other.strength) {
      return false;
    }
    return true;
  }


  // the clone() method should not be public

}
