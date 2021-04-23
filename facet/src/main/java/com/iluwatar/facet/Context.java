package com.iluwatar.facet;

/**
 * A Context abstracts the logic for make access control decisions on behalf of a Facet.
 */
public interface Context {
  public boolean validateInterface(Class interfaceClass);

  public void setUser(User user);
}
