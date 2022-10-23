package com.iluwatar.client.session;

/**
 * The Session class. Each client get assigned a Session which is then used for further communications.
 */
public class Session {

  /**
   * Session id.
   */
  private String id;

  /**
   * Client name.
   */
  private String clientName;

  public Session(String id, String clientName) {
    this.id = id;
    this.clientName = clientName;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getClientName() {
    return clientName;
  }

  public void setClientName(String clientName) {
    this.clientName = clientName;
  }
}
