package com.iluwatar.presentation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AlbumTest {
  @Test
  void testSetTitle(){
    Album album = new Album("a", "b", false, "");
    album.setTitle("b");
    assertEquals("b", album.getTitle());
  }

  @Test
  void testSetArtist(){
    Album album = new Album("a", "b", false, "");
    album.setArtist("c");
    assertEquals("c", album.getArtist());
  }

  @Test
  void testSetClassical(){
    Album album = new Album("a", "b", false, "");
    album.setClassical(true);
    assertTrue(album.isClassical());
  }

  @Test
  void testSetComposer(){
    Album album = new Album("a", "b", false, "");
    album.setClassical(true);
    album.setComposer("w");
    assertEquals("w", album.getComposer());
  }
}
