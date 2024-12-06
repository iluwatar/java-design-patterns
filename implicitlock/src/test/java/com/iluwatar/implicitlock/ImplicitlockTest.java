package com.iluwatar.implicitlock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.iluwatar.Framework;
import com.iluwatar.LockManager;
import com.iluwatar.Resource;


import static org.junit.jupiter.api.Assertions.*;

class ImplicitLockTest {

  private LockManager lockManager;
  private Framework framework;
  private Resource resource1;
  private Resource resource2;

  @BeforeEach
  void setUp() {
    lockManager = new LockManager();
    framework = new Framework(lockManager);
    resource1 = new Resource("Resource1");
    resource2 = new Resource("Resource2");
  }

  @Test
  void verifyLockAcquisition() {
    // Try to acquire lock on resource1
    assertTrue(framework.tryLockResource(resource1), "Lock should be acquired for resource1");



    // Try to acquire lock on resource2
    assertTrue(framework.tryLockResource(resource2), "Lock should be acquired for resource2");
  }

  @Test
  void verifyLockRelease() {
    // Acquire lock on resource1
    assertTrue(framework.tryLockResource(resource1), "Lock should be acquired for resource1");



    // Try to acquire lock on resource1 again after release
    assertTrue(framework.tryLockResource(resource1), "Lock should be acquired for resource1 again after release");
  }

  @Test
  void verifyResourceLockedByDifferentThreads() throws InterruptedException {
    // Create a thread to lock resource1
    Thread thread1 = new Thread(() -> {
      assertTrue(framework.tryLockResource(resource1), "Lock should be acquired for resource1 by thread1");
    });

    // Create a second thread to try locking the same resource
    Thread thread2 = new Thread(() -> {
      assertFalse(framework.tryLockResource(resource1), "Lock should not be acquired for resource1 by thread2");
    });

    thread1.start();
    thread1.join();

    thread2.start();
    thread2.join();
  }

}