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
