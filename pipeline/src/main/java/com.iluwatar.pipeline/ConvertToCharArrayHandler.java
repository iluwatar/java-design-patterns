package com.iluwatar.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

class ConvertToCharArrayHandler implements Handler<String, char[]> {

  private final Logger LOGGER = LoggerFactory.getLogger(ConvertToCharArrayHandler.class);

  @Override
  public char[] process(String input) {
    char[] characters = input.toCharArray();
    LOGGER.info(String.format("Current handler: %s, input is %s of type %s, output is %s, of type %s", ConvertToCharArrayHandler.class, input, String.class, Arrays.toString(characters), Character[].class));

    return characters;
  }
}
