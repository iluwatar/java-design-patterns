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
      final String dbUrl = "jdbc:derby:dao-factory/DerbyDB;create=true";
      conn1 = DriverManager.getConnection(dbUrl);
      if (conn1 != null && LOGGER.isInfoEnabled()) {
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
  public UserDao getUserDAO() {
    // DerbyUserDAO implements UserDAO
    return new DerbyUserDao();
  }

}
