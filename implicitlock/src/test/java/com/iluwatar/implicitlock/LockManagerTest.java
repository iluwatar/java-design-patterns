package com.iluwatar.implicitlock;


import com.iluwatar.LockManager;
import com.iluwatar.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LockManagerTest {

  private LockManager lockManager;
  private Resource resource1;

  @BeforeEach
  void setUp() {
    lockManager = new LockManager();
    resource1 = new Resource("Resource1");
  }

  @Test
  void testAcquireLockSuccessfully() {
    // Attempt to acquire the lock for resource1
    boolean result = lockManager.acquireLock(resource1);

    // Assert that the lock was successfully acquired
    assertTrue(result, "Lock should be successfully acquired for Resource1");
  }



  @Test
  void testReleaseLockWithoutHolding() {
    // Try to release the lock without acquiring it
    boolean releaseAttempt = lockManager.releaseLock(resource1);
    assertFalse(releaseAttempt, "Release attempt should fail as the lock was not acquired");
  }
}
