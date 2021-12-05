package com.iluwatar.daofactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MongoDAOFactoryTest {
    @Test
    void getUserDAOTest() {
        DAOFactory mongoFactory = DAOFactory.getDAOFactory(DAOFactory.MONGO);
        UserDAO userDAO = mongoFactory.getUserDAO();
        assertTrue(userDAO instanceof MongoUserDAO);
    }
}