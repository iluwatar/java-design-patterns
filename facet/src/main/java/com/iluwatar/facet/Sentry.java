package com.iluwatar.facet;

/**
 * The sentry interface to consider the interface and context being
 * used to make the request and select one of it comprising objects to handle it.
 */
public interface Sentry {
  Context context = null;

  public abstract boolean execute(User user, Class interfaceClass);
}
