package com.iluwatar.rowdatagateway;

import java.sql.SQLException;
import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersonGateway {
  /**
   * Public element for creating schema.
   */
  public static final String CREATE_SCHEMA_SQL =
          "CREATE TABLE IF NOT EXISTS PERSONS (ID NUMBER UNIQUE, FIRSTNAME"
                  + " VARCHAR(30), LASTNAME VARCHAR(30))";
  /**
   * Public element for deleting schema.
   */
  public static final String DELETE_SCHEMA_SQL = "DROP TABLE PERSONS IF EXISTS";

  /**
   * The data source.
   */
  private final DataSource dataSource;

  /**
   * The private element person.
   */
  private final Person person;

  /**
   * Public constructor.
   *
   * @param person1     person
   * @param dataSource1 datasource
   */
  public PersonGateway(final Person person1, final DataSource dataSource1) {
    this.person = person1;
    this.dataSource = dataSource1;
  }

  /**
   * Insert.
   *
   * @return status
   * @throws SQLException if any
   */
  public int insert() throws SQLException {
    var sql = "insert into PERSONS (FIRSTNAME, LASTNAME) values (?,?)";
    try (var connection = dataSource.getConnection();
         var preparedStatement =
                 connection.prepareStatement(sql)
    ) {
      preparedStatement.setString(1, person.getFirstName());
      preparedStatement.setString(2, person.getLastName());
      preparedStatement.executeUpdate();
      LOGGER.info(String.format("Insert: Id = %d FirstName = %s LastName = %s",
              person.getId(), person.getFirstName(), person.getLastName()));
      return person.getId();
    }
  }

  /**
   * Update.
   *
   * @return status
   * @throws SQLException if anh
   */
  public int update() throws SQLException {
    var sql = "update PERSONS set FIRSTNAME = ? , LASTNAME=? where ID =?";
    try (var connection = dataSource.getConnection();
         var preparedStatement =
                 connection.prepareStatement(sql)
    ) {
      preparedStatement.setString(1, person.getFirstName());
      preparedStatement.setString(2, person.getLastName());
      preparedStatement.setInt(3, person.getId());
      preparedStatement.executeUpdate();
      LOGGER.info(String.format("Update: Id = %d FirstName = %s LastName = %s",
              person.getId(), person.getFirstName(), person.getLastName()));
    }
    return person.getId();
  }

  /**
   * Delete.
   *
   * @return status
   * @throws SQLException if any
   */
  public int delete() throws SQLException {
    var sql = "delete from PERSONS where ID =?";
    try (var connection = dataSource.getConnection();
         var preparedStatement =
                 connection.prepareStatement(sql)
    ) {
      preparedStatement.setInt(1, person.getId());
      preparedStatement.executeUpdate();
      LOGGER.info(String.format("Delete: Id = %d FirstName = %s LastName = %s",
              person.getId(), person.getFirstName(), person.getLastName()));
    }
    return person.getId();
  }
}
