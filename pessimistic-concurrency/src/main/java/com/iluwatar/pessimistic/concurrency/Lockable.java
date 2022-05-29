package com.iluwatar.pessimistic.concurrency;

public interface Lockable {
  public boolean isLocked();

  public void lock(String username) throws LockingException;

  public void unlock(String username) throws LockingException;
}
