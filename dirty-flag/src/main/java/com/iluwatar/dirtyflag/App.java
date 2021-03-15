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

package com.iluwatar.dirtyflag;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * This application demonstrates the <b>Dirty Flag</b> pattern. The dirty flag behavioral pattern
 * allows you to avoid expensive operations that would just need to be done again anyway. This is a
 * simple pattern that really just explains how to add a bool value to your class that you can set
 * anytime a property changes. This will let your class know that any results it may have previously
 * calculated will need to be calculated again when they’re requested. Once the results are
 * re-calculated, then the bool value can be cleared.
 *
 * <p>There are some points that need to be considered before diving into using this pattern:-
 * there are some things you’ll need to consider:- (1) Do you need it? This design pattern works
 * well when the results to be calculated are difficult or resource intensive to compute. You want
 * to save them. You also don’t want to be calculating them several times in a row when only the
 * last one counts. (2) When do you set the dirty flag? Make sure that you set the dirty flag within
 * the class itself whenever an important property changes. This property should affect the result
 * of the calculated result and by changing the property, that makes the last result invalid. (3)
 * When do you clear the dirty flag? It might seem obvious that the dirty flag should be cleared
 * whenever the result is calculated with up-to-date information but there are other times when you
 * might want to clear the flag.
 *
 * <p>In this example, the {@link DataFetcher} holds the <i>dirty flag</i>. It fetches and
 * re-fetches from <i>world.txt</i> when needed. {@link World} mainly serves the data to the
 * front-end.
 */
@Slf4j
public class App {

  /**
   * Program execution point.
   */
  public void run() {
    final var executorService = Executors.newSingleThreadScheduledExecutor();
    executorService.scheduleAtFixedRate(new Runnable() {
      final World world = new World();

      @Override
      public void run() {
        var countries = world.fetch();
        LOGGER.info("Our world currently has the following countries:-");
        countries.stream().map(country -> "\t" + country).forEach(LOGGER::info);
      }
    }, 0, 15, TimeUnit.SECONDS); // Run at every 15 seconds.
  }

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    var app = new App();
    app.run();
  }

}
