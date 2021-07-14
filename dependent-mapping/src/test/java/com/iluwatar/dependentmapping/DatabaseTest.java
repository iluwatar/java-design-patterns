package com.iluwatar.dependentmapping;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * The type Database test.
 */
class DatabaseTest {
  /**
   * Add get tracks test.
   */
  @Test
  void addGetTracksTest() {
    Database database = new Database();
    DataBaseTrack dataBaseTrack = new DataBaseTrack("track");
    database.addTracks(dataBaseTrack);
    assertEquals("track", database.getTracks("track").getName());
  }

  /**
   * Add get album test.
   */
  @Test
  void addGetAlbumTest() {
    Database database = new Database();
    DatabaseAlbum databaseAlbum = new DatabaseAlbum("album");
    database.addAlbum(databaseAlbum);
    assertEquals("album", database.getAlbum(0).getName());
  }

  /**
   * Remove album test.
   */
  @Test
  void removeAlbumTest() {
    Database database = new Database();
    DatabaseAlbum databaseAlbum = new DatabaseAlbum("album");
    DatabaseAlbum databaseAlbum1 = new DatabaseAlbum("album1");
    database.addAlbum(databaseAlbum);
    database.addAlbum(databaseAlbum1);
    assertEquals("album", database.getAlbum(0).getName());
    database.removeAlbum(0);
    assertEquals("album1", database.getAlbum(0).getName());
  }

  /**
   * Get album index test.
   */
  @Test
  void getAlbumIndexTest() {
    Database database = new Database();
    DatabaseAlbum databaseAlbum = new DatabaseAlbum("album");
    DatabaseAlbum databaseAlbum1 = new DatabaseAlbum("album1");
    database.addAlbum(databaseAlbum);
    database.addAlbum(databaseAlbum1);
    assertEquals(0, database.getAlbumIndex("album"));
    assertEquals(1, database.getAlbumIndex("album1"));
    assertEquals(-1, database.getAlbumIndex("not find"));
  }
}
