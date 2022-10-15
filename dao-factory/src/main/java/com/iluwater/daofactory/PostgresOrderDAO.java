package com.iluwater.daofactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A DAO implementation for the OrderDAO in Postgres
 */
public class PostgresOrderDAO implements OrderDAO {
    public PostgresOrderDAO() {}

    /**
     * A method to insert a new instance of Order to the database
     *
     * @param senderName The sender_name attribute of the Order
     * @param rcvrName The receiver_name attribute of the Order
     * @param destination The destination attribute of the Order
     * @return True if the insertion is executed successfully, and False otherwise
     */
    public boolean insertOrder(String senderName, String rcvrName, String destination) {
        Connection con = PostgresDAOFactory.createConnection();
        Statement stmt;

        // Wrap each string attribute with a quotation for query
        senderName = "'" + senderName + "'";
        rcvrName = "'" + rcvrName + "'";
        destination = "'" + destination + "'";

        // Combine the query with the variable value
        String sql = "INSERT INTO orders (sender_name,receiver_name,destination) "
                + "VALUES (" + senderName + "," + rcvrName + "," + destination + ");";

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
     * A method to find an existing Order from the database.
     * Ex. Find order that the senderName is John and receiverName is Hiro.
     * Others attribute can be left as null.
     *
     * @param senderName The sender_name attribute of the Order
     * @param rcvrName The receiver_name attribute of the Order
     * @param destination The destination attribute of the Order
     * @return An Order TO containing the attribute if found, and null otherwise
     */
    public Order findOrder(String senderName, String rcvrName, String destination) {
        Order template = new Order();
        Connection con = PostgresDAOFactory.createConnection();
        Statement stmt;
        ResultSet rs;
        String sql = "SELECT * FROM orders WHERE (";
        boolean firstCondition = true;

        // Combining the query with the variable value depending on the
        // attribute that has been passed to the function
        if (senderName == null && rcvrName == null && destination == null) {
            return template;
        }
        // Combining the senderName attribute
        if (senderName != null && !senderName.equals("")) {
            senderName = "'" + senderName + "'";
            sql += "sender_name = " +senderName;
            firstCondition = false;
        }
        // Combining the receiverName attribute
        if (rcvrName != null && !rcvrName.equals("")) {
            rcvrName = "'" + rcvrName + "'";
            if (firstCondition) {
                sql += "receiver_name = " + rcvrName;
                firstCondition = false;
            } else {
                sql += " AND receiver_name = " + rcvrName;
            }
        }
        // Combining the destination attribute
        if (destination != null && !destination.equals("")) {
            destination = "'" + destination + "'";
            if (firstCondition) {
                sql += "destination = " + destination;
            } else {
                sql += " AND destination = " + destination;
            }
        }
        // Closing the sql statement
        sql += ");";

        try {
            // Creating statement and executing query
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            // Passing the queried item to an Order TO
            while (rs.next()) {
                int id = rs.getInt("id");
                String  sndName = rs.getString("sender_name");
                String rcvName  = rs.getString("receiver_name");
                String  dest = rs.getString("destination");
                template.setId(id);
                template.setSenderName(sndName);
                template.setReceiverName(rcvName);
                template.setDestination(dest);
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
     * A method to delete an existing instance of Order in the database.
     *
     * @param senderName The sender_name attribute of the Order
     * @param rcvrName The receiver_name attribute of the Order
     * @param destination The destination attribute of the Order
     * @return True if the deletion is executed successfully, and False otherwise
     */
    public boolean deleteOrder(String senderName, String rcvrName, String destination) {
        Connection con = PostgresDAOFactory.createConnection();
        Statement stmt;
        String sql = "DELETE FROM orders WHERE (";
        boolean firstCondition = true;

        // Combining the query with the variable value depending on the
        // attribute that has been passed to the function
        if (senderName == null && rcvrName == null && destination == null) {
            return false;
        }
        // Combining the senderName attribute
        if (senderName != null && !senderName.equals("")) {
            senderName = "'" + senderName + "'";
            sql += "sender_name = " +senderName;
            firstCondition = false;
        }
        // Combining the receiverName attribute
        if (rcvrName != null && !rcvrName.equals("")) {
            rcvrName = "'" + rcvrName + "'";
            if (firstCondition) {
                sql += "receiver_name = " + rcvrName;
                firstCondition = false;
            } else {
                sql += " AND receiver_name = " + rcvrName;
            }
        }
        // Combining the destination attribute
        if (destination != null && !destination.equals("")) {
            destination = "'" + destination + "'";
            if (firstCondition) {
                sql += "destination = " + destination;
            } else {
                sql += " AND destination = " + destination;
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
     * A method to update an existing instance of Order in the database.
     * The parameter contains an Order Object with the updated attribute
     *
     * @param ord An Order Object with the updated attribute
     * @return True if the update is executed successfully, and False otherwise
     */
    public boolean updateOrder(Order ord) {
        Connection con = PostgresDAOFactory.createConnection();
        Statement stmt;
        String sql = "UPDATE orders SET ";
        boolean firstCondition = true;

        // Combining the query with the updated variable value
        // within the Order TO
        // Combining the senderName from Order TO
        if (ord.getSenderName() != null) {
            sql += "sender_name = '" + ord.getSenderName() + "'";
            firstCondition = false;
        }
        // Combining the receiverName from Order TO
        if (ord.getReceiverName() != null) {
            String rcvrName = "'" + ord.getReceiverName() + "'";
            if (firstCondition) {
                sql += "receiver_name = " + rcvrName;
                firstCondition = false;
            } else {
                sql += ", receiver_name = " + rcvrName;
            }
        }
        // Combining the destination from Order TO
        if (ord.getDestination() != null) {
            String destination = "'" + ord.getDestination() + "'";
            if (firstCondition) {
                sql += "destination = " + destination;
            } else {
                sql += ", destination = " + destination;
            }
        }
        // Closing the sql statement
        sql += " WHERE id = " + ord.getId() + ";";

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
