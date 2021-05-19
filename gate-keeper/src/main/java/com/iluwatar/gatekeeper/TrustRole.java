package com.iluwatar.gatekeeper;

import lombok.NoArgsConstructor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class TrustRole {
  /**
   * Handle a variety of legitimate requests.
   * @param request The request from the client.
   */
  public void handleRequest(Request request) {
    if (request.getAction().equals("login")) {
      LOGGER.info("Handle login request.");
      handleLogin((LoginRequest) request);
    }
  }

  /**
   * Handle a login requests.
   * @param request The request from the client.
   */
  public void handleLogin(LoginRequest request) {
    LOGGER.info("Link to the database and check account: "
            + request.getAccount() + " and password: " + request.getPassword() + ".");
  }

}
