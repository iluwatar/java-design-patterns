package com.iluwatar.dependentmapping;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Database.
 */
class Database {
  private final List<DataBaseTrack> allTracks = new ArrayList<>();
  private final List<DatabaseAlbum> allAlbum = new ArrayList<>();

  /**
   * Add tracks.
   *
   * @param newTrack the new track
   */
  void addTracks(DataBaseTrack newTrack) {
    allTracks.add(newTrack);
  }

  /**
   * Add album.
   *
   * @param newAlbum the new album
   */
  void addAlbum(DatabaseAlbum newAlbum) {
    allAlbum.add(newAlbum);
  }

  /**
   * Gets tracks.
   *
   * @param trackName the track name
   * @return the tracks
   */
  DataBaseTrack getTracks(String trackName) {
    for (DataBaseTrack t : allTracks) {
      if (t.getName().equals(trackName)) {
        return t;
      }
    }
    return null;
  }

  /**
   * Gets album.
   *
   * @param index the index
   * @return the album
   */
  DatabaseAlbum getAlbum(int index) {
    return allAlbum.get(index);
  }

  /**
   * Remove album.
   *
   * @param index the index
   */
  void removeAlbum(int index) {
    if (index == -1) {
      return;
    }
    allAlbum.remove(index);
  }

  /**
   * Gets album index.
   *
   * @param albumName the album name
   * @return the album index
   */
  int getAlbumIndex(String albumName) {
    for (int i = 0; i < allAlbum.size(); i++) {
      if (allAlbum.get(i).getName().equals(albumName)) {
        return i;
      }
    }
    return -1;
  }
}
