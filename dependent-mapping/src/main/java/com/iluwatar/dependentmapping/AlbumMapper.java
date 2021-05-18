package com.iluwatar.dependentmapping;

class AlbumMapper {
  private final Database database;
  AlbumMapper(Database database){
    this.database=database;
  }
  Album loadAlbum(int index){
    DBAlbum album = database.getAlbum(index);
    Album result = new Album(album.getName());
    for(DBTrack track:album.getAllTrack()){
      Track newTrack = new Track(track.getName());
      result.addTrack(newTrack);
    }
    return result;
  }
  void updateAlbum(Album album){
    int index = database.getAlbumIndex(album.getName());
    database.removeAlbum(index);
    DBAlbum dbAlbum=new DBAlbum(album.getName());
    for(Track track: album.getAllTrack()){
      String trackName = track.getName();
      DBTrack dbTrack = database.getTracks(trackName);
      if (dbTrack == null) {
        dbTrack = new DBTrack(trackName);
        database.addTracks(dbTrack);
      }
      dbAlbum.addTrack(dbTrack);
      database.addAlbum(dbAlbum);
    }
  }
}
