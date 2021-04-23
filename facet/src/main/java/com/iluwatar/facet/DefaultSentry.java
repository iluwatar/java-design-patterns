package com.iluwatar.facet;

/**
 * The default sentry is to consider the interface and context being
 * used to make the request and selects one of it comprising objects to handle it.
 */
public class DefaultSentry implements Sentry {
  private Context context;

  public DefaultSentry(Context context) {
    this.context = context;
  }


  @Override
  public boolean execute(User user, Class interfaceClass) {
    this.context.setUser(user);
    if (this.context.validateInterface(interfaceClass)) {
      return true;
    } else {
      return false;
    }
  }
}
