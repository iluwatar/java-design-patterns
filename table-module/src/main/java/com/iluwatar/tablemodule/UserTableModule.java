package com.iluwatar.tablemodule;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.extern.slf4j.Slf4j;


/**
 * This class organizes domain logic with the user table in the
 * database. A single instance of this class contains the various
 * procedures that will act on the data.
 */
@Slf4j
public class UserTableModule {
  private final DataSource dataSource;
  private Connection connection = null;
  private ResultSet resultSet = null;
  private PreparedStatement preparedStatement = null;

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
    try {
      var result = 0;
      connection = dataSource.getConnection();
      String sql = "select count(*) from USERS where username=? and password=?";
      preparedStatement = connection.prepareStatement(sql);
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
      connection.close();
      preparedStatement.close();
      resultSet.close();
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
    try {
      var result = 0;
      connection = dataSource.getConnection();
      String sql = "insert into USERS (username, password) values (?,?)";
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, user.getUsername());
      preparedStatement.setString(2, user.getPassword());
      result = preparedStatement.executeUpdate();
      LOGGER.info("Register successfully!");
      return result;
    } finally {
      connection.close();
      preparedStatement.close();
    }
  }
}
