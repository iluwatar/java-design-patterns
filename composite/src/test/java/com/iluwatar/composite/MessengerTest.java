/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.composite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Date: 12/11/15 - 8:12 PM
 *
 * @author Jeroen Meulemeester
 */
class MessengerTest {

  /**
   * The buffer used to capture every write to {@link System#out}
   */
  private ByteArrayOutputStream stdOutBuffer = new ByteArrayOutputStream();

  /**
   * Keep the original std-out so it can be restored after the test
   */
  private final PrintStream realStdOut = System.out;

  /**
   * Inject the mocked std-out {@link PrintStream} into the {@link System} class before each test
   */
  @BeforeEach
  void setUp() {
    this.stdOutBuffer = new ByteArrayOutputStream();
    System.setOut(new PrintStream(stdOutBuffer));
  }

  /**
   * Removed the mocked std-out {@link PrintStream} again from the {@link System} class
   */
  @AfterEach
  void tearDown() {
    System.setOut(realStdOut);
  }

  /**
   * Test the message from the orcs
   */
  @Test
  void testMessageFromOrcs() {
    final var messenger = new Messenger();
    testMessage(
        messenger.messageFromOrcs(),
        "Where there is a whip there is a way."
    );
  }

  /**
   * Test the message from the elves
   */
  @Test
  void testMessageFromElves() {
    final var messenger = new Messenger();
    testMessage(
        messenger.messageFromElves(),
        "Much wind pours from your mouth."
    );
  }

  /**
   * Test if the given composed message matches the expected message
   *
   * @param composedMessage The composed message, received from the messenger
   * @param message         The expected message
   */
  private void testMessage(final LetterComposite composedMessage, final String message) {
    // Test is the composed message has the correct number of words
    final var words = message.split(" ");
    assertNotNull(composedMessage);
    assertEquals(words.length, composedMessage.count());

    // Print the message to the mocked stdOut ...
    composedMessage.print();

    // ... and verify if the message matches with the expected one
    assertEquals(message, new String(this.stdOutBuffer.toByteArray()).trim());
  }

}
