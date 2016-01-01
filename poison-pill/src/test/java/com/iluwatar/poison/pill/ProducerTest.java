package com.iluwatar.poison.pill;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Date: 12/27/15 - 10:32 PM
 *
 * @author Jeroen Meulemeester
 */
public class ProducerTest {

  @Test
  public void testSend() throws Exception {
    final MqPublishPoint publishPoint = mock(MqPublishPoint.class);
    final Producer producer = new Producer("producer", publishPoint);
    verifyZeroInteractions(publishPoint);

    producer.send("Hello!");

    final ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
    verify(publishPoint).put(messageCaptor.capture());

    final Message message = messageCaptor.getValue();
    assertNotNull(message);
    assertEquals("producer", message.getHeader(Message.Headers.SENDER));
    assertNotNull(message.getHeader(Message.Headers.DATE));
    assertEquals("Hello!", message.getBody());

    verifyNoMoreInteractions(publishPoint);
  }

  @Test
  public void testStop() throws Exception {
    final MqPublishPoint publishPoint = mock(MqPublishPoint.class);
    final Producer producer = new Producer("producer", publishPoint);
    verifyZeroInteractions(publishPoint);

    producer.stop();
    verify(publishPoint).put(eq(Message.POISON_PILL));

    try {
      producer.send("Hello!");
      fail("Expected 'IllegalStateException' at this point, since the producer has stopped!");
    } catch (IllegalStateException e) {
      assertNotNull(e);
      assertNotNull(e.getMessage());
      assertEquals("Producer Hello! was stopped and fail to deliver requested message [producer].",
              e.getMessage());
    }

    verifyNoMoreInteractions(publishPoint);
  }

}
