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

package com.iluwatar.featuretoggle.pattern.tieredversion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.featuretoggle.pattern.Service;
import com.iluwatar.featuretoggle.user.User;
import com.iluwatar.featuretoggle.user.UserGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test Tiered Feature Toggle
 */
class TieredFeatureToggleVersionTest {

  final User paidUser = new User("Jamie Coder");
  final User freeUser = new User("Alan Defect");
  final Service service = new TieredFeatureToggleVersion();

  @BeforeEach
  void setUp() {
    UserGroup.addUserToPaidGroup(paidUser);
    UserGroup.addUserToFreeGroup(freeUser);
  }

  @Test
  void testGetWelcomeMessageForPaidUser() {
    final var welcomeMessage = service.getWelcomeMessage(paidUser);
    final var expected = "You're amazing Jamie Coder. Thanks for paying for this awesome software.";
    assertEquals(expected, welcomeMessage);
  }

  @Test
  void testGetWelcomeMessageForFreeUser() {
    final var welcomeMessage = service.getWelcomeMessage(freeUser);
    final var expected = "I suppose you can use this software.";
    assertEquals(expected, welcomeMessage);
  }

  @Test
  void testIsEnhancedAlwaysTrueAsTiered() {
    assertTrue(service.isEnhanced());
  }
}
