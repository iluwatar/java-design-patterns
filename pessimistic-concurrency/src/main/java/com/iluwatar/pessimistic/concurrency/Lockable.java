package com.iluwatar.pessimistic.concurrency;

public interface Lockable {
  boolean isLocked();

  void lock(String username) throws LockingException;

  void unlock(String username) throws LockingException;
}
