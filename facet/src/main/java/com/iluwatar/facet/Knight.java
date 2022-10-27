package com.iluwatar.facet;

import com.iluwatar.facet.dragon.DragonFacet;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Knight {
  private final String name;
  private Attack attack;
  private DragonFacet dragonFacet;

  public Knight (String name, Attack attack, DragonFacet dragonFacet) {
    this.name = name;
    this.attack = attack;
    this.dragonFacet = dragonFacet;
  }

  public void attackDragon() {
    int oldHealth = dragonFacet.getHealth();
    dragonFacet.receiveAttack(attack);
    if(oldHealth == dragonFacet.getHealth()){
      LOGGER.info("{}: Darn it! {} did nothing.", name, attack);
    } else {
      LOGGER.info("{}: Huzzah! {} hurt that dastardly dragon.", name, attack);
    }
  }
}
