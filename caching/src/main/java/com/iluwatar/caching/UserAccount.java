package com.iluwatar.caching;

/**
 *
 * Entity class (stored in cache and DB) used in the application.
 *
 */
public class UserAccount {
  private String userId;
  private String userName;
  private String additionalInfo;

  /**
   * Constructor
   */
  public UserAccount(String userId, String userName, String additionalInfo) {
    this.userId = userId;
    this.userName = userName;
    this.additionalInfo = additionalInfo;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getAdditionalInfo() {
    return additionalInfo;
  }

  public void setAdditionalInfo(String additionalInfo) {
    this.additionalInfo = additionalInfo;
  }

  @Override
  public String toString() {
    return userId + ", " + userName + ", " + additionalInfo;
  }
}
