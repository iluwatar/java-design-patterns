package com.iluwatar.presentation;

import java.util.ArrayList;

/**
 * a class used to deal with albums.
 *
 * @author EdisonE3
 */
public class DsAlbum {
  public ArrayList<Album> albums;
  public ArrayList<Album> albumsCache;

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
    if (albumsCache == null) {
      return;
    }
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
