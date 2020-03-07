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

package com.iluwatar.reactor.framework;

import java.nio.channels.SelectionKey;

/**
 * Represents the <i>EventHandler</i> of Reactor pattern. It handles the incoming events dispatched
 * to it by the {@link Dispatcher}. This is where the application logic resides.
 *
 * <p>A {@link ChannelHandler} can be associated with one or many {@link AbstractNioChannel}s, and
 * whenever an event occurs on any of the associated channels, the handler is notified of the
 * event.
 */
public interface ChannelHandler {

  /**
   * Called when the {@code channel} receives some data from remote peer.
   *
   * @param channel    the channel from which the data was received.
   * @param readObject the data read.
   * @param key        the key on which read event occurred.
   */
  void handleChannelRead(AbstractNioChannel channel, Object readObject, SelectionKey key);
}
