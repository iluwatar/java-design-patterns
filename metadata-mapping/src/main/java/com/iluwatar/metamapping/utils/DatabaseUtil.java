package com.iluwatar.metamapping.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcDataSource;

/**
 * Create h2 database.
 */
@Slf4j
public class DatabaseUtil {
  private static final String DB_URL = "jdbc:h2:mem:metamapping";
  private static final String CREATE_SCHEMA_SQL = "DROP TABLE IF EXISTS `user`;"
                                                + "CREATE TABLE `user` (\n"
                                                + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                                                + "  `username` varchar(255) NOT NULL,\n"
                                                + "  `password` varchar(255) NOT NULL,\n"
                                                + "  PRIMARY KEY (`id`)\n"
                                                + ");";
  /**
   * Hide constructor.
   */
  private DatabaseUtil() { }

  /**
   * Create database.
   */
  public static void createDataSource() throws SQLException {
    LOGGER.info("create h2 database");
    Connection connection = null;
    Statement statement = null;
    try {
      var source = new JdbcDataSource();
      source.setURL(DB_URL);
      connection = source.getConnection();
      statement = connection.createStatement();
      statement.execute(CREATE_SCHEMA_SQL);
    } catch (SQLException e) {
      LOGGER.error("unable to create h2 data source", e);
    } finally {
      statement.close();
      connection.close();
    }
  }
}
