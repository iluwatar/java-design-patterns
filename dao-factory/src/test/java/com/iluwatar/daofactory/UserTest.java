package com.iluwatar.daofactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void getAndSetId() {
        final var newId = 2;
        user.setUserId(newId);
        assertEquals(newId, user.getUserId());
    }

    @Test
    void getAndSetName() {
        final var newName = "Bill Joe";
        user.setName(newName);
        assertEquals(newName, user.getName());
    }

    @Test
    void getAndSetAddress() {
        final var address = "12345 5th Street";
        user.setStreetAddress(address);
        assertEquals(address, user.getStreetAddress());
    }

    @Test
    void getAndSetCity() {
        final var city = "Seattle";
        user.setCity(city);
        assertEquals(city, user.getCity());
    }

    @Test
    void notEqualWithDifferentId() {
        final var UserId2 = 3;
        final var user2 = new User();
        user2.setUserId(UserId2);
        assertNotEquals(user, user2);
        assertNotEquals(user.hashCode(), user2.hashCode());
    }

    @Test
    void equalsWithSameObjects() {
        assertEquals(user, user);
        assertEquals(user.hashCode(), user.hashCode());
    }

}
