package com.iluwatar.caching;

/**
 *
 * Entity class (stored in cache and DB) used in the application.
 *
 */
public class UserAccount {
  private String userID;
  private String userName;
  private String additionalInfo;

  public UserAccount(String userID, String userName, String additionalInfo) {
    this.userID = userID;
    this.userName = userName;
    this.additionalInfo = additionalInfo;
  }

  public String getUserID() {
    return userID;
  }

  public void setUserID(String userID) {
    this.userID = userID;
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
    return userID + ", " + userName + ", " + additionalInfo;
  }
}
