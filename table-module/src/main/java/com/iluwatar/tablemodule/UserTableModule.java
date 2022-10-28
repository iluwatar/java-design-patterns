package com.iluwatar.tablemodule;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;


/**
 * This class organizes domain logic with the user table in the
 * database. A single instance of this class contains the various
 * procedures that will act on the data.
 */
@Slf4j
public class UserTableModule {
  /**
   * Public element for creating schema.
   */
  public static final String CREATE_SCHEMA_SQL =
          "CREATE TABLE IF NOT EXISTS USERS (ID NUMBER, USERNAME VARCHAR(30) "
                  + "UNIQUE,PASSWORD VARCHAR(30))";
  /**
   * Public element for deleting schema.
   */
  public static final String DELETE_SCHEMA_SQL = "DROP TABLE USERS IF EXISTS";
  private final DataSource dataSource;


  /**
   * Public constructor.
   *
   * @param userDataSource the data source in the database
   */
  public UserTableModule(final DataSource userDataSource) {
    this.dataSource = userDataSource;
  }


  /**
   * Login using username and password.
   *
   * @param username the username of a user
   * @param password the password of a user
   * @return the execution result of the method
   * @throws SQLException if any error
   */
  public int login(final String username, final String password)
          throws SQLException {
    var sql = "select count(*) from USERS where username=? and password=?";
    ResultSet resultSet = null;
    try (var connection = dataSource.getConnection();
         var preparedStatement =
                 connection.prepareStatement(sql)
    ) {
      var result = 0;
      preparedStatement.setString(1, username);
      preparedStatement.setString(2, password);
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        result = resultSet.getInt(1);
      }
      if (result == 1) {
        LOGGER.info("Login successfully!");
      } else {
        LOGGER.info("Fail to login!");
      }
      return result;
    } finally {
      if (resultSet != null) {
        resultSet.close();
      }
    }
  }

  /**
   * Register a new user.
   *
   * @param user a user instance
   * @return the execution result of the method
   * @throws SQLException if any error
   */
  public int registerUser(final User user) throws SQLException {
    var sql = "insert into USERS (username, password) values (?,?)";
    try (var connection = dataSource.getConnection();
         var preparedStatement =
                 connection.prepareStatement(sql)
    ) {
      preparedStatement.setString(1, user.getUsername());
      preparedStatement.setString(2, user.getPassword());
      var result = preparedStatement.executeUpdate();
      LOGGER.info("Register successfully!");
      return result;
    }
  }
}
