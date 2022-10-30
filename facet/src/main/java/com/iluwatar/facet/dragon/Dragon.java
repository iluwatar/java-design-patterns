package com.iluwatar.facet.dragon;

import com.iluwatar.facet.Attack;

/**
 * Dragon object needs to be protected, since the other objects shouldn't be
 * allowed to edit its health. That is why it is in its own package with
 * {@link DragonFacet} and all the functions have no access level modifiers,
 * meaning only classes in the same package have access.
 */
public class Dragon {
  private int health;

  public Dragon(int health) {
    this.health = health;
  }

  int facetedGetHealth() {
    return health;
  }

  /**
   * This has no access level modifier, so only other classes within
   * the same package can access this function. This protects the knight
   * from being able to arbitrarily change the dragon's health.
   *
   * @param health The new health of the dragon
   */
  void setHealth(int health) {
    this.health = health;
  }

  void facetedReceiveAttack(Attack attack) {
    switch (attack) {
      case ARROW:
        health -= 10;
        break;
      case WATER_PISTOL:
        health -= 15;
        break;
      default:
        health -= 5;
    }
  }
}
