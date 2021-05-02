package com.iluwatar.tablemodule;

/**
 * User Schema SQL Class.
 */
public final class UserSchemaSql {

  /**
   * Private constructor.
   */
  private UserSchemaSql() {

  }

  /**
   * Private element for creating schema.
   */
  public static final String CREATE_SCHEMA_SQL =
          "CREATE TABLE IF NOT EXISTS USERS (ID NUMBER, USERNAME VARCHAR(30) "
                  + "UNIQUE,PASSWORD VARCHAR(30))";

  /**
   * Private element for deleting schema.
   */
  public static final String DELETE_SCHEMA_SQL = "DROP TABLE USERS IF EXISTS";
}
