package com.iluwatar.dependentmapping;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The type Album test.
 */
class AlbumTest {

  /**
   * Make album.
   */
  @Test
  void makeAlbum() {
    Album album = new Album("album");
    assertNotNull(album);

  }

  /**
   * Test get name.
   */
  @Test
  void testGetName() {
    Album album = new Album("album");
    assertEquals("album", album.getName());
  }

  /**
   * Test get add tracks.
   */
  @Test
  void testGetAddTracks() {
    Album album = new Album("album");
    Track track = new Track("track2");
    Track track1 = new Track("track1");
    album.addTrack(track);
    album.addTrack(track1);
    assertEquals(track, album.getAllTrack().get(0));
    assertEquals(track1, album.getAllTrack().get(1));
  }

}
