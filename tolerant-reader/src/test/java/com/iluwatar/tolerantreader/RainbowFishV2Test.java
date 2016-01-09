package com.iluwatar.tolerantreader;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Date: 12/30/15 - 18:35 PM
 *
 * @author Jeroen Meulemeester
 */
public class RainbowFishV2Test {

  /**
   * Verify if the getters of a {@link RainbowFish} return the expected values
   */
  @Test
  public void testValues() {
    final RainbowFishV2 fish = new RainbowFishV2("name", 1, 2, 3, false, true, false);
    assertEquals("name", fish.getName());
    assertEquals(1, fish.getAge());
    assertEquals(2, fish.getLengthMeters());
    assertEquals(3, fish.getWeightTons());
    assertEquals(false, fish.getSleeping());
    assertEquals(true, fish.getHungry());
    assertEquals(false, fish.getAngry());
  }

}