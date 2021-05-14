package com.iluwatar.valet.key;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Resource {
  protected boolean checkToken(Token token, int target) {
    if (token.isValid() && token.getTarget() == target) {
      LOGGER.info("Token is valid");
      return true;
    } else {
      LOGGER.info("Token is invalid or No access rights");
      return false;
    }
  }
}
