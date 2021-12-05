package com.iluwatar.daofactory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DerbyDAOFactoryTest {

    @Test
    void getUserDAOTest() {
        AbstractDAOFactory derbyFactory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.DERBY);
        UserDAO userDAO = derbyFactory.getUserDAO();
        assertTrue(userDAO instanceof DerbyUserDAO);
    }
}
