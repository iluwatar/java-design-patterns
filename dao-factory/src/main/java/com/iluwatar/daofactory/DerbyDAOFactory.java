package com.iluwatar.daofactory;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class DerbyDAOFactory extends DAOFactory {

    /**
     * method to create Derby connections
     *
     * @return a Connection
     */
    static Connection createConnection() {

        Connection conn1 = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            String dbURL1 = "jdbc:derby:dao-factory/DerbyDB;create=true";
            conn1 = DriverManager.getConnection(dbURL1);
            if (conn1 != null) {
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("Connected to database #1");
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(ex.getMessage());
            }
        }
        return conn1;
    }

    /**
     * Override getUserDAO method
     *
     * @return DerbyUserDAO
     */
    @Override
    public UserDAO getUserDAO() {
        // DerbyUserDAO implements UserDAO
        return new DerbyUserDAO();
    }

}
