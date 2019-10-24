package com.iluwatar.roleobject;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class CustomerCoreTest {

    @Test
    public void addRole() {
        CustomerCore core = new CustomerCore();
        boolean add = core.addRole(Role.Borrower);
        assertTrue(add);

    }

    @Test
    public void hasRole() {
        CustomerCore core = new CustomerCore();
        core.addRole(Role.Borrower);

        boolean has = core.hasRole(Role.Borrower);
        assertTrue(has);

        boolean notHas = core.hasRole(Role.Investor);
        assertFalse(notHas);
    }

    @Test
    public void remRole() {
        CustomerCore core = new CustomerCore();
        core.addRole(Role.Borrower);

        Optional<BorrowerRole> bRole = core.getRole(Role.Borrower, BorrowerRole.class);
        assertTrue(bRole.isPresent());

        boolean res = core.remRole(Role.Borrower);
        assertTrue(res);

        Optional<BorrowerRole> empt = core.getRole(Role.Borrower, BorrowerRole.class);
        assertFalse(empt.isPresent());

    }

    @Test
    public void getRole() {
        CustomerCore core = new CustomerCore();
        core.addRole(Role.Borrower);

        Optional<BorrowerRole> bRole = core.getRole(Role.Borrower, BorrowerRole.class);
        assertTrue(bRole.isPresent());

        Optional<InvestorRole> nonRole = core.getRole(Role.Borrower, InvestorRole.class);
        assertFalse(nonRole.isPresent());

        Optional<InvestorRole> invRole = core.getRole(Role.Investor, InvestorRole.class);
        assertFalse(invRole.isPresent());


    }


    @Test
    public void toStringTest() {
        CustomerCore core = new CustomerCore();
        core.addRole(Role.Borrower);
        assertEquals(core.toString(), "Customer{roles=[Borrower]}");

        core = new CustomerCore();
        core.addRole(Role.Investor);
        assertEquals(core.toString(), "Customer{roles=[Investor]}");

        core = new CustomerCore();
        assertEquals(core.toString(), "Customer{roles=[]}");


    }

}