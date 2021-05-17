package com.iluwatar.lockableobject;

/**
 * An exception regarding the locking process of a Lockable object.
 */
public class LockingException extends RuntimeException {

  private static final long serialVersionUID = 8556381044865867037L;

  public LockingException(String message) {
    super(message);
  }

}
