package com.iluwatar.facet.dragon;

import com.iluwatar.facet.Attack;

/**
 * Dragon object needs to be protected, since the other objects shouldn't be
 * allowed to edit its health.
 *
 *
 */
public class Dragon {
  private int health;

  public Dragon(int health) {
    this.health = health;
  }

  int f_getHealth() {
    return health;
  }

  void setHealth(int health) {
    this.health = health;
  }

  void f_receiveAttack(Attack attack) {
    switch(attack) {
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
