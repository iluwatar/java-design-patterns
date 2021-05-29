package com.iluwatar.dependentmapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import lombok.Data;


/**
 * The type Database.
 */
@Data
class Database {

  private final List<DataBaseTrack> allTracks = new ArrayList<>();
  private final List<DatabaseAlbum> allAlbum = new ArrayList<>();

  public Database() {
  }

  void addTracks(DataBaseTrack newTrack) {
    allTracks.add(newTrack);
  }


  void addAlbum(DatabaseAlbum newAlbum) {
    allAlbum.add(newAlbum);
  }

  DataBaseTrack getTracks(String trackName) {
    return allTracks.stream()
      .filter(t -> t.getName().equals(trackName))
      .findFirst()
      .orElse(null);
  }

  DatabaseAlbum getAlbum(int index) {
    return allAlbum.get(index);
  }

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
    return IntStream.range(0, allAlbum.size())
      .filter(i -> allAlbum.get(i).getName().equals(albumName)).findFirst().orElse(-1);
  }
}
