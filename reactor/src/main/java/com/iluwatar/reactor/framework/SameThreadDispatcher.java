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
package com.iluwatar.reactor.framework;

import java.nio.channels.SelectionKey;

/**
 * Dispatches the events in the context of caller thread. This implementation is a good fit for
 * small applications where there are limited clients. Using this implementation limits the
 * scalability because the I/O thread performs the application specific processing.
 *
 * <p>For better performance use {@link ThreadPoolDispatcher}.
 *
 * @see ThreadPoolDispatcher
 */
public class SameThreadDispatcher implements Dispatcher {

  /**
   * Dispatches the read event in the context of caller thread. <br> Note this is a blocking call.
   * It returns only after the associated handler has handled the read event.
   */
  @Override
  public void onChannelReadEvent(AbstractNioChannel channel, Object readObject, SelectionKey key) {
    /*
     * Calls the associated handler to notify the read event where application specific code
     * resides.
     */
    channel.getHandler().handleChannelRead(channel, readObject, key);
  }

  /**
   * No resources to free.
   */
  @Override
  public void stop() {
    // no-op
  }
}
