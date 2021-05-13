package com.iluwatar.presentation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DisplayedAlbumsTest {
  @Test
  void testAdd_true(){
    DisplayedAlbums displayedAlbums = new DisplayedAlbums();
    displayedAlbums.addAlbums("title", "artist", true, "composer");
    assertEquals("composer", displayedAlbums.getAlbums().get(0).getComposer());

  }

  @Test
  void testAdd_false(){
    DisplayedAlbums displayedAlbums = new DisplayedAlbums();
    displayedAlbums.addAlbums("title", "artist", false, "composer");
    assertEquals("", displayedAlbums.getAlbums().get(0).getComposer());
  }
}
