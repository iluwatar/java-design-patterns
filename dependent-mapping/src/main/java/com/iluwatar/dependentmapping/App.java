package com.iluwatar.dependentmapping;

import lombok.extern.slf4j.Slf4j;

/**
 * The type App.
 */
@Slf4j
public class App {
  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    LOGGER.info("Making database.");
    DatabaseAlbum databaseAlbum1 = new DatabaseAlbum("album1");
    DatabaseAlbum databaseAlbum2 = new DatabaseAlbum("album2");

    DataBaseTrack dataBaseTrack1 = new DataBaseTrack("track1");
    DataBaseTrack dataBaseTrack2 = new DataBaseTrack("track2");
    DataBaseTrack dataBaseTrack3 = new DataBaseTrack("track3");

    databaseAlbum1.addTrack(dataBaseTrack1);
    databaseAlbum1.addTrack(dataBaseTrack2);
    databaseAlbum2.addTrack(dataBaseTrack3);

    Database database = new Database();
    database.addAlbum(databaseAlbum1);
    database.addAlbum(databaseAlbum2);
    database.addTracks(dataBaseTrack1);
    database.addTracks(dataBaseTrack2);
    database.addTracks(dataBaseTrack3);

    AlbumMapper albumMapper = new AlbumMapper(database);
    LOGGER.info("Test loading album");
    Album album = albumMapper.loadAlbum(0);
    for (Track track : album.getAllTrack()) {
      LOGGER.info(track.getName());
    }

    LOGGER.info("Test update album");
    Album album1 = albumMapper.loadAlbum(1);
    Track track4 = new Track("track4");
    album1.addTrack(track4);
    albumMapper.updateAlbum(album1);
    DatabaseAlbum changed = database.getAlbum(1);
    for (DataBaseTrack dataBaseTrack : changed.getAllTrack()) {
      LOGGER.info(dataBaseTrack.getName());
    }
  }
}
