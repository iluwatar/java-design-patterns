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