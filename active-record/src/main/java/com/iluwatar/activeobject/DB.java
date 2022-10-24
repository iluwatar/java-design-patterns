package com.iluwatar.activeobject;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.PreparedStatement;
//import com.mysql.jdbc.Statement;
import java.util.ArrayList;

public class DB {
    final String dbDomain;
    final String dbName;
    final String username;
    final String password;
    final String tableName;
    final Connection connection;

    public DB(String dbName, String username, String password, String dbDomain, String tableName) throws SQLException {
        this.dbDomain = dbDomain;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
        this.tableName = tableName;
        this.connection = DriverManager.getConnection(
                "jdbc:mysql://" + this.dbDomain + "/" +this.dbName,
                this.username,
                this.password);
    }

    public static void main(String[] args) throws SQLException {
        DB db = new DB("world","root","apple-trunks","localhost:3306", "city");
        ActiveRow activeRow = new ActiveRow(db, "3");
        ArrayList<String> s = (activeRow.Read());
        System.out.println(s);

    }
}
