/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.iluwatar.databus.DataBus;
import com.iluwatar.databus.data.MessageData;
import com.iluwatar.databus.data.StartingData;
import com.iluwatar.databus.data.StoppingData;
import java.time.LocalDateTime;
import java.time.Month;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link StatusMember}.
 *
 */
class StatusMemberTest {

  @Test
  void statusRecordsTheStartTime() {
    //given
    final var startTime = LocalDateTime.of(2017, Month.APRIL, 1, 19, 9);
    final var startingData = new StartingData(startTime);
    final var statusMember = new StatusMember(1);
    //when
    statusMember.accept(startingData);
    //then
    assertEquals(startTime, statusMember.getStarted());
  }

  @Test
  void statusRecordsTheStopTime() {
    //given
    final var stop = LocalDateTime.of(2017, Month.APRIL, 1, 19, 12);
    final var stoppingData = new StoppingData(stop);
    stoppingData.setDataBus(DataBus.getInstance());
    final var statusMember = new StatusMember(1);
    //when
    statusMember.accept(stoppingData);
    //then
    assertEquals(stop, statusMember.getStopped());
  }

  @Test
  void statusIgnoresMessageData() {
    //given
    final var messageData = new MessageData("message");
    final var statusMember = new StatusMember(1);
    //when
    statusMember.accept(messageData);
    //then
    assertNull(statusMember.getStarted());
    assertNull(statusMember.getStopped());
  }

}
