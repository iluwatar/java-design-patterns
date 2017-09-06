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
package com.iluwatar.databus.members;

import com.iluwatar.databus.data.MessageData;
import com.iluwatar.databus.data.StartingData;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * Tests for {@link MessageCollectorMember}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class MessageCollectorMemberTest {

  @Test
  public void collectMessageFromMessageData() {
    //given
    final String message = "message";
    final MessageData messageData = new MessageData(message);
    final MessageCollectorMember collector = new MessageCollectorMember("collector");
    //when
    collector.accept(messageData);
    //then
    Assert.assertTrue(collector.getMessages().contains(message));
  }

  @Test
  public void collectIgnoresMessageFromOtherDataTypes() {
    //given
    final StartingData startingData = new StartingData(LocalDateTime.now());
    final MessageCollectorMember collector = new MessageCollectorMember("collector");
    //when
    collector.accept(startingData);
    //then
    Assert.assertEquals(0, collector.getMessages().size());
  }

}
