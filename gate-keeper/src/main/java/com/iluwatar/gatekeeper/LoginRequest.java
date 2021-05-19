package com.iluwatar.gatekeeper;

import lombok.Getter;

@Getter

public class LoginRequest extends Request {
  private final String account;
  private final String password;

  /**
   * Construction method.
   * @param action the action of the request, such as :"login"
   * @param account the account of the login request.
   * @param password the password of the login request.
   */
  public LoginRequest(String action, String account, String password) {
    super(action);
    this.account = account;
    this.password = password;
  }
}
