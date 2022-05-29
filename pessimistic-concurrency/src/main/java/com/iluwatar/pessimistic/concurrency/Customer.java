package com.iluwatar.pessimistic.concurrency;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "customers")
public class Customer implements Lockable {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  private String name;
  private String lockingUser = null;

  public Customer(String name) {
    this.name = name;
  }

  public Customer() {}

  @Override
  public boolean isLocked() {
    return (lockingUser != null);
  }

  @Override
  public void lock(String username) throws LockingException {
    if (username == null) {
      throw new LockingException("No User Provided.");
    }
    synchronized (this) {
      if (lockingUser == null) {
        lockingUser = username;
      } else if ((lockingUser != null) && (!lockingUser.equals(username))) {
        throw new LockingException("Resource already locked.");
      }
    }
  }

  @Override
  public void unlock(String username) throws LockingException {
    if ((lockingUser != null) && (lockingUser.equals(username))) {
      lockingUser = null;
    } else if (lockingUser != null) {
      throw new LockingException("Resource already locked.");
    }
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getLockingUser() {
    return lockingUser;
  }

  public void setLockingUser(String lockingUser) {
    this.lockingUser = lockingUser;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
