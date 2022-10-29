package com.iluwatar.dependentmapping;

import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class AlbumMapperTest {
  private AlbumMapper albumMapper;
  private Connection connection;
  private Statement statement;
  private ResultSet resultSet;

  void connectDB() throws SQLException {
    String DB_URL = "jdbc:h2:~/test";
    connection= DriverManager.getConnection(DB_URL);
    albumMapper = new AlbumMapper(connection);
    statement = connection.createStatement();
    statement.execute("create table if not exists albums(id int not null ,title varchar(50) not null);");
    statement.execute("create table if not exists tracks(seq int not null, albumid int not null, title varchar(50) not null);");
  }

  @Test
  void findstatement() throws SQLException {
    connectDB();
    String str= albumMapper.findstatement();
    assertNotNull(str);
  }

  @Test
  void update() throws SQLException {
    connectDB();
    statement.execute("truncate table albums");
    statement.execute("truncate table tracks");

    Album album=new Album((long)1,"firstAlbum");
    Track track1=new Track("firstTrack");
    Track track2=new Track("secondTrack");
    album.addDepObj(track1);
    album.addDepObj(track2);

    albumMapper.insertMasterObj(album);
    resultSet = statement.executeQuery("SELECT * from albums");
    int num=0;
    while (resultSet.next()) {
      num++;
    }
    assertEquals(1,num);
    String oldTitle=album.getTitle();
    while (resultSet.next()){
      assertEquals(oldTitle,resultSet.getString(2));
    }
    albumMapper.insertDepObj(track1,2,album);
    albumMapper.insertDepObj(track2,1,album);
    resultSet = statement.executeQuery("SELECT * from tracks");
    int[] index=new int[2];
    num = 0;
    while (resultSet.next()){
      index[num]=resultSet.getInt(1);
      num ++;
    }
    assertEquals(2,num);
    assertEquals(2,index[0]);
    assertEquals(1,index[1]);


    Album newAlbum=new Album((long)1,"firstNewAlbum");
    newAlbum.addDepObj(album.getDepObjs()[0]);
    newAlbum.addDepObj(album.getDepObjs()[1]);
    albumMapper.update(newAlbum);
    resultSet = statement.executeQuery("SELECT * from albums");
    num=0;
    while (resultSet.next()) {
      num++;
    }
    assertEquals(1,num);
    while (resultSet.next()){
      assertNotEquals(oldTitle,resultSet.getString(2));
    }
    resultSet = statement.executeQuery("SELECT * from tracks");
    index=new int[2];
    num = 0;
    while (resultSet.next()){
      index[num]=resultSet.getInt(1);
      num ++;
    }
    assertEquals(2,num);
    assertEquals(1,index[0]);
    assertEquals(2,index[1]);

  }

  @Test
  void insertDepObj() throws SQLException {
    connectDB();
    statement.execute("truncate table albums");
    statement.execute("truncate table tracks");
    Album album=new Album((long)23,"23Album");
    albumMapper.insertMasterObj(album);
    resultSet = statement.executeQuery("SELECT * from albums");
    int num=0;
    while (resultSet.next()) {
      num++;
    }
    assertEquals(1,num);
    Track track=new Track("firstTrack");
    albumMapper.insertDepObj(track,1,album);
    resultSet = statement.executeQuery("SELECT * from tracks");
    num=0;
    while (resultSet.next()) {
      num++;
    }
    assertEquals(1,num);
    Track track2=new Track("SecondTrack");
    albumMapper.insertDepObj(track,2,album);
    resultSet = statement.executeQuery("SELECT * from tracks");
    num=0;
    while (resultSet.next()) {
      num++;
    }
    assertEquals(2,num);

  }

  @Test
  void insertMasterObj() throws SQLException {
    connectDB();
    statement.execute("truncate table albums");
    int num=0;
    resultSet = statement.executeQuery("SELECT * from albums");
    while (resultSet.next()) {
      num++;
    }
    assertEquals(0,num);
    Album album=new Album((long)1,"firstAlbum");
    albumMapper.insertMasterObj(album);
    resultSet = statement.executeQuery("SELECT * from albums");
    while (resultSet.next()) {
       num++;
    }
    assertEquals(1,num);
  }

  @Test
  void doLoad() throws SQLException {
    connectDB();
    statement.execute("truncate table albums");
    statement.execute("truncate table tracks");

    Album album=new Album((long)1,"firstAlbum");
    Track track1=new Track("firstTrack");
    Track track2=new Track("secondTrack");
    album.addDepObj(track1);
    album.addDepObj(track2);

    albumMapper.insertMasterObj(album);
    resultSet = statement.executeQuery("SELECT * from albums");
    int num=0;
    while (resultSet.next()) {
      num++;
    }
    assertEquals(1,num);
    String oldTitle=album.getTitle();
    while (resultSet.next()){
      assertEquals(oldTitle,resultSet.getString(2));
    }
    albumMapper.insertDepObj(track1,1,album);
    albumMapper.insertDepObj(track2,2,album);
    resultSet = statement.executeQuery("SELECT * from tracks");
    num = 0;
    while (resultSet.next()){
      num ++;
    }
    assertEquals(2,num);

    PreparedStatement loadStatement = connection.prepareStatement(albumMapper.findstatement());
    loadStatement.setLong(1,album.getId().longValue());
    resultSet = loadStatement.executeQuery();
    resultSet.next();
    Album newAlbum=albumMapper.doLoad((long)1,resultSet);
    assertEquals(album.getId(),newAlbum.getId());
    assertEquals(album.getTitle(),newAlbum.getTitle());
    assertEquals(album.getDepObjs()[0].getTitle(),newAlbum.getDepObjs()[0].getTitle());
    assertEquals(album.getDepObjs()[1].getTitle(),newAlbum.getDepObjs()[1].getTitle());
  }
}