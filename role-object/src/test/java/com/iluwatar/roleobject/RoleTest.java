package com.iluwatar.roleobject;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class RoleTest {

    @Test
    public void instanceTest() {
        Optional<CustomerRole> instance = Role.Borrower.instance();
        Assert.assertTrue(instance.isPresent());
        Assert.assertEquals(instance.get().getClass(),BorrowerRole.class);
    }
}