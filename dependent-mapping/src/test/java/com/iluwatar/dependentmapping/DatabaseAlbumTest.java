package com.iluwatar.dependentmapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * The type DatabaseAlbum test.
 */
public class DatabaseAlbumTest {
  /**
   * Make album.
   */
  @Test
  void makeAlbum() {
    DatabaseAlbum album = new DatabaseAlbum("album");
    assertNotNull(album);

  }

  /**
   * Test get name.
   */
  @Test
  void testGetName() {
    DatabaseAlbum album = new DatabaseAlbum("album");
    assertEquals("album", album.getName());
  }

  /**
   * Test get add tracks.
   */
  @Test
  void testGetAddTracks() {
    DatabaseAlbum album = new DatabaseAlbum("album");
    DataBaseTrack track = new DataBaseTrack("track2");
    DataBaseTrack track1 = new DataBaseTrack("track1");
    album.addTrack(track);
    album.addTrack(track1);
    assertEquals(track, album.getAllTrack().get(0));
    assertEquals(track1, album.getAllTrack().get(1));
  }
}
