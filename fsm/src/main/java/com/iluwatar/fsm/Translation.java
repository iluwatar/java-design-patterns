package com.iluwatar.fsm;

/**
 * Transition methods.
 */
public interface Translation {
  public void pass();

  public void coin();

  public void failed();

  public void fixed();
}
