package com.iluwatar.facet;

/**
 * implements a set of interfaces and it is exposed to user.
 */
public class Facet {
  private final Sentry sentry;
  private Class<? extends SecurityMethods>[] classes;
  private User user;

  /**
   * create a facet.
   *
   * @param sentry  set sentry corresponds to such facet.
   * @param classes set supported interfaces for this facet.
   * @return created facet.
   */
  public static Facet create(Sentry sentry, Class<? extends SecurityMethods>[] classes) {
    return new Facet(sentry, classes);
  }

  private Facet(Sentry sentry, Class<? extends SecurityMethods>[] classes) {
    this.sentry = sentry;
    this.classes = classes;
  }

  public static Class<? extends SecurityMethods>[] query(Facet facet) {
    return facet.classes;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public static Facet narrow(Facet facet, Class<? extends SecurityMethods>[] interfaces) {
    facet.classes = interfaces.clone();
    return facet;
  }

  /**
   * invoke method.
   *
   * @param interfaceClass the interface for the class.
   * @return the result of this invoke.
   */
  public String invokeSecurityMethod(Class<? extends SecurityMethods> interfaceClass) {
    for (var j = 0; j < this.classes.length; j++) {
      if (interfaceClass.equals(classes[j]) && this.sentry.execute(this.user, interfaceClass)) {
        return SecurityMethodsImplementation.delegate(user);
      }
    }
    return null;
  }
}
