package com.iluwatar.gatekeeper;

import lombok.NoArgsConstructor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class GateKeeper {
  /**
   * verify and forward the request.
   * @param request The request from the client.
   */
  public void validateAndForwardRequest(Request request) {
    beginRequest(request);
    endRequest(request);
  }

  /**
   * begin to verify the request, if it satisfy the requirements,
   * forward it to the TrustRole.
   * @param request The request from the client.
   */
  public void beginRequest(Request request) {
    var trustRole = new TrustRole();
    LOGGER.info("----------------------------");
    LOGGER.info("Start the request: " + request.getAction());
    if (checkRequest(request)) {
      LOGGER.info("Accept the request!");
      trustRole.handleRequest(request);
    } else {
      LOGGER.info("Refuse the request!");
    }

  }

  /**
   * End verify the request.
   * @param request The request from the client.
   */
  public void endRequest(Request request) {
    LOGGER.info("End the request: " + request.getAction());
  }

  /**
   * check the validity of the request.
   * @param request The request from the client.
   * @return if the request meet the requirements.
   */
  public boolean checkRequest(Request request) {
    return request.getAction().equals("login");
  }
}
