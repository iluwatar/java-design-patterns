package com.iluwatar.facet.dragon;

import com.iluwatar.facet.Attack;

/**
 * The facet class which acts as an interface/wrapper for {@link Dragon}.
 * It only allows access to the methods with names beginning
 * with 'faceted', and also checks in receiveAttack() what
 * the type of attack is, so that it can filter some illegal
 * values.
 */
public class DragonFacet {
  private final Dragon dragon;

  public DragonFacet(Dragon dragon) {
    this.dragon = dragon;
  }

  public int getHealth() {
    return dragon.facetedGetHealth();
  }

  /**
   * This performs a check of the attack, and for certain illegal values
   * (namely FLAME_THROWER and SWORD), the attack will not even alert
   * the dragon.
   *
   * @param attack The attack which is attempted against dragon
   */
  public void receiveAttack(Attack attack) {
    if (attack == Attack.WATER_PISTOL || attack == Attack.ARROW) {
      dragon.facetedReceiveAttack(attack);
    }
  }
}
