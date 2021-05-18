package com.iluwatar.dependentmapping;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class TrackTest {
  @Test
  void makeTrack(){
    Track track= new Track("track");
    assertNotNull(track);

  }
  @Test
  void testGetName(){
    Track track= new Track("track");
    assertEquals("track",track.getName());
  }
}
