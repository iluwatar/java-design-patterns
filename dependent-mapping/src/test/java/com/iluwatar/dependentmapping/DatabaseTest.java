package com.iluwatar.dependentmapping;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DatabaseTest {
  @Test
  void addGetTracksTest(){
    Database database = new Database();
    DBTrack dbTrack = new DBTrack("track");
    database.addTracks(dbTrack);
    assertEquals("track",database.getTracks("track").getName());
  }
  @Test
  void addGetAlbumTest(){
    Database database = new Database();
    DBAlbum dbAlbum = new DBAlbum("album");
    database.addAlbum(dbAlbum);
    assertEquals("album",database.getAlbum(0).getName());
  }
  @Test
  void removeAlbumTest(){
    Database database = new Database();
    DBAlbum dbAlbum = new DBAlbum("album");
    DBAlbum dbAlbum1 = new DBAlbum("album1");
    database.addAlbum(dbAlbum);
    database.addAlbum(dbAlbum1);
    assertEquals("album",database.getAlbum(0).getName());
    database.removeAlbum(0);
    assertEquals("album1",database.getAlbum(0).getName());
  }
  @Test
  void getAlbumIndexTest(){
    Database database = new Database();
    DBAlbum dbAlbum = new DBAlbum("album");
    DBAlbum dbAlbum1 = new DBAlbum("album1");
    database.addAlbum(dbAlbum);
    database.addAlbum(dbAlbum1);
    assertEquals(0,database.getAlbumIndex("album"));
    assertEquals(1,database.getAlbumIndex("album1"));
    assertEquals(-1,database.getAlbumIndex("not find"));
  }
}
