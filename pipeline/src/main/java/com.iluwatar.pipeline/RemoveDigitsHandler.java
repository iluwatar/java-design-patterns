package com.iluwatar.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class RemoveDigitsHandler implements Handler<String, String> {

  private final Logger LOGGER = LoggerFactory.getLogger(RemoveDigitsHandler.class);

  @Override
  public String process(String input) {
    StringBuilder inputWithoutDigits = new StringBuilder();

    for (int index = 0; index < input.length(); index++) {
      char currentCharacter = input.charAt(index);
      if (Character.isDigit(currentCharacter)) {
        continue;
      }

      inputWithoutDigits.append(currentCharacter);
    }

    String inputWithoutDigitsStr = inputWithoutDigits.toString();
    LOGGER.info(String.format("Current handler: %s, input is %s of type %s, output is %s, of type %s", RemoveDigitsHandler.class, input, String.class, inputWithoutDigitsStr, String.class));

    return inputWithoutDigitsStr;
  }
}