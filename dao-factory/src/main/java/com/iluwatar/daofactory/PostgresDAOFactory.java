package com.iluwatar.daofactory;

import java.sql.*;

/**
 * A DAO implementation for the Postgres Factory
 */
public class PostgresDAOFactory extends DAOFactory {
    // Connection variable information to Postgres database
    private static final String conn = "jdbc:postgresql://localhost:5432/dao_factory";
    private static final String user = "postgres";
    private static final String pass = "123";

    /**
     * Return a DAO for Account
     * @return A new AccountDAO
     */
    public AccountDAO getAccountDAO() {
        return new PostgresAccountDAO();
    }

    /**
     * Return a DAO for Order
     * @return A new OrderDAO
     */
    public OrderDAO getOrderDAO() {
        return new PostgresOrderDAO();
    }

    /**
     * A method to create a connection to the database
     * @return The connection to the database
     */
    public static Connection createConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(conn, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return con;
    }
}
