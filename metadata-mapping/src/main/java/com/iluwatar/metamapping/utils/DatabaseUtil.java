package com.iluwatar.metamapping.utils;

import java.sql.SQLException;
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
  private DatabaseUtil() {}

  /**
   * Create database.
   */
  static {
    LOGGER.info("create h2 database");
    var source = new JdbcDataSource();
    source.setURL(DB_URL);
    try (var statement = source.getConnection().createStatement()) {
      statement.execute(CREATE_SCHEMA_SQL);
    } catch (SQLException e) {
      LOGGER.error("unable to create h2 data source", e);
    }
  }
}