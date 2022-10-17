package com.iluwatar.daofactory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DAOFactoryTest {
    @Test
    void testGetPostgresDAO() {
        assertInstanceOf(PostgresDAOFactory.class, DAOFactory.getDAOFactory(DataSourceType.POSTGRES));
    }
}