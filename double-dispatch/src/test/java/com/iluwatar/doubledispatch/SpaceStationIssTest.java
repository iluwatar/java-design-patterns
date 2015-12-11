package com.iluwatar.doubledispatch;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Date: 12/10/15 - 11:31 PM
 *
 * @author Jeroen Meulemeester
 */
public class SpaceStationIssTest extends CollisionTest<SpaceStationIss> {

  @Override
  final SpaceStationIss getTestedObject() {
    return new SpaceStationIss(1, 2, 3, 4);
  }

  /**
   * Test the constructor parameters
   */
  @Test
  public void testConstructor() {
    final SpaceStationIss iss = new SpaceStationIss(1, 2, 3, 4);
    assertEquals(1, iss.getLeft());
    assertEquals(2, iss.getTop());
    assertEquals(3, iss.getRight());
    assertEquals(4, iss.getBottom());
    assertFalse(iss.isOnFire());
    assertFalse(iss.isDamaged());
    assertEquals("SpaceStationIss at [1,2,3,4] damaged=false onFire=false", iss.toString());
  }

  /**
   * Test what happens we collide with an asteroid
   */
  @Test
  public void testCollideFlamingAsteroid() {
    testCollision(
        new FlamingAsteroid(1, 1, 3, 4),
        false, true,
        false, false,
        "SpaceStationIss hits FlamingAsteroid."
    );
  }

  /**
   * Test what happens we collide with an meteoroid
   */
  @Test
  public void testCollideMeteoroid() {
    testCollision(
        new Meteoroid(1, 1, 3, 4),
        false, false,
        false, false,
        "SpaceStationIss hits Meteoroid."
    );
  }

  /**
   * Test what happens we collide with ISS
   */
  @Test
  public void testCollideSpaceStationIss() {
    testCollision(
        new SpaceStationIss(1, 1, 3, 4),
        true, false,
        false, false,
        "SpaceStationIss hits SpaceStationIss. SpaceStationIss is damaged!"
    );
  }

  /**
   * Test what happens we collide with MIR
   */
  @Test
  public void testCollideSpaceStationMir() {
    testCollision(
        new SpaceStationMir(1, 1, 3, 4),
        true, false,
        false, false,
        "SpaceStationIss hits SpaceStationMir. SpaceStationMir is damaged!"
    );
  }

}