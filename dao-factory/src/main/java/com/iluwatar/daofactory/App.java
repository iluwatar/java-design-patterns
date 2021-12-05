package com.iluwatar.daofactory;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class App {
    /**
     * Program entry point.
     *
     * @param args command line args
     */
    public static void main(final String[] args) {
        // create the required DAO Factory
        final DAOFactory derbyFactory = DAOFactory.getDAOFactory(DAOFactory.DERBY);

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
        final User u = new User();
        u.setName("Sam Doe");
        u.setStreetAddress("333 4th Street");
        u.setCity("Seattle");

        return userDAO.insertUser(u);
    }

    private static User findUser(final int id, final UserDAO userDAO){
        final User user = userDAO.findUser(id);
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
            LOGGER.info(
                "Found " + userList.size() + " Users With " + criteriaCol + " = " + criteria + ":");
        }
        for (final User i: userList){
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("User " + i.getUserId());
            }
        }
    }

}
