package com.iluwatar.presentation;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * a class used to deal with albums.
 */
@Slf4j
@Getter
public class DsAlbum {
  private List<Album> albums;

  /**
   * a constructor method.
   */
  public DsAlbum() {
    this.albums = new ArrayList<>();
  }

  /**
   * a method used to add a new album to album list.
   */
  public void addAlbums(int rowId, String title, String artist,
                        boolean isClassical, String composer) {
    this.albums.add(new Album(rowId, title, artist, isClassical, composer));
  }

}
