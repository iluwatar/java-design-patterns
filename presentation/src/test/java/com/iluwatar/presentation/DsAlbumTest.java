package com.iluwatar.presentation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DsAlbumTest {
  @Test
  void testAdd_true(){
    DsAlbum dsAlbum = new DsAlbum();
    dsAlbum.addAlbums(1, "title", "artist", true, "composer");
    assertEquals("composer", dsAlbum.getAlbums().get(0).composer);
  }

  @Test
  void testAdd_false(){
    DsAlbum dsAlbum = new DsAlbum();
    dsAlbum.addAlbums(1, "title", "artist", false, "composer");
    assertEquals("", dsAlbum.getAlbums().get(0).composer);
  }
}
