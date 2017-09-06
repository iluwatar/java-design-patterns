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

import com.iluwatar.databus.DataBus;
import com.iluwatar.databus.data.MessageData;
import com.iluwatar.databus.data.StartingData;
import com.iluwatar.databus.data.StoppingData;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

/**
 * Tests for {@link StatusMember}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class StatusMemberTest {

  @Test
  public void statusRecordsTheStartTime() {
    //given
    final LocalDateTime startTime = LocalDateTime.of(2017, Month.APRIL, 1, 19, 9);
    final StartingData startingData = new StartingData(startTime);
    final StatusMember statusMember = new StatusMember(1);
    //when
    statusMember.accept(startingData);
    //then
    Assert.assertEquals(startTime, statusMember.getStarted());
  }

  @Test
  public void statusRecordsTheStopTime() {
    //given
    final LocalDateTime stop = LocalDateTime.of(2017, Month.APRIL, 1, 19, 12);
    final StoppingData stoppingData = new StoppingData(stop);
    stoppingData.setDataBus(DataBus.getInstance());
    final StatusMember statusMember = new StatusMember(1);
    //when
    statusMember.accept(stoppingData);
    //then
    Assert.assertEquals(stop, statusMember.getStopped());
  }

  @Test
  public void statusIgnoresMessageData() {
    //given
    final MessageData messageData = new MessageData("message");
    final StatusMember statusMember = new StatusMember(1);
    //when
    statusMember.accept(messageData);
    //then
    Assert.assertNull(statusMember.getStarted());
    Assert.assertNull(statusMember.getStopped());
  }

}
