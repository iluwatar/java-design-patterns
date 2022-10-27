package com.iluwatar.facet.dragon;

import com.iluwatar.facet.Attack;
import com.iluwatar.facet.dragon.Dragon;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DragonFacet {
  private Dragon dragon;

  public DragonFacet(Dragon dragon) {
    this.dragon = dragon;
  }

  public int getHealth() {
    return dragon.f_getHealth();
  }

  public void receiveAttack(Attack attack) {
    if(attack == Attack.WATER_PISTOL || attack == Attack.ARROW) {
      dragon.f_receiveAttack(attack);
    }
  }
}
