package com.iluwatar.daofactory;

/**
 * An abstract class that determine and specify
 * the different type of DAO Factory
 */
public abstract class DAOFactory {
    // A DAO Implementation for each of the Factory
    public abstract AccountDAO getAccountDAO();
    public abstract OrderDAO getOrderDAO();

    /**
     * A method to get the DAO Factory for a certain type
     *
     * @param dsType The enum value for the Data Source Type
     * @return A specific DAO Factory Type
     */
    public static DAOFactory getDAOFactory(DataSourceType dsType) {
        switch (dsType) {
            case POSTGRES:
                return new PostgresDAOFactory();
            default:
                return null;
        }
    }
}
