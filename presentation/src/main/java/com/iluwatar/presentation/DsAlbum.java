package com.iluwatar.presentation;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * a class used to deal with albums.
 */
@Data
public class DsAlbum {
  private List<Album> albums;
  private List<Album> albumsCache;

  /**
   * a constructor method.
   */
  public DsAlbum() {
    this.albums = new ArrayList<>();
    this.albumsCache = new ArrayList<>();
  }

  /**
   *a method used to add a new album to album list.
   */
  public void addAlbums(int rowId, String title, String artist,
                        boolean isClassical, String composer) {
    this.albumsCache.add(new Album(rowId, title, artist, isClassical, composer));
  }

  /**
   * a method to accept all changes.
   */
  public void acceptChanges() {
    albums.addAll(albumsCache);
    albumsCache.clear();
  }
}
