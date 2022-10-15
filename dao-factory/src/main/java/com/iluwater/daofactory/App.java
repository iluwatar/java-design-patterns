package com.iluwater.daofactory;

/**
 * An implementation example for a client code
 */
public class App {
    public static void main(String[] args) {
        // Create a PostgreSQL DAO Factory
        DAOFactory postgres = DAOFactory.getDAOFactory(DAOFactory.POSTGRES);

        // Create an Account DAO
        AccountDAO psqlAccount = postgres.getAccountDAO();

        // Inserting a new account to the database
        psqlAccount.insertAccount("Richard", "Reynaldo", "Canberra");
        psqlAccount.insertAccount("Josh", "Sebastian", "Paris");
        psqlAccount.insertAccount("Paul", "James", "Boston");
        psqlAccount.insertAccount("Hiro", "Daiki", "Tokyo");

        // Finding an account with first_name Richard and
        // last_name Reynaldo
        Account tempAcc = psqlAccount.findAccount("Richard", "Reynaldo", null);

        // Resetting the location attribute
        tempAcc.setLocation("Sydney");

        // Update modified account to the database
        psqlAccount.updateAccount(tempAcc);

        // Delete an account from the database
        // (delete all record where its location is Boston)
        psqlAccount.deleteAccount(null, null, "Boston");

        // Create an Order DAO
        OrderDAO psqlOrder = postgres.getOrderDAO();

        // Inserting a new order to the database
        psqlOrder.insertOrder("Richard", "Josh", "Paris");
        psqlOrder.insertOrder("Hiro", "Richard", "Sydney");
        psqlOrder.insertOrder("Josh", "Richard", "Sydney");

        // Finding an order whose sender is Richard
        Order tempOrder = psqlOrder.findOrder("Richard", null, null);

        // Resetting the destination attribute
        tempOrder.setDestination("Milan");

        // Update modified order to the database
        psqlOrder.updateOrder(tempOrder);

        // Delete an account from the database
        // (delete all record whose sender is Hiro)
        psqlOrder.deleteOrder("Hiro", null, null);
    }
}
