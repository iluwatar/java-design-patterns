package com.iluwatar.dependentmapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class DBAlbumTest {
  @Test
  void makeAlbum(){
    DBAlbum album= new DBAlbum("album");
    assertNotNull(album);

  }
  @Test
  void testGetName(){
    DBAlbum album= new DBAlbum("album");
    assertEquals("album",album.getName());
  }
  @Test
  void testGetAddTracks(){
    DBAlbum album= new DBAlbum("album");
    DBTrack track = new DBTrack("track2");
    DBTrack track1 = new DBTrack("track1");
    album.addTrack(track);
    album.addTrack(track1);
    assertEquals(track,album.getAllTrack().get(0));
    assertEquals(track1,album.getAllTrack().get(1));
  }
}
