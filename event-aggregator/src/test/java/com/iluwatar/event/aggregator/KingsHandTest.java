package com.iluwatar.event.aggregator;

import org.junit.Test;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Date: 12/12/15 - 10:57 AM
 *
 * @author Jeroen Meulemeester
 */
public class KingsHandTest extends EventEmitterTest<KingsHand> {

  /**
   * Create a new test instance, using the correct object factory
   */
  public KingsHandTest() {
    super(null, null, KingsHand::new, KingsHand::new);
  }

  /**
   * The {@link KingsHand} is both an {@EventEmitter} as an {@link EventObserver} so verify if every
   * event received is passed up to it's superior, in most cases {@link KingJoffrey} but now just a
   * mocked observer.
   */
  @Test
  public void testPassThrough() throws Exception {
    final EventObserver observer = mock(EventObserver.class);
    final KingsHand kingsHand = new KingsHand(observer);

    // The kings hand should not pass any events before he received one
    verifyZeroInteractions(observer);

    // Verify if each event is passed on to the observer, nothing less, nothing more.
    for (final Event event : Event.values()) {
      kingsHand.onEvent(event);
      verify(observer, times(1)).onEvent(eq(event));
      verifyNoMoreInteractions(observer);
    }

  }

}