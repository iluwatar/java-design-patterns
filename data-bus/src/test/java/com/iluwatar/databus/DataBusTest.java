package com.iluwatar.databus;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

/**
 * Tests for {@link DataBus}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DataBusTest {

  @Mock
  private Member member;

  @Mock
  private DataType event;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void publishedEventIsReceivedBySubscribedMember() {
    //given
    final DataBus dataBus = DataBus.getInstance();
    dataBus.subscribe(member);
    //when
    dataBus.publish(event);
    //then
    then(member).should().accept(event);
  }

  @Test
  public void publishedEventIsNotReceivedByMemberAfterUnsubscribing() {
    //given
    final DataBus dataBus = DataBus.getInstance();
    dataBus.subscribe(member);
    dataBus.unsubscribe(member);
    //when
    dataBus.publish(event);
    //then
    then(member).should(never()).accept(event);
  }

}
