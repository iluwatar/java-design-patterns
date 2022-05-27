package com.iluwatar.pessimistic.concurrency;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PessimisticConcurrencyTest {
    @BeforeClass
    public static void init() {
        Customer obj1 = new Customer();
        Customer obj2 = new Customer();
        Customer obj3 = new Customer();
        LockManager lockManager = LockManager.getLockManager("CUSTOMER");
    }
    @Test
    public void testRequestLockAtTheSameTime() {

    }
}
