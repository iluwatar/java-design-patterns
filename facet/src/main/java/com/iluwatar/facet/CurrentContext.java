package com.iluwatar.facet;

/**
 * current used context to abstract the logic for make access control decisions
 * on behalf of a Facet.
 */
public class CurrentContext implements Context {
  @Override
  public boolean validateInterface(Class interfaceClass) {
    if (interfaceClass.equals(SecurityMethods.class)) {
      return AccessController.checkPermission(user);
    }
    return true;
  }

  private User user;

  public void setUser(User user) {
    this.user = user;
  }
}
