package com.iluwatar.daofactory;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class PostgresDAOFactoryTest {
    @Test
    void testGetAccountDAO() {
        Method method = null;
        try {
            method = PostgresDAOFactory.class.getMethod("getAccountDAO");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        assertNotNull(method);
    }

    @Test
    void testGetOrderAO() {
        Method method = null;
        try {
            method = PostgresDAOFactory.class.getMethod("getOrderDAO");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        assertNotNull(method);
    }

    @Test
    void testCreateConnection() {
        Method method = null;
        try {
            method = PostgresDAOFactory.class.getMethod("createConnection");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        assertNotNull(method);
    }
}