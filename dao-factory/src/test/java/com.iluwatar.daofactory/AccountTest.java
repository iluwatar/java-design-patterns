package com.iluwatar.daofactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {
    private Account account;
    private final String fName = "Paul";
    private final String sName = "Jack";
    private final String loc = "Canberra";

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setLocation(fName);
        account.setLastName(sName);
        account.setLocation(loc);
    }

    @Test
    void testGetAndSetFirstName() {
        String newFirstName = "Richard";
        account.setFirstName(newFirstName);
        assertEquals(newFirstName, account.getFirstName());
    }

    @Test
    void testGetAndSetLastName() {
        String newLastName = "Son";
        account.setLastName(newLastName);
        assertEquals(newLastName, account.getLastName());
    }

    @Test
    void testGetAndSetLocation() {
        String newLocation = "Sydney";
        account.setLocation(newLocation);
        assertEquals(newLocation, account.getLocation());
    }

    @Test
    void testEqualsWithSameObjects() {
        assertEquals(account, account);
        assertEquals(account.hashCode(), account.hashCode());
    }
}