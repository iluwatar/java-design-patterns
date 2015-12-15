package com.iluwatar.flux.action;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Date: 12/12/15 - 10:11 PM
 *
 * @author Jeroen Meulemeester
 */
public class ContentTest {

  @Test
  public void testToString() throws Exception {
    for (final Content content : Content.values()) {
      final String toString = content.toString();
      assertNotNull(toString);
      assertFalse(toString.trim().isEmpty());
    }
  }

}
