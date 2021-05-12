package com.iluwatar.facet;

/**
 * implements a set of interfaces and it is exposed to user.
 */
public class Facet {
  private final Sentry sentry;
  private Class[] classes;
  private User user;

  /**
   * create a facet.
   *
   * @param sentry  set sentry corresponds to such facet.
   * @param classes set supported interfaces for this facet.
   * @return created facet.
   */
  public static Facet create(Sentry sentry, Class[] classes) {
    Facet facet = new Facet(sentry, classes);
    return facet;
  }

  private Facet(Sentry sentry, Class[] classes) {
    this.sentry = sentry;
    this.classes = classes;
  }

  public static Class[] query(Facet facet) {
    return facet.classes;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public static Facet narrow(Facet facet, Class[] interfaces) {
    facet.classes = interfaces.clone();
    return facet;
  }

  /**
   * invoke method.
   *
   * @param interfaceClass the interface for the class.
   * @return the result of this invoke.
   */
  public String invokeSecurityMethod(Class interfaceClass) {
    for (int j = 0; j < this.classes.length; j++) {
      if (interfaceClass.equals(classes[j])) {
        if (this.sentry.execute(this.user, interfaceClass)) {
          return SecurityMethodsImplementation.delegate(user);
        }
      }
    }
    return null;
  }
}
