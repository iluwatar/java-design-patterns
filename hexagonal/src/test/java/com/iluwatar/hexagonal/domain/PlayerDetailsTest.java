package com.iluwatar.hexagonal.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

/**
 * 
 * Unit tests for {@link PlayerDetails}
 *
 */
public class PlayerDetailsTest {

  @Test
  public void testEquals() {
    PlayerDetails details1 = PlayerDetails.create("tom@foo.bar", "11212-123434", "+12323425");
    PlayerDetails details2 = PlayerDetails.create("tom@foo.bar", "11212-123434", "+12323425");
    assertEquals(details1, details2);
    PlayerDetails details3 = PlayerDetails.create("john@foo.bar", "16412-123439", "+34323432");
    assertFalse(details1.equals(details3));
  }  
}
