package com.iluwatar.daofactory;

/**
 * This factory produces DAOs such as UserDAO.
 * This strategy uses the Factory Method implementation
 * in the factories produced by the Abstract Factory.
 */
public abstract class AbstractDAOFactory {

  /**
   * Integer switcher for Derby
   */
  public static final int DERBY = 1;

  /**
   * Integer switcher for Mongo
   */
  public static final int MONGO = 2;

  /**
   * Instantiates a DAOFactory.
   */
  public AbstractDAOFactory() {
    // This constructor is intentionally empty. Nothing special is needed here.
  }

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
  public static AbstractDAOFactory getDAOFactory(final int whichFactory) {
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
