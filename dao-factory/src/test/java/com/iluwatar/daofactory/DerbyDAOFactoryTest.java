package com.iluwatar.daofactory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DerbyDAOFactoryTest {

    @Test
    void getUserDAOTest() {
        DAOFactory derbyFactory = DAOFactory.getDAOFactory(DAOFactory.DERBY);
        UserDAO userDAO = derbyFactory.getUserDAO();
        assertTrue(userDAO instanceof DerbyUserDAO);
    }
}
