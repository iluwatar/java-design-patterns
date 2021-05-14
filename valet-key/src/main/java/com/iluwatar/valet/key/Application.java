package com.iluwatar.valet.key;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {
  protected Token generateToken(boolean request) {
    if (request) {
      LOGGER.info("the request is invalid, generate token successfully");
    } else {
      LOGGER.info("the request is valid, generate token failed");
    }

    int target = 1;
    return new Token(request, target);
  }
}
