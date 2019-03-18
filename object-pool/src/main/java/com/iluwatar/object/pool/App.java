/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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
package com.iluwatar.object.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * When it is necessary to work with a large number of objects that are particularly expensive to
 * instantiate and each object is only needed for a short period of time, the performance of an
 * entire application may be adversely affected. An object pool design pattern may be deemed
 * desirable in cases such as these.
 * <p>
 * The object pool design pattern creates a set of objects that may be reused. When a new object is
 * needed, it is requested from the pool. If a previously prepared object is available it is
 * returned immediately, avoiding the instantiation cost. If no objects are present in the pool, a
 * new item is created and returned. When the object has been used and is no longer needed, it is
 * returned to the pool, allowing it to be used again in the future without repeating the
 * computationally expensive instantiation process. It is important to note that once an object has
 * been used and returned, existing references will become invalid.
 * <p>
 * In this example we have created {@link OliphauntPool} inheriting from generic {@link ObjectPool}.
 * {@link Oliphaunt}s can be checked out from the pool and later returned to it. The pool tracks
 * created instances and their status (available, inUse).
 *
 */
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * Program entry point
   * 
   * @param args command line args
   */
  public static void main(String[] args) {
    OliphauntPool pool = new OliphauntPool();
    LOGGER.info(pool.toString());
    Oliphaunt oliphaunt1 = pool.checkOut();
    LOGGER.info("Checked out {}", oliphaunt1);
    LOGGER.info(pool.toString());
    Oliphaunt oliphaunt2 = pool.checkOut();
    LOGGER.info("Checked out {}", oliphaunt2);
    Oliphaunt oliphaunt3 = pool.checkOut();
    LOGGER.info("Checked out {}", oliphaunt3);
    LOGGER.info(pool.toString());
    LOGGER.info("Checking in {}", oliphaunt1);
    pool.checkIn(oliphaunt1);
    LOGGER.info("Checking in {}", oliphaunt2);
    pool.checkIn(oliphaunt2);
    LOGGER.info(pool.toString());
    Oliphaunt oliphaunt4 = pool.checkOut();
    LOGGER.info("Checked out {}", oliphaunt4);
    Oliphaunt oliphaunt5 = pool.checkOut();
    LOGGER.info("Checked out {}", oliphaunt5);
    LOGGER.info(pool.toString());
  }
}
