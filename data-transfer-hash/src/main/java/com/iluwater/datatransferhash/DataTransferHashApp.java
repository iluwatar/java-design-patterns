package com.iluwater.datatransferhash;

import lombok.extern.slf4j.Slf4j;

/**
 * The Data Transfer Hash Pattern aims to reduce coupling between objects communicating
 * between DTOs with the help of a data transfer hash object, which enables DTOs to send
 * data and receive data using well-known keys.
 *
 * <p>In this implementation, we use a simple hash map to act as the data transfer hash object.
 * For more robust implementation, a container object could be used to hold the hash,
 * as well as identifying information, type-safe data retrieval methods, and well-known keys.
 *
 * <p>In the following example, {@link DataTransferHashApp} simulates a scenario
 * where the presentation tier would retrieve one's phone number according to
 * his or her name from business object.
 */

@Slf4j
public class DataTransferHashApp {

  /**
  * This method simulates a scenario where the presentation tier would
  * retrieve one's phone number according to his or her name from business object.

  * @param args program argument.
  */
  public static void main(final String[] args) {
    var hash = new DataTransferHashObject();
    var business = new Business();
    business.createHash("Alice", "88887777", hash);
    LOGGER.info("Business object adds Alice's phone number 88887777.");
    business.createHash("Bob", "67189923", hash);
    LOGGER.info("Business object adds Bob's phone number 67189923");
    business.createHash("Trump", "33521179", hash);
    LOGGER.info("Business object adds Trump's phone number 33521179");
    var presentation = new Presentation();
    var alicePhoneNumber = presentation.getData("Alice", hash);
    LOGGER.info("Presentation tier receives Alice's phone number: " + alicePhoneNumber);
    var bobPhoneNumber = presentation.getData("Bob", hash);
    LOGGER.info("Presentation tier receives Bob's phone number: " + bobPhoneNumber);
    var trumpPhoneNumber = presentation.getData("Trump", hash);
    LOGGER.info("Presentation tier receives Trump's phone number: " + trumpPhoneNumber);
  }
}
