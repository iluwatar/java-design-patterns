package com.iluwatar.presentation;

/**
 *A class used to store the information of album.
 */
public class Album {
  int rowId;
  String title;
  String artist;
  boolean isClassical;
  String composer;

  /**
   * The constructor method of Album.
   *
   * @param rowId the id of the album
   * @param title the title of the album
   * @param artist the name of artist
   * @param isClassical whether the album is classical
   * @param composer the name of composer
   */
  public Album(int rowId, String title, String artist, boolean isClassical, String composer) {
    this.rowId = rowId;
    this.title = title;
    this.artist = artist;
    this.isClassical = isClassical;
    this.composer = composer;
  }


}
