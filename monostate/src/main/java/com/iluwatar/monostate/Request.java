package com.iluwatar.monostate;

/**
 * 
 * The Request class. A {@link Server} can handle an instance of a Request. 
 * 
 */

public class Request {
  public final String value;

  public Request(String value) {
    super();
    this.value = value;
  }
}
