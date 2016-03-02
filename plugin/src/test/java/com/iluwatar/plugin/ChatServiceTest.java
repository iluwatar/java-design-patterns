/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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
package com.iluwatar.plugin;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.iluwatar.plugin.ChatApplication;
import com.iluwatar.plugin.config.modules.TestModule;

import junit.framework.TestCase;

/**
 * Unit test for {@link ChatApplication}
 */
public final class ChatServiceTest extends TestCase {

  /**
   * System Under test
   */
  private final ChatApplication application;

  /**
   * Constructor
   */
  public ChatServiceTest() {
    final Injector injector = Guice.createInjector(new TestModule());
    this.application = injector.getInstance(ChatApplication.class);
  }

  @Test
  public void testStartChat() {
    assertEquals("Test User", this.application.whoIsChatting());
  }

  @Test
  public void testWhoIsChating() {
    assertEquals("Mocking Chat Service", this.application.startChat());
  }

}
