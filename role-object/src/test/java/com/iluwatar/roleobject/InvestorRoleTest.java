package com.iluwatar.roleobject;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class InvestorRoleTest {

    @Test
    public void investTest() {
        InvestorRole investorRole = new InvestorRole();
        investorRole.setName("test");
        investorRole.setAmountToInvest(10);
        String res = "Investor test has invested 10 dollars";
        Assert.assertEquals(investorRole.invest(), res);
    }
}