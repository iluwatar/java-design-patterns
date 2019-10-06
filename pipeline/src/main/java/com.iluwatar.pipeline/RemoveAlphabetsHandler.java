package com.iluwatar.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class RemoveAlphabetsHandler implements Handler<String, String> {

  private final Logger LOGGER = LoggerFactory.getLogger(RemoveAlphabetsHandler.class);

  @Override
  public String process(String input) {
    StringBuilder inputWithoutAlphabets = new StringBuilder();

    for (int index = 0; index < input.length(); index++) {
      char currentCharacter = input.charAt(index);
      if (Character.isAlphabetic(currentCharacter)) {
        continue;
      }

      inputWithoutAlphabets.append(currentCharacter);
    }

    String inputWithoutAlphabetsStr = inputWithoutAlphabets.toString();
    LOGGER.info(String.format("Current handler: %s, input is %s of type %s, output is %s, of type %s", RemoveAlphabetsHandler.class, input, String.class, inputWithoutAlphabetsStr, String.class));

    return inputWithoutAlphabetsStr;
  }
}