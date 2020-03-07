/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.event.aggregator;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

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
   * The {@link KingsHand} is both an {@link EventEmitter} as an {@link EventObserver} so verify if
   * every event received is passed up to it's superior, in most cases {@link KingJoffrey} but now
   * just a mocked observer.
   */
  @Test
  public void testPassThrough() throws Exception {
    final var observer = mock(EventObserver.class);
    final var kingsHand = new KingsHand(observer);

    // The kings hand should not pass any events before he received one
    verifyZeroInteractions(observer);

    // Verify if each event is passed on to the observer, nothing less, nothing more.
    Arrays.stream(Event.values()).forEach(event -> {
      kingsHand.onEvent(event);
      verify(observer, times(1)).onEvent(eq(event));
      verifyNoMoreInteractions(observer);
    });

  }

}