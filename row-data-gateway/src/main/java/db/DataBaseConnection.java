package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private final String DATABASE_CONNECTION = "jdbc:mysql://localhost:3306/parrot?user=admin&password=admin123";

    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_CONNECTION);

        return connection;
    }

    public void closeConnection(Connection connection) throws SQLException {
        if(connection != null) {
            connection.close();
        }
    }
}
