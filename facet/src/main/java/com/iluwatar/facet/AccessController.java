package com.iluwatar.facet;

/**
 * to control the permission.
 */
public class AccessController {
  private AccessController() {
    throw new IllegalStateException("utility class");
  }

  /**
   * check the permission to use the method.
   *
   * @param user the identity of the user.
   * @return boolean to judge whether can use the method.
   */
  public static boolean checkPermission(User user) {
    if (user == null) {
      return false;
    } else if (user instanceof Administrator) {
      return true;
    } else if (user instanceof Client) {
      return false;
    }
    return false;
  }
}
