package com.iluwatar.active.record;

import org.h2.tools.DeleteDbFiles;

import java.sql.*;

/**
 * An utility class that holds and encapsulates all the DB related operations.
 *
 * Created by Stephen Lazarionok.
 */
public class DB {

    static {
        DeleteDbFiles.execute("~", "test", true);
        try {
            Class.forName("org.h2.Driver");
            final Connection connection = getConnection();
            final Statement statement = connection.createStatement();
            statement.execute("create table wand(id BIGINT primary key, length_inches REAL, wood varchar(100), core varchar(100))");
            statement.close();
            connection.close();
        } catch (final SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Provides a connection to the database configured.
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:~/test");
    }

    public static void closeConnection(final Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Failed to close a connection");
        }
    }

    public static void closePreparedStatement(final PreparedStatement ps) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println("Failed to close a prepared statement");
        }
    }
}
