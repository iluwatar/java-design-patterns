/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
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
package com.iluwatar.activeobject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * ActiveRow attempts to implement the fundamental ruby on rails 'Active Record' design pattern in
 * Java.
 */
public class ActiveRow {

  String id;
  ActiveDatabase dataBase;
  Connection con;
  String read;
  String delete;
  String write;
  int columnCount;
  ArrayList<String> columns;
  ArrayList<String> contents = new ArrayList<>();

  /**
   * ActiveRow attempts to implement the fundamental ruby on rails 'Active Record' design pattern in
   * Java.
   *
   * @param dataBase A Database object which handles opening the connection.
   * @param id       The unique identifier of a row.
   */
  public ActiveRow(ActiveDatabase dataBase, String id) {
    this.dataBase = dataBase;
    this.id = id;
    initialise();
  }

  /**
   * This initialises the class by creating a connection and populating the column names and other
   * variables which are used in the program.
   */
  @Rowverride
  public void initialise() {
    try {
      con = DriverManager
          .getConnection("jdbc:mysql://" + dataBase.getDbDomain() + "/" + dataBase.getDbName(),
              dataBase.getUsername(), dataBase.getPassword());
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      Statement statement = con.createStatement();
      ResultSet columnSet = statement.executeQuery(
          "SELECT * FROM `" + dataBase.getDbName() + "`.`" + dataBase.getTableName() + "`");
      ArrayList<String> columnNames = new ArrayList<String>();
      ResultSetMetaData rsmd = columnSet.getMetaData();
      columnCount = rsmd.getColumnCount();
      for (int i = 1; i < columnCount + 1; i++) {
        String name = rsmd.getColumnName(i);
        columnNames.add(name);
      }
      this.columns = columnNames;
      read =
          "SELECT * FROM `" + dataBase.getDbName() + "`.`" + dataBase.getTableName() + "` WHERE ID="
              + this.id;
      delete = "DELETE FROM `" + dataBase.getDbName() + "`.`" + dataBase.getTableName() + "`"
          + " WHERE ID = '" + this.id + "';";
      write = "INSERT INTO `" + dataBase.getDbName() + "`.`" + dataBase.getTableName() + "`";
      statement.close();
      columnSet.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }


  }

  /**
   * Writes contents of the active row object to the chosen database.
   */
  @Rowverride
  public void write() {
    StringBuilder query = new StringBuilder();
    query.append(write);
    query.append(" VALUES (");
    for (String con : this.contents) {
      if (contents.indexOf(con) != this.contents.size() - 1) {
        query.append("'").append(con).append("' ,");
      } else {
        query.append("'").append(con).append("' )");
      }
    }

    try {
      PreparedStatement stmt = con.prepareStatement(query.toString());
      stmt.executeUpdate();

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * Deletes the current instance of the active row from the database based on the given ID value.
   */
  @Rowverride
  public void delete() {
    if (this.read().equals(new ArrayList<String>())) {
      System.out.println("Row does not exist.");
      return;
    }
    try {
      con.prepareStatement(delete).executeUpdate();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Reads the current active row.
   *
   * @return the current active row contents as Arraylist
   */
  @Rowverride
  public ArrayList<String> read() {
    try {
      Statement statement = con.createStatement();

      ResultSet rowResult = statement.executeQuery(read);
      while (rowResult.next()) {
        for (int col = 1; col <= columnCount; col++) {
          Object value = rowResult.getObject(col);
          if (value != null) {
            contents.add(value.toString());
          }
        }
      }
      rowResult.close();

    } catch (Exception e) {
      e.printStackTrace();
    }

    return this.contents;
  }

}
