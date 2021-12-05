package com.iluwatar.daofactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class DAOFactoryTest {

    @Test
    void getDAOFactoryTest() {
        var derbyFactory = DAOFactory.getDAOFactory(DAOFactory.DERBY);
        assertTrue(derbyFactory instanceof DAOFactory);
        assertTrue(derbyFactory instanceof DerbyDAOFactory);
    }
}
