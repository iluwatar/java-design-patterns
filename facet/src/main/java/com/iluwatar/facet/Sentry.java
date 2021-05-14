package com.iluwatar.facet;

/**
 * The sentry interface to consider the interface and context being
 * used to make the request and select one of it comprising objects to handle it.
 */
public interface Sentry {
  public abstract boolean execute(User user, Class<? extends SecurityMethods> interfaceClass);
}
