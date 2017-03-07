/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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
package com.iluwatar.chain;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Date: 12/6/15 - 9:29 PM
 *
 * @author Jeroen Meulemeester
 */
public class OrcKingTest {

  /**
   * All possible requests
   */
  private static final Request[] REQUESTS = new Request[]{
      new Request(RequestType.DEFEND_CASTLE, "Don't let the barbarians enter my castle!!"),
      new Request(RequestType.TORTURE_PRISONER, "Don't just stand there, tickle him!"),
      new Request(RequestType.COLLECT_TAX, "Don't steal, the King hates competition ..."),
  };

  @Test
  public void testMakeRequest() throws Exception {
    final OrcKing king = new OrcKing();

    for (final Request request : REQUESTS) {
      king.makeRequest(request);
      assertTrue(
          "Expected all requests from King to be handled, but [" + request + "] was not!",
          request.isHandled()
      );
    }

  }

}