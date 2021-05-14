package com.iluwatar.facet;

/**
 * implementation of the secure method.
 */
public class SecurityMethodsImplementation implements SecurityMethods {
  private SecurityMethodsImplementation() {
    throw new IllegalStateException("utility class");
  }

  /**
   * the method to be secured.
   *
   * @param user according to different user to return different result.
   * @return the execution result of this method.
   */
  public static String delegate(User user) {
    if (user instanceof Client) {
      return "Client create something.";
    } else if (user instanceof Administrator) {
      return "Administrator create something.";
    }
    return "";
  }
}
