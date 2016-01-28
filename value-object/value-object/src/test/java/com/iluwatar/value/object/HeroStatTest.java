package com.iluwatar.value.object;

import com.google.common.testing.EqualsTester;

import org.junit.Test;

/**
 * Unit test for HeroStat.
 */
public class HeroStatTest {

  /**
   * Tester for equals() and hashCode() methods of a class.
   * @see http://www.javadoc.io/doc/com.google.guava/guava-testlib/19.0
   */
  @Test
  public void testEquals() {
    HeroStat heroStatA = HeroStat.valueOf(3, 9, 2);
    HeroStat heroStatB = HeroStat.valueOf(3, 9, 2);
    new EqualsTester().addEqualityGroup(heroStatA, heroStatB).testEquals();
  }

}
