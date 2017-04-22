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
