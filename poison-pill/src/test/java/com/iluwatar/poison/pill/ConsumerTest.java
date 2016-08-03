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
package com.iluwatar.poison.pill;

import org.junit.Test;
import org.mockito.InOrder;

import java.time.LocalDateTime;

import static org.mockito.Mockito.inOrder;

/**
 * Date: 12/27/15 - 9:45 PM
 *
 * @author Jeroen Meulemeester
 */
public class ConsumerTest extends StdOutTest {

  @Test
  public void testConsume() throws Exception {
    final Message[] messages = new Message[]{
        createMessage("you", "Hello!"),
        createMessage("me", "Hi!"),
        Message.POISON_PILL,
        createMessage("late_for_the_party", "Hello? Anyone here?"),
    };

    final MessageQueue queue = new SimpleMessageQueue(messages.length);
    for (final Message message : messages) {
      queue.put(message);
    }

    new Consumer("NSA", queue).consume();

    final InOrder inOrder = inOrder(getStdOutMock());
    inOrder.verify(getStdOutMock()).println("Message [Hello!] from [you] received by [NSA]");
    inOrder.verify(getStdOutMock()).println("Message [Hi!] from [me] received by [NSA]");
    inOrder.verify(getStdOutMock()).println("Consumer NSA receive request to terminate.");
    inOrder.verifyNoMoreInteractions();

  }

  /**
   * Create a new message from the given sender with the given message body
   *
   * @param sender  The sender's name
   * @param message The message body
   * @return The message instance
   */
  private static Message createMessage(final String sender, final String message) {
    final SimpleMessage msg = new SimpleMessage();
    msg.addHeader(Message.Headers.SENDER, sender);
    msg.addHeader(Message.Headers.DATE, LocalDateTime.now().toString());
    msg.setBody(message);
    return msg;
  }

}