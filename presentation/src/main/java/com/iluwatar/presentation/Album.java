package com.iluwatar.presentation;

import lombok.AllArgsConstructor;

/**
 *A class used to store the information of album.
 */
@AllArgsConstructor
public class Album {
  int rowId;
  String title;
  String artist;
  boolean isClassical;
  String composer;
}
