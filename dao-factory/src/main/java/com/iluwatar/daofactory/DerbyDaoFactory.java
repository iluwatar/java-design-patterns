package com.iluwatar.daofactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;

/**
 * This concrete factory extends DAOFactory.
 */
@Slf4j
public class DerbyDaoFactory extends AbstractDaoFactory {
  /**
   * Database Url.
   */
  private static final String DBURL = "jdbc:derby:dao-factory/DerbyDB;create=true";

  /**
   * Instantiates a DerbyDAOFactory.
   */
  public DerbyDaoFactory() {
    super();
  }

  /**
   * method to create Derby connections.
   *
   * @return a Connection
   */
  public static Connection createConnection() {

    Connection conn1 = null;
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
      conn1 = DriverManager.getConnection(DBURL);
      if (LOGGER.isInfoEnabled()) {
        LOGGER.info("Connected to database #1");
      }

    } catch (SQLException | ClassNotFoundException ex) {
      if (LOGGER.isErrorEnabled()) {
        LOGGER.error(ex.getMessage());
      }
    }
    return conn1;
  }

  /**
   * Override getUserDAO method.
   *
   * @return DerbyUserDAO
   */
  @Override
  public UserDao getUserDao() {
    // DerbyUserDAO implements UserDAO
    return new DerbyUserDao();
  }

}
