package com.iluwatar.dependentmapping;

import lombok.extern.slf4j.Slf4j;

/**
 * Some objects naturally appear in the context of other objects. Tracks on an album may be loaded
 * or saved whenever the underlying album is loaded or saved. If they aren't referenced to by any
 * other table in the database, you can simplify the mapping procedure by having the album mapper
 * perform the map-ping for the tracks as well - treating this mapping as a dependent mapping.
 *
 * <p>In this example {@link Database} contains methods that can be changed.
 * First the {@link Database} loads some albums and tracks and output the track name. Then the
 * {@link Database} updates the person and delete them and output the track name.
 */
@Slf4j
public class App {

  /**
   * Init database database.
   *
   * @return the database
   */
  public static Database initDatabase() {
    var databaseAlbum1 = new DatabaseAlbum("album1");
    var databaseAlbum2 = new DatabaseAlbum("album2");

    var dataBaseTrack1 = new DataBaseTrack("track1");
    var dataBaseTrack2 = new DataBaseTrack("track2");
    var dataBaseTrack3 = new DataBaseTrack("track3");

    databaseAlbum1.addTrack(dataBaseTrack1);
    databaseAlbum1.addTrack(dataBaseTrack2);
    databaseAlbum2.addTrack(dataBaseTrack3);

    var database = new Database();
    database.addAlbum(databaseAlbum1);
    database.addAlbum(databaseAlbum2);
    database.addTracks(dataBaseTrack1);
    database.addTracks(dataBaseTrack2);
    database.addTracks(dataBaseTrack3);
    return database;
  }

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    LOGGER.info("Making database.");
    var database = initDatabase();

    var albumMapper = new AlbumMapper(database);
    LOGGER.info("Test loading album");
    var album = albumMapper.loadAlbum(0);
    for (Track track : album.getAllTrack()) {
      LOGGER.info(track.getName());
    }

    LOGGER.info("Test update album");
    var album1 = albumMapper.loadAlbum(1);
    var track4 = new Track("track4");
    album1.addTrack(track4);
    albumMapper.updateAlbum(album1);
    var changed = database.getAlbum(1);
    for (DataBaseTrack dataBaseTrack : changed.getAllTrack()) {
      LOGGER.info(dataBaseTrack.getName());
    }
  }
}
