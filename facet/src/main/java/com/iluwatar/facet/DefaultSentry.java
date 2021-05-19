package com.iluwatar.facet;

/**
 * The default sentry is to consider the interface and context being
 * used to make the request and selects one of its comprising objects to handle it.
 */
public class DefaultSentry implements Sentry {
  private final Context context;

  public DefaultSentry(Context context) {
    this.context = context;
  }


  @Override
  public boolean execute(User user, Class<? extends SecurityMethods> interfaceClass) {
    this.context.setUser(user);
    return this.context.validateInterface(interfaceClass);
  }
}
