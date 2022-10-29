package com.iluwatar.dependentmapping;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.h2.command.dml.Select;

/**
 * Some objects naturally appear in the context of other objects. Whenever you load or save the base album, you can load
 * or save the tracks in the album. If these tracks are not referenced by any other table in the database,
 * you can simplify the mapping process by having the album mapper perform track mapping and treat this mapping
 * as a related mapping.
 *
 * <p>In this example, I use the basic method of dependent-mapping to show an example described above.
 */
public class App {

  public static AlbumMapper albumMapper;
  public static Connection connection;
  public static Statement statement;
  public static ResultSet resultSet;
  public static final String DB_URL = "jdbc:h2:~/test";

  /**
   * Return the basic "Select" sentence of SQL.
   *
   * @param args not args need.
   */
  public static void main(String[] args) throws SQLException {
    connection = DriverManager.getConnection(DB_URL);
    albumMapper = new AlbumMapper(connection);
    statement = connection.createStatement();
    statement.execute("create table if not exists albums(id int not null ,title varchar(50) not null);");
    statement.execute("create table if not exists tracks(seq int not null, albumid int not null, title varchar(50) not null);");
    statement.execute("truncate table albums");
    statement.execute("truncate table tracks");

    resultSet = statement.executeQuery("SELECT * from albums");
    displayAlbums(resultSet);
    resultSet = statement.executeQuery("SELECT * from tracks");
    displayTracks(resultSet);

    Album album = new Album((long) 1, "firstAlbum");
    albumMapper.insertMasterObj(album);
    resultSet = statement.executeQuery("SELECT * from albums");
    displayAlbums(resultSet);
    displayTracks(resultSet);
    System.out.println("Adding two tracks in the object:");
    album.addDepObj(new Track("firstTrack"));
    album.addDepObj(new Track("SecondTrack"));
    albumMapper.update(album);
    resultSet = statement.executeQuery("SELECT * from tracks");
    displayTracks(resultSet);

    System.out.println("Adding one track in the object:");
    album.addDepObj(new Track("ThirdTrack"));
    albumMapper.update(album);
    resultSet = statement.executeQuery("SELECT * from tracks");
    displayTracks(resultSet);

  }

  /**
   * display the current data of table "albums".
   *
   * @param resultSet the result set got from table "albums".
   * @throws SQLException the exception of SQL.
   */
  public static void displayAlbums(ResultSet resultSet) throws SQLException {
    System.out.println("\n----------albums-----------");
    int num = 0;
    System.out.println("album_id" + "\t" + "title");
    while (resultSet.next()) {
      num++;
      System.out.println(resultSet.getInt(1) + "\t\t\t" + resultSet.getString(2));
    }
    System.out.println("----------------------------");
    System.out.println(num + " album(s) in total\n");
  }

  /**
   * display the current data of table "tracks".
   *
   * @param resultSet the result set got from table "tracks".
   * @throws SQLException the exception of SQL.
   */
  public static void displayTracks(ResultSet resultSet) throws SQLException {
    System.out.println("\n----------aracks-----------");
    int num = 0;
    System.out.println("track_seq" + "\t" + "album_id" + "\t" + "title");
    while (resultSet.next()) {
      num++;
      System.out.println(resultSet.getInt(1) + "\t\t\t" + resultSet.getInt(2) + "\t\t\t" + resultSet.getString(3));
    }
    System.out.println("----------------------------");
    System.out.println(num + " track(s) in total\n");
  }
}
