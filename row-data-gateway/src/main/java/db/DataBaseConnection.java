package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private final static String DATABASE_CONNECTION = "jdbc:mysql://localhost:3306/parrot?user=admin&password=admin123";

    /**
     * Get Database connection
     *
     * @return Connection
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_CONNECTION);
    }

    /**
     * Closes the Database connection
     *
     * @param connection
     * @throws SQLException
     */
    public void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
