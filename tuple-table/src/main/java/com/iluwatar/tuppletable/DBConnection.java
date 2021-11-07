package com.iluwatar.tuppletable;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    /**
     * @should @return connection
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Connection getConnection() throws SQLException, ClassNotFoundException, IOException {
        FileInputStream fis=new FileInputStream("src/main/resources/connection.prop");
        Properties p=new Properties();
        p.load (fis);
        Class.forName((String) p.get ("Dname"));
        Connection conn = DriverManager.getConnection(
                (String) p.get ("URL"), (String) p.get ("Uname"), (String) p.get ("password"));
        System.out.println("Connected database successfully...");
        p.clear();
        fis.close();
        return conn;
    }

    /**
     * Close the prepared statement and the connection in a proper way
     * @should close the connection and prepared statement
     * @param conn
     * @param ps
     */
    public void closeConnection(Connection conn, PreparedStatement ps) {
        try {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing connections");
            e.printStackTrace();
        }
    }

}
