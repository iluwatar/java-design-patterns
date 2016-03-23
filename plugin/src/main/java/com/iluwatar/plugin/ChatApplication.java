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

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.iluwatar.plugin.annotations.Google;
import com.iluwatar.plugin.service.IChatService;

/**
 * Chat Application consuming services from different chat service providers
 */
public final class ChatApplication {

  /**
   * Actual Chat Service
   */
  @Inject
  @Google
  private IChatService chatService;

  /**
   * The user id of the chat initiator
   */
  @Inject
  @Named("user")
  private String userId;

  /**
   * Starts the chat messaging
   *
   * @return the string representation of the actual service call
   */
  public String startChat() {
    return this.chatService.chat();
  }

  /**
   * Returns the user id of the messaging initiator
   *
   * @return the user id
   */
  public String whoIsChatting() {
    return this.userId;
  }

}
