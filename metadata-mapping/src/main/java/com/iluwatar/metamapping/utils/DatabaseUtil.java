package com.iluwatar.metamapping.utils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

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
  private static DataSource dataSource = null;

  /**
   * Create database.
   */
  public static void createDataSource() {
    LOGGER.info("create h2 database");
    try {
      var dSource = new JdbcDataSource();
      dSource.setURL(DB_URL);
      Connection connection = null;
      connection = dSource.getConnection();
      var statement = connection.createStatement();
      statement.execute(CREATE_SCHEMA_SQL);
      dataSource = dSource;
      connection.close();
    } catch (SQLException throwables) {
      LOGGER.error("unable to create h2 data source", throwables);
    }
  }
}
