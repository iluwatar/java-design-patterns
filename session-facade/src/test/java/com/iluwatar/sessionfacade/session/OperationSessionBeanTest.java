package com.iluwatar.sessionfacade.session;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationSessionBeanTest {

    @Test
    void add() {
        OperationSessionBean testOSB = new OperationSessionBean();
        assertTrue(testOSB.add(20, 30) == 50);
        assertTrue(testOSB.add(120, 230) == 350);
        assertTrue(testOSB.add(-20, -30) == -50);
    }

    @Test
    void subtract() {
        OperationSessionBean testOSB = new OperationSessionBean();
        assertTrue(testOSB.subtract(14.6, 13.1) == 1.5);
        assertTrue(testOSB.subtract(-14.6, 13.1) == -27.7);
        assertTrue(testOSB.subtract(-14.6, -13.1) == -1.5);
    }

    @Test
    void multiply() {
        OperationSessionBean testOSB = new OperationSessionBean();
        assertTrue(testOSB.multiply(20, 10) == 200);
        assertTrue(testOSB.multiply(20, -10) == -200);
        assertTrue(testOSB.multiply(-20, -10) == 200);
        assertTrue(testOSB.multiply(20, 0) == 0);
    }

    @Test
    void divide() {
        OperationSessionBean testOSB = new OperationSessionBean();
        assertTrue(testOSB.divide(10, 2) == 5);
        assertTrue(testOSB.divide(10, -2) == -5);
        assertTrue(testOSB.divide(-10, -2) == 5);
    }
}