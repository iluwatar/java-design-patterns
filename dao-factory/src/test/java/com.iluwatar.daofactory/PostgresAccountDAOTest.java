package com.iluwatar.daofactory;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class PostgresAccountDAOTest {
    @Test
    void testInsertAccount() {
        Method method = null;
        try {
            method = PostgresAccountDAO.class.getMethod("insertAccount", String.class, String.class, String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        assertNotNull(method);
    }

    @Test
    void testFindAccount() {
        Method method = null;
        try {
            method = PostgresAccountDAO.class.getMethod("findAccount", String.class, String.class, String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        assertNotNull(method);
    }

    @Test
    void testDeleteAccount() {
        Method method = null;
        try {
            method = PostgresAccountDAO.class.getMethod("deleteAccount", String.class, String.class, String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        assertNotNull(method);
    }

    @Test
    void testUpdateAccount() {
        Method method = null;
        try {
            method = PostgresAccountDAO.class.getMethod("updateAccount", Account.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        assertNotNull(method);
    }
}