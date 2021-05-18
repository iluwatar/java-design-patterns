package com.iluwatar.dependentmapping;

import java.util.ArrayList;
import java.util.List;

class Database {
  private final List<DBTrack> allTracks = new ArrayList<>();
  private final List<DBAlbum> allAlbum = new ArrayList<>();
  void addTracks(DBTrack newTrack){
    allTracks.add(newTrack);
  }
  void addAlbum(DBAlbum newAlbum){
    allAlbum.add(newAlbum);
  }
  DBTrack getTracks(String trackName){
    for (DBTrack t: allTracks){
      if (t.getName().equals(trackName)){
        return t;
      }
    }
    return null;
  }
  DBAlbum getAlbum(int index){
    return allAlbum.get(index);
  }
  void removeAlbum(int index){
    if(index == -1){
      return;
    }
    allAlbum.remove(index);
  }
  int getAlbumIndex(String albumName){
    for(int i=0; i< allAlbum.size(); i++){
      if(allAlbum.get(i).getName().equals(albumName)){
        return i;
      }
    }
    return -1;
  }
}
