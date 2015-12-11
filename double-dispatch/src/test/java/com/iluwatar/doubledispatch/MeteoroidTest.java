package com.iluwatar.doubledispatch;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Date: 12/10/15 - 11:31 PM
 *
 * @author Jeroen Meulemeester
 */
public class MeteoroidTest extends CollisionTest<Meteoroid> {

  @Override
  final Meteoroid getTestedObject() {
    return new Meteoroid(1, 2, 3, 4);
  }

  /**
   * Test the constructor parameters
   */
  @Test
  public void testConstructor() {
    final Meteoroid meteoroid = new Meteoroid(1, 2, 3, 4);
    assertEquals(1, meteoroid.getLeft());
    assertEquals(2, meteoroid.getTop());
    assertEquals(3, meteoroid.getRight());
    assertEquals(4, meteoroid.getBottom());
    assertFalse(meteoroid.isOnFire());
    assertFalse(meteoroid.isDamaged());
    assertEquals("Meteoroid at [1,2,3,4] damaged=false onFire=false", meteoroid.toString());
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
        "Meteoroid hits FlamingAsteroid."
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
        "Meteoroid hits Meteoroid."
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
        "Meteoroid hits SpaceStationIss. SpaceStationIss is damaged!"
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
        "Meteoroid hits SpaceStationMir. SpaceStationMir is damaged!"
    );
  }

}