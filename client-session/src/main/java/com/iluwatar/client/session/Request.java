package com.iluwatar.client.session;

/**
 * The Request class which contains the Session details and data.
 */
public class Request {

  private String data;

  private Session session;

  public Request(String data, Session session) {
    this.data = data;
    this.session = session;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public Session getSession() {
    return session;
  }

  public void setSession(Session session) {
    this.session = session;
  }
}
