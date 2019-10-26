/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.mute;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test for the mute-idiom pattern
 */
public class MuteTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(MuteTest.class);

  private static final String MESSAGE = "should not occur";

  @Test
  public void muteShouldRunTheCheckedRunnableAndNotThrowAnyExceptionIfCheckedRunnableDoesNotThrowAnyException() {
    Mute.mute(this::methodNotThrowingAnyException);
  }

  @Test
  public void muteShouldRethrowUnexpectedExceptionAsAssertionError() {
    assertThrows(AssertionError.class, () -> {
      Mute.mute(this::methodThrowingException);
    });
  }

  @Test
  public void loggedMuteShouldRunTheCheckedRunnableAndNotThrowAnyExceptionIfCheckedRunnableDoesNotThrowAnyException() {
    Mute.loggedMute(this::methodNotThrowingAnyException);
  }

  @Test
  public void loggedMuteShouldLogExceptionTraceBeforeSwallowingIt() {
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    System.setErr(new PrintStream(stream));

    Mute.loggedMute(this::methodThrowingException);

    assertTrue(new String(stream.toByteArray()).contains(MESSAGE));
  }


  private void methodNotThrowingAnyException() {
    LOGGER.info("Executed successfully");
  }

  private void methodThrowingException() throws Exception {
    throw new Exception(MESSAGE);
  }
}
