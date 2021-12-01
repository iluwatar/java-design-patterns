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


package com.iluwatar.application.controller;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class ApplicationControllerTest {

    /**
     * Verify Application controller can be instantiated.
     */
    @Test
    void shouldInstantiateControllerWithoutException() {
        assertDoesNotThrow(() -> new ApplicationController());
    }

    /**
     * Verify all defined pages can be reached.
     */
    @Test
    void testExistingPages() {
        ApplicationController controller = new ApplicationController();
        controller.handler("H");
        controller.handler("A");
        controller.handler("C");
        
        try {
          controller.handler("X");
          throw new Exception("Did not throw NullPointerException");
        } catch (NullPointerException e) {
          assertDoesNotThrow(() -> Target.clearScreen());
        } 
    }

  /**
   * Verify non-existent page throws null pointer exception.
   */
  @Test
  void testInvalidPage() {
      ApplicationController controller = new ApplicationController();
      assertThrows(NullPointerException.class,() -> controller.handler("P"));
  }

}
