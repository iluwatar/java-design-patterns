package com.iluwatar.tolerantreader;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Date: 12/30/15 - 18:34 PM
 *
 * @author Jeroen Meulemeester
 */
public class RainbowFishTest {

  /**
   * Verify if the getters of a {@link RainbowFish} return the expected values
   */
  @Test
  public void testValues() {
    final RainbowFish fish = new RainbowFish("name", 1, 2, 3);
    assertEquals("name", fish.getName());
    assertEquals(1, fish.getAge());
    assertEquals(2, fish.getLengthMeters());
    assertEquals(3, fish.getWeightTons());
  }

}