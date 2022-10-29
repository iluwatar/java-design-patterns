/*
 * This project is licensed under the MIT license.
 * Module model-view-viewmodel is using ZK framework
 * licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.dependentmapping;

import com.iluwatar.dependentmapping.structure.DependentObj;
import com.iluwatar.dependentmapping.structure.Mapper;
import com.iluwatar.dependentmapping.structure.MasterObj;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The album mapper class handles all the SQL for tracks and thus
 * defines the SQL statements that access the tracks table.
 * The tracks are loaded into the album whenever the album is loaded.
 * When the album is updated all the tracks are deleted and reinserted.
 */
public class AlbumMapper implements Mapper {

  /**
   * parameter index used to set database sentence.
   */
  private final int parameterIndex1 = 1;

  /**
   * parameter index used to set database sentence.
   */
  private final int parameterIndex2 = 2;

  /**
   * parameter index used to set database sentence.
   */
  private final int parameterIndex3 = 3;

  /**
   * DataBase connection.
   */
  private final Connection db;

  /**
   * Construction method.
   *
   * @param newDb the connection of database.
   */
  public AlbumMapper(final Connection newDb) {
    this.db = newDb;
  }

  /**
   * Index to get the album instance.
   */
  private final int columnIndex = 3;

  /**
   * return the semi finished product statement, by fixing the id part you can got the statement which can help you
   * load the master object(the album).
   *
   * @return the data of album and tracks in database.
   */
  @Override
  public String findstatement() {
    return
      "SELECT ID, a.title, t.title as trackTitle"
        + "  FROM albums a, tracks t"
        + "  WHERE a.ID = ? AND t.albumID = a.ID"
        + "  ORDER BY t.seq";
  }

  /**
   * Load the specific album through the database by using findstatement and album id.
   *
   * @param id the id of album.
   * @param rs the database result set.
   * @return the album which has correct id and rs.
   * @throws SQLException the exception of SQL.
   */
  public Album doLoad(Long id, ResultSet rs) throws SQLException {
    String title = rs.getString(parameterIndex2);
    Album result = new Album(id, title);
    loadTracks(result, rs);
    return result;
  }

  /**
   * Load tracks of the specific album through the database result set got by using findstatement.
   *
   * @param arg the album of which the tracks you will load.
   * @param rs  Got by using findstatement and album id.
   * @throws SQLException exception of SQL.
   */
  public void loadTracks(Album arg, ResultSet rs) throws SQLException {
    arg.addDepObj(newTrack(rs));
    while (rs.next()) {
      arg.addDepObj(newTrack(rs));
    }
  }

  /**
   * new a track through the result set.
   *
   * @param rs the result set from database.
   * @return a new track object.
   * @throws SQLException the exception of SQL.
   */
  public Track newTrack(ResultSet rs) throws SQLException {
    String title = rs.getString(3);
    Track newTrack = new Track(title);
    return newTrack;
  }

  /**
   * Update specific album in database.
   *
   * @param arg Master class instance.
   * @throws SQLException exception of SQL
   */
  @Override
  public void update(final MasterObj arg) throws SQLException {
    PreparedStatement updateStatement = null;
    try {
      updateStatement = db.prepareStatement(
        "UPDATE albums SET title = ? WHERE id = ?");
      Album album = (Album) arg;
      updateStatement.setLong(parameterIndex2, album.getId());
      updateStatement.setString(parameterIndex1, album.getTitle());
      updateStatement.execute();
      updateDepObjs(album);
    } finally {
      updateStatement.close();
      System.out.println("--update master object work done--");
    }
  }

  /**
   * Update all the tracks which belong to the album.
   *
   * @param arg the album of which the tracks you want update.
   * @throws SQLException exception of SQL.
   */
  @Override
  public void updateDepObjs(final MasterObj arg) throws SQLException {
    PreparedStatement deleteTracksStatement = null;
    try {
      deleteTracksStatement = db.prepareStatement(
        "DELETE from tracks WHERE albumID = ?");
      Album album = (Album) arg;
      deleteTracksStatement.setLong(parameterIndex1, album.getId().longValue());
      deleteTracksStatement.execute();
      for (int i = 0; i < album.getDepObjs().length; i++) {
        Track track = album.getDepObjs()[i];
        insertDepObj(track, i + 1, arg);
      }
    } finally {
      deleteTracksStatement.close();
      System.out.println("--update dependent objects work done--");
    }
  }

  /**
   * insert a track into an album.
   *
   * @param dependentObj dependent class instance.
   * @param seq          sequence.
   * @param masterObj    master class instance.
   * @throws SQLException exception of SQL.
   */
  @Override
  public void insertDepObj(final DependentObj dependentObj, final int seq, final MasterObj masterObj) throws SQLException {
    PreparedStatement insertTracksStatement = null;
    try {
      insertTracksStatement =
        db.prepareStatement(
          "INSERT INTO tracks "
            + "(seq, albumID, title)"
            + " VALUES (?, ?, ?)");
      Album album = (Album) masterObj;
      Track track = (Track) dependentObj;
      insertTracksStatement.setInt(parameterIndex1, seq);
      insertTracksStatement.setLong(parameterIndex2, album.getId());
      insertTracksStatement.setString(parameterIndex3, track.getTitle());
      insertTracksStatement.execute();
    } finally {
      insertTracksStatement.close();
      System.out.println("--insert dependent object work done--");
    }
  }

  /**
   * Insert Master obj (here is album) into database.
   *
   * @param masterObj the album you want insert.
   * @throws SQLException the exception of SQL.
   */
  public void insertMasterObj(final MasterObj masterObj) throws SQLException {
    PreparedStatement insertTracksStatement = null;
    try {
      insertTracksStatement =
        db.prepareStatement(
          "INSERT INTO albums "
            + "(id, title)"
            + " VALUES (?, ?)");
      Album album = (Album) masterObj;
      insertTracksStatement.setLong(parameterIndex1, album.getId());
      insertTracksStatement.setString(parameterIndex2, album.getTitle());
      insertTracksStatement.execute();
    } finally {
      insertTracksStatement.close();
      System.out.println("--insert Master object work done--");
    }
  }
}
