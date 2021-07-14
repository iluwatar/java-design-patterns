---  
layout: pattern  
title: Dependent Mapping
folder: dependent-mapping
permalink: /patterns/dependent-mapping/  
categories: Behavioral
tags:
- Data access
---  

## Intent
The basic idea behind Dependent Mapping is that one class relies upon some other class for its database persistence. Each dependent can have only one owner and must have one owner.

A dependent may itself be the owner of another dependent. In this case the owner of the first dependent is also responsible for the persistence of the second dependent. You can have all the attributes that the owner has.

if you want to update the collection of dependents you can safely delete all rows that link to the owner and then reinsert all the dependents.

## Explanation

There are many tracks. Different albums have corresponding tracks. When you want to change the tracks of the albums, the dependent-mapping-pattern will delete all the tracks in the albums and insert them again to update the albums.

**Programmatic Example**

First of all, we have track class and album class.
```java
class Track {

  private final String name;

  Track(String name) {
    this.name = name;
  }

}
```
```java
class Album {

  private final List<Track> tracks = new ArrayList<>();
  private final String name;

  Album(String name) {
    this.name = name;
  }

  List<Track> getAllTrack() {
    return tracks;
  }

  void addTrack(Track track) {
    tracks.add(track);
  }
}
```
Tracks and albums can be stored in the database, and some corresponding interfaces are provided.
```java
class Database {

  private final List<DataBaseTrack> allTracks = new ArrayList<>();
  private final List<DatabaseAlbum> allAlbum = new ArrayList<>();

  public Database() {
  }

  void addTracks(DataBaseTrack newTrack) {
    allTracks.add(newTrack);
  }


  void addAlbum(DatabaseAlbum newAlbum) {
    allAlbum.add(newAlbum);
  }

  DataBaseTrack getTracks(String trackName) {
    return allTracks.stream()
      .filter(t -> t.getName().equals(trackName))
      .findFirst()
      .orElse(null);
  }

  DatabaseAlbum getAlbum(int index) {
    return allAlbum.get(index);
  }

  void removeAlbum(int index) {
    if (index == -1) {
      return;
    }
    allAlbum.remove(index);
  }

  /**
   * Gets album index.
   *
   * @param albumName the album name
   * @return the album index
   */
  int getAlbumIndex(String albumName) {
    return IntStream.range(0, allAlbum.size())
      .filter(i -> allAlbum.get(i).getName().equals(albumName)).findFirst().orElse(-1);
  }
}
```
When you want to update an album, the AlbumMapper class loads the album.

```java
  Album loadAlbum(int index) {
    DatabaseAlbum album = database.getAlbum(index);
    Album result = new Album(album.getName());
    for (DataBaseTrack track : album.getAllTrack()) {
      Track newTrack = new Track(track.getName());
      result.addTrack(newTrack);
    }
    return result;
  }
```

When the album is updated, the AlbumMapper class will delete and reinsert all the tracks in the album to update the album.

```java
  void updateAlbum(Album album) {
    int index = database.getAlbumIndex(album.getName());
    database.removeAlbum(index);
    DatabaseAlbum databaseAlbum = new DatabaseAlbum(album.getName());
    for (Track track : album.getAllTrack()) {
      String trackName = track.getName();
      DataBaseTrack dataBaseTrack = database.getTracks(trackName);
      if (dataBaseTrack == null) {
        dataBaseTrack = new DataBaseTrack(trackName);
        database.addTracks(dataBaseTrack);
      }
      databaseAlbum.addTrack(dataBaseTrack);
      database.addAlbum(databaseAlbum);
    }
  }
```

## Class diagram
![alt text](./etc/dependent-mapping.png "Dependent Mapping pattern class diagram")

## Applicability
You use Dependent Mapping when you have an object that’s only referred to by one other object, which usually occurs when one object has a collection of dependents. Dependent Mapping is a good way of dealing with the awkward situation where the owner has a collection of references to its dependents but there’s no back pointer. Providing that the many objects don’t need their own identity, using Dependent Mapping makes it easier to manage their persistence.

For Dependent Mapping to work there are a number of preconditions.
-   A dependent must have exactly one owner.
-   There must be no references from any object other than the owner to the dependent.

## Credits

* [Dependent Mapping Pattern-1]([https://martinfowler.com/eaaCatalog/dependentMapping.html](https://martinfowler.com/eaaCatalog/dependentMapping.html))
* [Dependent Mapping Pattern-2]([https://www.sourcecodeexamples.net/2018/05/dependent-mapping-pattern.html](https://www.sourcecodeexamples.net/2018/05/dependent-mapping-pattern.html))
