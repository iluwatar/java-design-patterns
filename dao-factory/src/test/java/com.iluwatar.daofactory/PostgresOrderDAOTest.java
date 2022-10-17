package com.iluwatar.daofactory;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class PostgresOrderDAOTest {
    @Test
    void testInsertOrder() {
        Method method = null;
        try {
            method = PostgresOrderDAO.class.getMethod("insertOrder", String.class, String.class, String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        assertNotNull(method);
    }

    @Test
    void testFindOrder() {
        Method method = null;
        try {
            method = PostgresOrderDAO.class.getMethod("findOrder", String.class, String.class, String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        assertNotNull(method);
    }

    @Test
    void testDeleteOrder() {
        Method method = null;
        try {
            method = PostgresOrderDAO.class.getMethod("deleteOrder", String.class, String.class, String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        assertNotNull(method);
    }

    @Test
    void testUpdateOrder() {
        Method method = null;
        try {
            method = PostgresOrderDAO.class.getMethod("updateOrder", Order.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        assertNotNull(method);
    }
}