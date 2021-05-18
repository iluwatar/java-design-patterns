package com.iluwatar.dependentmapping;

import java.util.ArrayList;
import java.util.List;

class DBAlbum {
  private final List<DBTrack> tracks = new ArrayList<>();
  private final String name;
  DBAlbum(String name){
    this.name = name;
  }
  List<DBTrack> getAllTrack(){
    return tracks;
  }
  String getName(){
    return name;
  }
  void addTrack(DBTrack track){
    tracks.add(track);
  }
}