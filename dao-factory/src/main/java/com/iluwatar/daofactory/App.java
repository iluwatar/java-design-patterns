package com.iluwatar.daofactory;

import java.util.Collection;
import lombok.extern.slf4j.Slf4j;


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
    final AbstractDaoFactory derbyFactory =
        AbstractDaoFactory.getDaoFactory(AbstractDaoFactory.DERBY);

    // Create a DAO for Derby
    final UserDao derbyUserDao = derbyFactory.getUserDao();
    DerbyDaoFactory.createConnection();

    // create, update, find a customer, or search by criteria
    final int userId = createUser(derbyUserDao);
    final User user = findUser(userId, derbyUserDao);
    updateUser(user, derbyUserDao);
    deleteUser(user, derbyUserDao);
    findUserWithCriteria(derbyUserDao, "City", "Seattle");

  }

  private static int createUser(final UserDao userDao) {
    final User user = new User();
    user.setName("Sam Doe");
    user.setStreetAddress("333 4th Street");
    user.setCity("Seattle");

    return userDao.insertUser(user);
  }

  private static User findUser(final int userId, final UserDao userDao) {
    final User user = userDao.findUser(userId);
    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("User Created: " + user.getUserId());
    }
    return user;
  }

  private static User updateUser(final User user, final UserDao userDao) {
    user.setStreetAddress("12345 8th Street");
    user.setName("Sam Smith");
    user.setCity("York");
    userDao.updateUser(user);
    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("User Updated: " + user.getUserId());
    }

    return user;
  }

  private static void deleteUser(final User user, final UserDao userDao) {
    userDao.deleteUser(user);
    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("User Deleted: " + user.getUserId());
    }
  }

  private static void findUserWithCriteria(final UserDao userDao,
                                           final String criteriaCol,
                                           final String criteria) {
    final Collection<User> userList = userDao.selectUsersTO(criteriaCol, criteria);
    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("Found " + userList.size()
          + " Users With " + criteriaCol + " = " + criteria + ":");
    }
    for (final User i: userList) {
      if (LOGGER.isInfoEnabled()) {
        LOGGER.info("User " + i.getUserId());
      }
    }
  }

}
