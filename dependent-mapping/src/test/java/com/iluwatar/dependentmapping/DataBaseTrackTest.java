package com.iluwatar.dependentmapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * The type DatabaseTrack test.
 */
public class DataBaseTrackTest {
  /**
   * Make track.
   */
  @Test
  void makeTrack() {
    DataBaseTrack track = new DataBaseTrack("track");
    assertNotNull(track);

  }

  /**
   * Test get name.
   */
  @Test
  void testGetName() {
    DataBaseTrack track = new DataBaseTrack("track");
    assertEquals("track", track.getName());
  }
}