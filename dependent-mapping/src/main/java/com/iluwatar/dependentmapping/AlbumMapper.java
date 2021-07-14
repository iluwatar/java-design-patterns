package com.iluwatar.dependentmapping;

/**
 * The type Album mapper.
 */
class AlbumMapper {
  private final Database database;

  /**
   * Instantiates a new Album mapper.
   *
   * @param database the database
   */
  AlbumMapper(Database database) {
    this.database = database;
  }

  /**
   * Load album.
   *
   * @param index the index
   * @return the album
   */
  Album loadAlbum(int index) {
    DatabaseAlbum album = database.getAlbum(index);
    var result = new Album(album.getName());
    for (DataBaseTrack track : album.getAllTrack()) {
      var newTrack = new Track(track.getName());
      result.addTrack(newTrack);
    }
    return result;
  }

  /**
   * Update album.
   *
   * @param album the album
   */
  void updateAlbum(Album album) {
    int index = database.getAlbumIndex(album.getName());
    database.removeAlbum(index);
    var databaseAlbum = new DatabaseAlbum(album.getName());
    for (Track track : album.getAllTrack()) {
      String trackName = track.getName();
      var dataBaseTrack = database.getTracks(trackName);
      if (dataBaseTrack == null) {
        dataBaseTrack = new DataBaseTrack(trackName);
        database.addTracks(dataBaseTrack);
      }
      databaseAlbum.addTrack(dataBaseTrack);
      database.addAlbum(databaseAlbum);
    }
  }
}
