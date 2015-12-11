package com.iluwatar.doubledispatch;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Date: 12/10/15 - 11:31 PM
 *
 * @author Jeroen Meulemeester
 */
public class SpaceStationMirTest extends CollisionTest<SpaceStationMir> {

  @Override
  final SpaceStationMir getTestedObject() {
    return new SpaceStationMir(1, 2, 3, 4);
  }

  /**
   * Test the constructor parameters
   */
  @Test
  public void testConstructor() {
    final SpaceStationMir mir = new SpaceStationMir(1, 2, 3, 4);
    assertEquals(1, mir.getLeft());
    assertEquals(2, mir.getTop());
    assertEquals(3, mir.getRight());
    assertEquals(4, mir.getBottom());
    assertFalse(mir.isOnFire());
    assertFalse(mir.isDamaged());
    assertEquals("SpaceStationMir at [1,2,3,4] damaged=false onFire=false", mir.toString());
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
        "SpaceStationMir hits FlamingAsteroid."
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
        "SpaceStationMir hits Meteoroid."
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
        "SpaceStationMir hits SpaceStationIss. SpaceStationIss is damaged!"
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
        "SpaceStationMir hits SpaceStationMir. SpaceStationMir is damaged!"
    );
  }

}