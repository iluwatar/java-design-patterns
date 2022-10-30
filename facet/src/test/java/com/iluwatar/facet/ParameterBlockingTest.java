package com.iluwatar.facet;

import com.iluwatar.facet.dragon.Dragon;
import com.iluwatar.facet.dragon.DragonFacet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * One of the possible ways of using a facet is to restrict parameters that can
 * be used by the protected class. This class tests that some parameters do
 * something while others don't.
 */
public class ParameterBlockingTest {

  /**
   * Make sure that illegal parameters applied to the receiveAttack function in
   * the dragonFacet indeed do not do any damage (i.e. are ignored.)
   */
  @Test
  public void blockIllegalParameters () {
    Dragon dragon = new Dragon(100);
    DragonFacet dragonFacet = new DragonFacet(dragon);
    Knight sirJim = new Knight("Sir Jim", Attack.SWORD, dragonFacet);
    int oldDragonHealth = dragonFacet.getHealth();
    sirJim.attackDragon();
    assertEquals(oldDragonHealth, dragonFacet.getHealth());

    Knight sirWill = new Knight("Sir Will", Attack.FLAME_THROWER, dragonFacet);
    oldDragonHealth = dragonFacet.getHealth();
    sirWill.attackDragon();
    assertEquals(oldDragonHealth, dragonFacet.getHealth());
  }

  /**
   * These tests make sure that the dragon is indeed attacked when the knights
   * use the correct attacks
   */
  @Test
  public void allowLegalParameters () {
    Dragon dragon = new Dragon(100);
    DragonFacet dragonFacet = new DragonFacet(dragon);
    Knight sirJim = new Knight("Sir Jim", Attack.ARROW, dragonFacet);
    int oldDragonHealth = dragonFacet.getHealth();
    sirJim.attackDragon();
    assertNotEquals(oldDragonHealth, dragonFacet.getHealth());

    Knight sirWill = new Knight("Sir Will", Attack.WATER_PISTOL, dragonFacet);
    oldDragonHealth = dragonFacet.getHealth();
    sirWill.attackDragon();
    assertNotEquals(oldDragonHealth, dragonFacet.getHealth());
  }
}
