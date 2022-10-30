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
