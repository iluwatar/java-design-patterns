package com.iluwatar.activeobject;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class DB {
  final String dbDomain;
  final String dbName;
  final String username;
  final String password;
  final String tableName;

    public DB(String dbName, String username, String password, String dbDomain, String tableName) throws SQLException {
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
