package com.iluwatar.dependentmapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class DBTrackTest {
  @Test
  void makeTrack(){
    DBTrack track= new DBTrack("track");
    assertNotNull(track);

  }
  @Test
  void testGetName(){
    DBTrack track= new DBTrack("track");
    assertEquals("track",track.getName());
  }
}