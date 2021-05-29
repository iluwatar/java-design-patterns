package com.iluwatar.dependentmapping;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

/**
 * The type Album.
 */
@Data
@Getter(AccessLevel.PACKAGE)
class Album {

  private final List<Track> tracks = new ArrayList<>();
  private final String name;

  Album(String name) {
    this.name = name;
  }

  List<Track> getAllTrack() {
    return tracks;
  }

  void addTrack(Track track) {
    tracks.add(track);
  }
}
