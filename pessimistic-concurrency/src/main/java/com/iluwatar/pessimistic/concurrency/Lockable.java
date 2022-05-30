package com.iluwatar.pessimistic.concurrency;

public interface Lockable {
  void lock(String username) throws LockingException;

  void unlock(String username) throws LockingException;
}
