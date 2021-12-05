package com.iluwatar.daofactory;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

/**
 * The Dao Factory Pattern provides an abstract DAO
 * factory object (Abstract Factory) that can construct
 * various types of concrete DAO factories, each factory
 * supporting a different type of persistent storage
 * implementation.
 */
@Slf4j
public final class App {

  private App() {
  }

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(final String[] args) {
    // create the required DAO Factory
    final AbstractDAOFactory derbyFactory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.DERBY);

    // Create a DAO for Derby
    final UserDAO derbyUserDAO = derbyFactory.getUserDAO();
    DerbyDAOFactory.createConnection();

    // create, update, find a customer, or search by criteria
    final int userId = createUser(derbyUserDAO);
    final User user = findUser(userId, derbyUserDAO);
    updateUser(user, derbyUserDAO);
    deleteUser(user, derbyUserDAO);
    final String criteriaCol = "City";
    final String criteria = "Seattle";
    findUserWithCriteria(derbyUserDAO, criteriaCol, criteria);

  }

  private static int createUser(final UserDAO userDAO){
    final User user = new User();
    user.setName("Sam Doe");
    user.setStreetAddress("333 4th Street");
    user.setCity("Seattle");

    return userDAO.insertUser(user);
  }

  private static User findUser(final int userId, final UserDAO userDAO){
    final User user = userDAO.findUser(userId);
    if (LOGGER.isInfoEnabled()){
      LOGGER.info("User Created: " + user.getUserId());
    }
    return user;
  }

  private static User updateUser(final User user, final UserDAO userDAO){
    user.setStreetAddress("12345 8th Street");
    user.setName("Sam Smith");
    user.setCity("York");
    userDAO.updateUser(user);
    if (LOGGER.isInfoEnabled()){
      LOGGER.info("User Updated: " + user.getUserId());
    }

    return user;
  }

  private static void deleteUser(final User user, final UserDAO userDAO){
    userDAO.deleteUser(user);
    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("User Deleted: " + user.getUserId());
    }
  }

  private static void findUserWithCriteria(final UserDAO userDAO, final String criteriaCol, final String criteria){
    final Collection<User> userList = userDAO.selectUsersTO(criteriaCol, criteria);
    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("Found " + userList.size() + " Users With " + criteriaCol + " = " + criteria + ":");
    }
    for (final User i: userList){
      if (LOGGER.isInfoEnabled()) {
        LOGGER.info("User " + i.getUserId());
      }
    }
  }

}
