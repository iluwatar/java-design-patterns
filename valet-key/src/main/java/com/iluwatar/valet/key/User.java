package com.iluwatar.valet.key;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class User {
  private Token token;
  private final Application application;
  private final Resource resource;

  /**
   * request a source in target database.
   *
   * @param validRequest is the request valid.
   * @param target the target position of resource.
   * @return is it access successfully.
   */
  public boolean requestResource(boolean validRequest, int target) {
    LOGGER.info("request a resource");
    if (token == null || !token.isValid()) {
      token = application.generateToken(validRequest);
    }

    return accessResource(target);
  }

  private boolean accessResource(int target) {
    if (resource.checkToken(token, target)) {
      LOGGER.info("Access Resource successfully");
      return true;
    } else {
      LOGGER.info("Access Resource failed");
      return false;
    }
  }
}
