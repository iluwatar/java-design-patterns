package com.iluwatar.facet;

import com.iluwatar.facet.dragon.DragonFacet;
import lombok.extern.slf4j.Slf4j;

/**
 * Simple knight class. It is given a reference only to a DragonFacet,
 * not a dragon itself. This restricts its access to its functions and
 * disallows some incorrect parameters in the receiveAttack() function.
 */
@Slf4j
public class Knight {
  private final String name;
  private Attack attack;
  private DragonFacet dragonFacet;

  /**
   * Simple constructor for a Knight.
   *
   * @param name The name of the knight
   * @param attack His type of attack
   * @param dragonFacet The reference to the dragon wrapped by a facet
   */
  public Knight(String name, Attack attack, DragonFacet dragonFacet) {
    this.name = name;
    this.attack = attack;
    this.dragonFacet = dragonFacet;
  }

  /**
   * Try to attack the dragon through the facet.
   */
  public void attackDragon() {
    int oldHealth = dragonFacet.getHealth();
    dragonFacet.receiveAttack(attack);
    if (oldHealth == dragonFacet.getHealth()) {
      LOGGER.info("{}: Darn it! {} did nothing.", name, attack);
    } else {
      LOGGER.info("{}: Huzzah! {} hurt that dastardly dragon.", name, attack);
    }
  }
}
