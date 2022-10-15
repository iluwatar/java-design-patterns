package com.iluwater.daofactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A DAO implementation for the AccountDAO in Postgres
 */
public class PostgresAccountDAO implements AccountDAO {
    public PostgresAccountDAO() {}

    /**
     * A method to insert a new instance of Account to the database
     *
     * @param firstName The first_name attribute of the Account
     * @param lastName The last_name attribute of the Account
     * @param location The location attribute of the Account
     * @return True if the insertion is executed successfully, and False otherwise
     */
    public boolean insertAccount(String firstName, String lastName, String location) {
        Connection con = PostgresDAOFactory.createConnection();
        Statement stmt;

        // Wrap each string attribute with a quotation for query
        firstName = "'" + firstName + "'";
        lastName = "'" + lastName + "'";
        location = "'" + location + "'";

        // Combine the query with the variable value
        String sql = "INSERT INTO account (first_name,last_name,location) "
                + "VALUES (" + firstName + "," + lastName + "," + location + ");";

        try {
            // Creating statement and executing query
            stmt = con.createStatement();
            stmt.executeUpdate(sql);

            // Closing the connection
            stmt.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * A method to find an existing Account from the database.
     * Ex. Find account that the firstName is Bob and lastName is John.
     * Others attribute can be left as null.
     *
     * @param firstName The first_name attribute of the Account
     * @param lastName The last_name attribute of the Account
     * @param location The location attribute of the Account
     * @return An Account TO containing the attribute if found, and null otherwise
     */
    public Account findAccount(String firstName, String lastName, String location) {
        Account template = new Account();
        Connection con = PostgresDAOFactory.createConnection();
        Statement stmt;
        ResultSet rs;
        String sql = "SELECT * FROM account WHERE (";
        boolean firstCondition = true;

        // Combining the query with the variable value depending on the
        // attribute that has been passed to the function
        if (firstName == null && lastName == null && location == null) {
            return template;
        }
        // Combining the firstName attribute
        if (firstName != null && !firstName.equals("")) {
            firstName = "'" + firstName + "'";
            sql += "first_name = " +firstName;
            firstCondition = false;
        }
        // Combining the lastName attribute
        if (lastName != null && !lastName.equals("")) {
            lastName = "'" + lastName + "'";
            if (firstCondition) {
                sql += "last_name = " + lastName;
                firstCondition = false;
            } else {
                sql += " AND last_name = " + lastName;
            }
        }
        // Combining the location attribute
        if (location != null && !location.equals("")) {
            location = "'" + location + "'";
            if (firstCondition) {
                sql += "location = " + location;
            } else {
                sql += " AND location = " + location;
            }
        }
        // Closing the sql statement
        sql += ");";

        try {
            // Creating statement and executing query
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            // Passing the queried item to an Account TO
            while (rs.next()) {
                int id = rs.getInt("id");
                String  fName = rs.getString("first_name");
                String lName  = rs.getString("last_name");
                String  loc = rs.getString("location");
                template.setId(id);
                template.setFirstName(fName);
                template.setLastName(lName);
                template.setLocation(loc);
            }

            // Closing the connection
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return template;
    }

    /**
     * A method to delete an existing instance of Account in the database.
     *
     * @param firstName The first_name attribute of the Account
     * @param lastName The last_name attribute of the Account
     * @param location The location attribute of the Account
     * @return True if the deletion is executed successfully, and False otherwise
     */
    public boolean deleteAccount(String firstName, String lastName, String location) {
        Connection con = PostgresDAOFactory.createConnection();
        Statement stmt;
        String sql = "DELETE FROM account WHERE (";
        boolean firstCondition = true;

        // Combining the query with the variable value depending on the
        // attribute that has been passed to the function
        if (firstName == null && lastName == null && location == null) {
            return false;
        }
        // Combining the firstName attribute
        if (firstName != null && !firstName.equals("")) {
            firstName = "'" + firstName + "'";
            sql += "first_name = " + firstName;
            firstCondition = false;
        }
        // Combining the lastName attribute
        if (lastName != null && !lastName.equals("")) {
            lastName = "'" + lastName + "'";
            if (firstCondition) {
                sql += "last_name = " + lastName;
                firstCondition = false;
            } else {
                sql += " AND last_name = " + lastName;
            }
        }
        // Combining the location attribute
        if (location != null && !location.equals("")) {
            location = "'" + location + "'";
            if (firstCondition) {
                sql += "location = " + location;
            } else {
                sql += " AND location = " + location;
            }
        }
        // Closing the sql statement
        sql += ");";

        try {
            // Creating statement and executing query
            stmt = con.createStatement();
            stmt.executeUpdate(sql);

            // Closing the connection
            stmt.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * A method to update an existing instance of Account in the database.
     * The parameter contains an Account Object with the updated attribute
     *
     * @param acc An Account Object with the updated attribute
     * @return True if the update is executed successfully, and False otherwise
     */
    public boolean updateAccount(Account acc) {
        Connection con = PostgresDAOFactory.createConnection();
        Statement stmt;
        String sql = "UPDATE account SET ";
        boolean firstCondition = true;

        // Combining the query with the updated variable value
        // within the Account TO
        // Combining the firstName from Account TO
        if (acc.getFirstName() != null) {
            sql += "first_name = '" + acc.getFirstName() + "'";
            firstCondition = false;
        }
        // Combining the lastName from Account TO
        if (acc.getLastName() != null) {
            String lastName = "'" + acc.getLastName() + "'";
            if (firstCondition) {
                sql += "last_name = " + lastName;
                firstCondition = false;
            } else {
                sql += ", last_name = " + lastName;
            }
        }
        // Combining the Location from Account TO
        if (acc.getLocation() != null) {
            String location = "'" + acc.getLocation() + "'";
            if (firstCondition) {
                sql += "location = " + location;
            } else {
                sql += ", location = " + location;
            }
        }
        // Closing the sql statement
        sql += " WHERE id = " + acc.getId() + ";";

        try {
            // Creating statement and executing query
            stmt = con.createStatement();
            stmt.executeUpdate(sql);

            // Closing the connection
            stmt.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
