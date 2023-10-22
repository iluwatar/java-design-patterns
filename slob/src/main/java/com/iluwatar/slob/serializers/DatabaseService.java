package com.iluwatar.slob.serializers;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcDataSource;

@Slf4j
public class DatabaseService {

  public static final String CREATE_SCHEMA_SQL =
      "CREATE TABLE IF NOT EXISTS CUSTOMERS (ID NUMBER UNIQUE, NAME"
          + " VARCHAR(30),PRODUCTS VARCHAR)";
  public static final String DELETE_SCHEMA_SQL = "DROP TABLE CUSTOMERS IF "
      + "EXISTS";
  private static final String DB_URL = "jdbc:h2:~/test";
  private static final String INSERT = "insert into customers (id,name, products) values (?,?,?)";

  private static final String SELECT = "select products from customers where id = ?";

  private static final DataSource dataSource = createDataSource();

  private static DataSource createDataSource() {
    var dataSource = new JdbcDataSource();
    dataSource.setURL(DB_URL);
    return dataSource;
  }

  public void shutDownService()
      throws SQLException {
    try (var connection = dataSource.getConnection();
        var statement = connection.createStatement()) {
      statement.execute(DELETE_SCHEMA_SQL);
    }
  }

  public void startupService()
      throws SQLException {
    try (var connection = dataSource.getConnection();
        var statement = connection.createStatement()) {
      statement.execute(CREATE_SCHEMA_SQL);
    }
  }

  public void insert(int id, String name, Object data)
      throws SQLException {
    try (var connection = dataSource.getConnection();
        var insert = connection.prepareStatement(INSERT)) {
      insert.setInt(1, id);
      insert.setString(2, name);
      insert.setObject(3, data);
      insert.execute();
    }
  }

  protected Object select(final long id1, String columnsName) throws SQLException {
    ResultSet resultSet = null;
    try (var connection = dataSource.getConnection();
        var preparedStatement =
            connection.prepareStatement(SELECT)
    ) {
      var result = "";
      preparedStatement.setLong(1, id1);
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        result = resultSet.getString(columnsName);
      }
      return result;
    } finally {
      if (resultSet != null) {
        resultSet.close();
      }
    }
  }
}
