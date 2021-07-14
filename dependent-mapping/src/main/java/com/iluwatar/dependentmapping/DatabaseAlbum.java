package com.iluwatar.dependentmapping;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

/**
 * The type Db album.
 */
@Data
@Getter(AccessLevel.PACKAGE)
class DatabaseAlbum {

  private final List<DataBaseTrack> tracks = new ArrayList<>();
  private final String name;

  DatabaseAlbum(String name) {
    this.name = name;
  }

  List<DataBaseTrack> getAllTrack() {
    return tracks;
  }

  void addTrack(DataBaseTrack track) {
    tracks.add(track);
  }
}