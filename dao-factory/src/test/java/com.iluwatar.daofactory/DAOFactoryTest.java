package com.iluwatar.daofactory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DAOFactoryTest {
    @Test
    void testGetPostgresDAO() {
        assertInstanceOf(PostgresDAOFactory.class, DAOFactory.getDAOFactory(1));
    }

    @Test
    void testNullGetDaoFactory() {
        assertNull(DAOFactory.getDAOFactory(0));
        assertNull(DAOFactory.getDAOFactory(5));
        assertNull(DAOFactory.getDAOFactory(98512));
        assertNull(DAOFactory.getDAOFactory(-1));
    }
}