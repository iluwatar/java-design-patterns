package com.iluwatar.value.object;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import static org.junit.Assert.assertThat;

import com.google.common.testing.EqualsTester;

import org.junit.Test;

/**
 * Unit test for HeroStat.
 */
public class HeroStatTest {

  /**
   * Tester for equals() and hashCode() methods of a class. Using guava's EqualsTester
   * 
   * @see http://static.javadoc.io/com.google.guava/guava-testlib/19.0/com/google/common/testing/
   *      EqualsTester.html
   */
  @Test
  public void testEquals() {
    HeroStat heroStatA = HeroStat.valueOf(3, 9, 2);
    HeroStat heroStatB = HeroStat.valueOf(3, 9, 2);
    new EqualsTester().addEqualityGroup(heroStatA, heroStatB).testEquals();
  }

  /**
   * The toString() for two equal values must be the same. For two non-equal values it must be
   * different.
   */
  @Test
  public void testToString() {

    HeroStat heroStatA = HeroStat.valueOf(3, 9, 2);
    HeroStat heroStatB = HeroStat.valueOf(3, 9, 2);
    HeroStat heroStatC = HeroStat.valueOf(3, 9, 8);

    assertThat(heroStatA.toString(), is(heroStatB.toString()));
    assertThat(heroStatA.toString(), is(not(heroStatC.toString())));


  }

}
