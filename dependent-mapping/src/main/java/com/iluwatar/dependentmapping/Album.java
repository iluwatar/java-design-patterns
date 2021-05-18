package com.iluwatar.dependentmapping;

import java.util.ArrayList;
import java.util.List;

class Album {
  private final List<Track> tracks = new ArrayList<>();
  private final String name;
  Album(String name){
    this.name = name;
  }
  List<Track> getAllTrack(){
    return tracks;
  }
  String getName(){
    return name;
  }
  void addTrack(Track track){
    tracks.add(track);
  }
}
