package com.iluwatar.active.record;

import org.h2.tools.DeleteDbFiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
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
}
