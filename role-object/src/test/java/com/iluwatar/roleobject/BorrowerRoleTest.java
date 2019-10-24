package com.iluwatar.roleobject;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class BorrowerRoleTest {

    @Test
    public void borrowTest() {
        BorrowerRole borrowerRole = new BorrowerRole();
        borrowerRole.setName("test");
        String res = "A borrower test wants to get some money.";

        Assert.assertEquals(borrowerRole.borrow(),res);
    }
}