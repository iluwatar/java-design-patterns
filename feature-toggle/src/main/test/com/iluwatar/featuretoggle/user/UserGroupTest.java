package com.iluwatar.featuretoggle.user;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserGroupTest {

    @Test
    public void testAddUserToFreeGroup() throws Exception {
        User user  = new User("Free User");
        UserGroup.addUserToFreeGroup(user);
        assertFalse(UserGroup.isPaid(user));
    }

    @Test
    public void testAddUserToPaidGroup() throws Exception {
        User user  = new User("Paid User");
        UserGroup.addUserToPaidGroup(user);
        assertTrue(UserGroup.isPaid(user));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserToPaidWhenOnFree() throws Exception {
        User user  = new User("Paid User");
        UserGroup.addUserToFreeGroup(user);
        UserGroup.addUserToPaidGroup(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserToFreeWhenOnPaid() throws Exception {
        User user  = new User("Free User");
        UserGroup.addUserToPaidGroup(user);
        UserGroup.addUserToFreeGroup(user);
    }
}