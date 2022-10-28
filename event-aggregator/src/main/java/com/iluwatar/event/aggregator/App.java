/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * A system with lots of objects can lead to complexities when a client wants to subscribe to
 * events. The client has to find and register for each object individually, if each object has
 * multiple events then each event requires a separate subscription.
 *
 * <p>An Event Aggregator acts as a single source of events for many objects. It registers for all
 * the events of the many objects allowing clients to register with just the aggregator.
 *
 * <p>In the example {@link LordBaelish}, {@link LordVarys} and {@link Scout} deliver events to
 * {@link KingsHand}. {@link KingsHand}, the event aggregator, then delivers the events to {@link
 * KingJoffrey}.
 */
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {

    var kingJoffrey = new KingJoffrey();
    var kingsHand = new KingsHand(kingJoffrey);

    var emitters = List.of(
        kingsHand,
        new LordBaelish(kingsHand),
        new LordVarys(kingsHand),
        new Scout(kingsHand)
    );

    Arrays.stream(Weekday.values())
        .<Consumer<? super EventEmitter>>map(day -> emitter -> emitter.timePasses(day))
        .forEachOrdered(emitters::forEach);
  }
}
