package com.iluwatar.presentation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DsAlbumTest {
  @Test
  void testAdd_true(){
    DsAlbum dsAlbum = new DsAlbum();
    dsAlbum.addAlbums("title", "artist", true, "composer");
    assertEquals("composer", dsAlbum.getAlbums().get(0).getComposer());

  }

  @Test
  void testAdd_false(){
    DsAlbum dsAlbum = new DsAlbum();
    dsAlbum.addAlbums("title", "artist", false, "composer");
    assertEquals("", dsAlbum.getAlbums().get(0).getComposer());
  }
}
