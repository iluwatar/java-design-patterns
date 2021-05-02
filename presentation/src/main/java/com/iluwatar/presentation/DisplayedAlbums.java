package com.iluwatar.presentation;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * a class used to deal with albums.
 *
 */
@Slf4j
@Getter
public class DisplayedAlbums {
  /**
   * albums a list of albums.
   */
  private final List<Album> albums;

  /**
   * a constructor method.
   */
  public DisplayedAlbums() {
    this.albums = new ArrayList<>();
  }

  /**
   * a method used to add a new album to album list.
   *
   * @param title       the title of the album.
   * @param artist      the artist name of the album.
   * @param isClassical is the album classical, true or false.
   * @param composer    only when the album is classical,
   *                    composer can have content.
   */
  public void addAlbums(final String title,
                        final String artist, final boolean isClassical,
                        final String composer) {
    if (isClassical) {
      this.albums.add(new Album(title, artist, true, composer));
    } else {
      this.albums.add(new Album(title, artist, false, ""));
    }
  }
}
