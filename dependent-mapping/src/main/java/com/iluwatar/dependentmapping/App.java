package com.iluwatar.dependentmapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {
  public static void main(String[] args) {
    LOGGER.info("Making database.");
    DBAlbum dbAlbum1 = new DBAlbum("album1");
    DBAlbum dbAlbum2 = new DBAlbum("album2");

    DBTrack dbTrack1 = new DBTrack("track1");
    DBTrack dbTrack2 = new DBTrack("track2");
    DBTrack dbTrack3 = new DBTrack("track3");

    dbAlbum1.addTrack(dbTrack1);
    dbAlbum1.addTrack(dbTrack2);
    dbAlbum2.addTrack(dbTrack3);

    Database database = new Database();
    database.addAlbum(dbAlbum1);
    database.addAlbum(dbAlbum2);
    database.addTracks(dbTrack1);
    database.addTracks(dbTrack2);
    database.addTracks(dbTrack3);

    AlbumMapper albumMapper = new AlbumMapper(database);
    LOGGER.info("Test loading album");
    Album album = albumMapper.loadAlbum(0);
    for(Track track:album.getAllTrack()){
      LOGGER.info(track.getName());
    }

    LOGGER.info("Test update album");
    Album album1 = albumMapper.loadAlbum(1);
    Track track4 = new Track("track4");
    album1.addTrack(track4);
    albumMapper.updateAlbum(album1);
    DBAlbum changed = database.getAlbum(1);
    for(DBTrack dbTrack:changed.getAllTrack()){
      LOGGER.info(dbTrack.getName());
    }
  }
}
