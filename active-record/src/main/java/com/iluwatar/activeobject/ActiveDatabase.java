package com.iluwatar.activeobject;

import java.sql.SQLException;

/**
 * This class initialises frequently needed variables for the MySQLWorkbench Database.
 */
public class ActiveDatabase {

  final String dbDomain;
  final String dbName;
  final String username;
  final String password;
  final String tableName;

  /**
   * Constructor needed to instantiate the database object which is used with the MySQLWorkbench
   * Database.
   *
   * @param dbName    Name of the database as String.
   * @param username  Username for the database as String.
   * @param password  Password for the database as String.
   * @param dbDomain  The domain for the database as String. Tested on localhost:3306
   * @param tableName Name of the table which you wish to create the active row.
   */
  public ActiveDatabase(String dbName, String username, String password, String dbDomain,
      String tableName) {
    this.dbDomain = dbDomain;
    this.dbName = dbName;
    this.username = username;
    this.password = password;
    this.tableName = tableName;
  }

  public String getDbDomain() {
    return dbDomain;
  }

  public String getDbName() {
    return dbName;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getTableName() {
    return tableName;
  }

}
