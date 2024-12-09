package com.iluwatar.implicitlock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.iluwatar.Framework;
import com.iluwatar.LockManager;
import com.iluwatar.Resource;
import com.iluwatar.BusinessTransaction;

import static org.junit.jupiter.api.Assertions.*;

class BusinessTransactionTest {

  private LockManager lockManager;
  private Framework framework;
  private Resource resource1;
  private BusinessTransaction transaction;

  @BeforeEach
  void setUp() {
    lockManager = new LockManager();
    framework = new Framework(lockManager);
    transaction = new BusinessTransaction(framework);
    resource1 = new Resource("Resource1");
  }

  @Test
  void verifyProcessCustomerSuccess() {
    // Process a customer transaction and assert it returns true (indicating success)
    boolean result = transaction.processCustomer(resource1, "456", "Customer data for 456");
    assertTrue(result, "The transaction should be processed successfully and return true");
  }
  @Test
  void verifyProcessCustomerWithInterruption() {
    // Simulate an interrupted transaction and ensure it returns false
    Thread transactionThread = new Thread(() -> {
      boolean result = transaction.processCustomer(resource1, "456", "Customer data for 456");
      assertFalse(result, "The transaction should return false if interrupted");
    });

    // Start and interrupt the transaction thread
    transactionThread.start();
    transactionThread.interrupt();

    try {
      transactionThread.join();  // Wait for thread to finish
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
