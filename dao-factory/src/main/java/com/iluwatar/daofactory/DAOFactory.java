package com.iluwatar.daofactory;

public abstract class DAOFactory {
    /**
     * Integer switcher for Derby
     */
    public static final int DERBY = 1;

    /**
     * Integer switcher for Mongo
     */
    public static final int MONGO = 2;

    /**
     * Concrete classes need to implement this method
     */
    public abstract UserDAO getUserDAO();

    /**
     * There will be a method for each DAO that can be
     * created. The concrete factories will have to
     * implement these methods.
     *
     * @return a DAOFactory
     */
    public static DAOFactory getDAOFactory(
            final int whichFactory) {
        switch (whichFactory) {
            case DERBY    :
                return new DerbyDAOFactory();
            case MONGO    :
                return new MongoDAOFactory();
            default        :
                return null;
        }
    }
}
