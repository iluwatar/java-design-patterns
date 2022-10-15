package com.iluwater.daofactory;

/**
 * An abstract class that determine and specify
 * the different type of DAO Factory
 */
public abstract class DAOFactory {
    // Variable for the different Factory type
    public static final int POSTGRES = 1;

    // A DAO Implementation for each of the Factory
    public abstract AccountDAO getAccountDAO();
    public abstract OrderDAO getOrderDAO();

    /**
     * A method to get the DAO Factory for a certain type
     *
     * @param dao The int representation for the DAO type
     * @return A specific DAO Factory Type
     */
    public static DAOFactory getDAOFactory(int dao) {
        switch (dao) {
            case 1:
                return new PostgresDAOFactory();
            default:
                return null;
        }
    }
}
