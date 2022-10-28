package com.iluwatar.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *A class used to store the information of album.
 */
@Setter
@Getter
@AllArgsConstructor
public class Album {
  /**
   * the title of the album.
   */
  private String title;
  /**
   * the artist name of the album.
   */
  private String artist;
  /**
   * is the album classical, true or false.
   */
  private boolean isClassical;
  /**
   * only when the album is classical,
   * composer can have content.
   */
  private String composer;
}
