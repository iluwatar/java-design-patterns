package com.iluwatar.chain;

import java.util.Objects;

/**
 * Request
 */
public class Request {

  /**
   * The type of this request, used by each item in the chain to see if they should or can handle
   * this particular request
   */
  private final RequestType requestType;

  /**
   * A description of the request
   */
  private final String requestDescription;

  /**
   * Indicates if the request is handled or not. A request can only switch state from unhandled to
   * handled, there's no way to 'unhandle' a request
   */
  private boolean handled = false;

  /**
   * Create a new request of the given type and accompanied description.
   *
   * @param requestType        The type of request
   * @param requestDescription The description of the request
   */
  public Request(final RequestType requestType, final String requestDescription) {
    this.requestType = Objects.requireNonNull(requestType);
    this.requestDescription = Objects.requireNonNull(requestDescription);
  }

  /**
   * Get a description of the request
   *
   * @return A human readable description of the request
   */
  public String getRequestDescription() {
    return requestDescription;
  }

  /**
   * Get the type of this request, used by each person in the chain of command to see if they should
   * or can handle this particular request
   *
   * @return The request type
   */
  public RequestType getRequestType() {
    return requestType;
  }

  /**
   * Mark the request as handled
   */
  public void markHandled() {
    this.handled = true;
  }

  /**
   * Indicates if this request is handled or not
   *
   * @return <tt>true</tt> when the request is handled, <tt>false</tt> if not
   */
  public boolean isHandled() {
    return this.handled;
  }

  @Override
  public String toString() {
    return getRequestDescription();
  }

}
