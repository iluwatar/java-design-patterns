package com.iluwatar.dependentmapping;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Db album.
 */
class DatabaseAlbum {
  private final List<DataBaseTrack> tracks = new ArrayList<>();
  private final String name;

  /**
   * Instantiates a new Db album.
   *
   * @param name the name
   */
  DatabaseAlbum(String name) {
    this.name = name;
  }

  /**
   * Gets all track.
   *
   * @return the all track
   */
  List<DataBaseTrack> getAllTrack() {
    return tracks;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  String getName() {
    return name;
  }

  /**
   * Add track.
   *
   * @param track the track
   */
  void addTrack(DataBaseTrack track) {
    tracks.add(track);
  }
}