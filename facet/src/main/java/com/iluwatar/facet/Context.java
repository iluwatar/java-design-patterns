package com.iluwatar.facet;

/**
 * A Context abstracts the logic for making access control decisions on behalf of a Facet.
 */
public interface Context {
  public boolean validateInterface(Class<? extends SecurityMethods> interfaceClass);

  public void setUser(User user);
}
