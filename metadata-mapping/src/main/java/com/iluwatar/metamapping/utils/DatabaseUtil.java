package com.iluwatar.metamapping.utils;

import com.iluwatar.metamapping.model.User;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Slf4j
public class DatabaseUtil {
  private static final String DB_URL = "jdbc:h2:mem:metamapping";
  private static final String CREATE_SCHEMA_SQL = "DROP TABLE IF EXISTS `user`;" +
                                                  "CREATE TABLE `user` (\n" +
                                                  "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                                                  "  `username` varchar(255) NOT NULL,\n" +
                                                  "  `password` varchar(255) NOT NULL,\n" +
                                                  "  PRIMARY KEY (`id`)\n" +
                                                  ");";
  private static DataSource dataSource = null;

  public static void createDataSource(){
    LOGGER.info("create h2 database");
    try {
      var _dataSource = new JdbcDataSource();
      _dataSource.setURL(DB_URL);
      Connection connection = null;
      connection = _dataSource.getConnection();
      var statement = connection.createStatement();
      statement.execute(CREATE_SCHEMA_SQL);
      dataSource = _dataSource;
      connection.close();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }
}
