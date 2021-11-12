package com.iluwatar.tupletable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Database {

  /**
   * Method implements connecting to in memory sqlite database.
   *
   * @return conn
   * @throws SQLException           if encounters connecting to sql.
   * @throws ClassNotFoundException if org.sqlite.JDBC is not found class.
   */
  public Connection getConnection() throws SQLException, ClassNotFoundException {
    Class.forName("org.sqlite.JDBC");
    String url = "jdbc:sqlite::memory:test.db";
    Connection conn = DriverManager.getConnection(url);
    LOGGER.info("Connected database successfully...");
    return conn;
  }
}
