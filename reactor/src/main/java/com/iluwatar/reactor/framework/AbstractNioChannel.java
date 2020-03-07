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

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This represents the <i>Handle</i> of Reactor pattern. These are resources managed by OS which can
 * be submitted to {@link NioReactor}.
 *
 * <p>This class serves has the responsibility of reading the data when a read event occurs and
 * writing the data back when the channel is writable. It leaves the reading and writing of data on
 * the concrete implementation. It provides a block writing mechanism wherein when any {@link
 * ChannelHandler} wants to write data back, it queues the data in pending write queue and clears it
 * in block manner. This provides better throughput.
 */
public abstract class AbstractNioChannel {

  private final SelectableChannel channel;
  private final ChannelHandler handler;
  private final Map<SelectableChannel, Queue<Object>> channelToPendingWrites;
  private NioReactor reactor;

  /**
   * Creates a new channel.
   *
   * @param handler which will handle events occurring on this channel.
   * @param channel a NIO channel to be wrapped.
   */
  public AbstractNioChannel(ChannelHandler handler, SelectableChannel channel) {
    this.handler = handler;
    this.channel = channel;
    this.channelToPendingWrites = new ConcurrentHashMap<>();
  }

  /**
   * Injects the reactor in this channel.
   */
  void setReactor(NioReactor reactor) {
    this.reactor = reactor;
  }

  /**
   * Get channel.
   *
   * @return the wrapped NIO channel.
   */
  public SelectableChannel getJavaChannel() {
    return channel;
  }

  /**
   * The operation in which the channel is interested, this operation is provided to {@link
   * Selector}.
   *
   * @return interested operation.
   * @see SelectionKey
   */
  public abstract int getInterestedOps();

  /**
   * Binds the channel on provided port.
   *
   * @throws IOException if any I/O error occurs.
   */
  public abstract void bind() throws IOException;

  /**
   * Reads the data using the key and returns the read data. The underlying channel should be
   * fetched using {@link SelectionKey#channel()}.
   *
   * @param key the key on which read event occurred.
   * @return data read.
   * @throws IOException if any I/O error occurs.
   */
  public abstract Object read(SelectionKey key) throws IOException;

  /**
   * Get handler.
   *
   * @return the handler associated with this channel.
   */
  public ChannelHandler getHandler() {
    return handler;
  }

  /*
   * Called from the context of reactor thread when the key becomes writable. The channel writes the
   * whole pending block of data at once.
   */
  void flush(SelectionKey key) throws IOException {
    var pendingWrites = channelToPendingWrites.get(key.channel());
    Object pendingWrite;
    while ((pendingWrite = pendingWrites.poll()) != null) {
      // ask the concrete channel to make sense of data and write it to java channel
      doWrite(pendingWrite, key);
    }
    // We don't have anything more to write so channel is interested in reading more data
    reactor.changeOps(key, SelectionKey.OP_READ);
  }

  /**
   * Writes the data to the channel.
   *
   * @param pendingWrite the data to be written on channel.
   * @param key          the key which is writable.
   * @throws IOException if any I/O error occurs.
   */
  protected abstract void doWrite(Object pendingWrite, SelectionKey key) throws IOException;

  /**
   * Queues the data for writing. The data is not guaranteed to be written on underlying channel
   * when this method returns. It will be written when the channel is flushed.
   *
   * <p>This method is used by the {@link ChannelHandler} to send reply back to the client. <br>
   * Example:
   *
   * <pre>
   * <code>
   * {@literal @}Override
   * public void handleChannelRead(AbstractNioChannel channel, Object readObj, SelectionKey key) {
   *   byte[] data = ((ByteBuffer)readObj).array();
   *   ByteBuffer buffer = ByteBuffer.wrap("Server reply".getBytes());
   *   channel.write(buffer, key);
   * }
   * </code>
   * </pre>
   *
   * @param data the data to be written on underlying channel.
   * @param key  the key which is writable.
   */
  public void write(Object data, SelectionKey key) {
    var pendingWrites = this.channelToPendingWrites.get(key.channel());
    if (pendingWrites == null) {
      synchronized (this.channelToPendingWrites) {
        pendingWrites = this.channelToPendingWrites.get(key.channel());
        if (pendingWrites == null) {
          pendingWrites = new ConcurrentLinkedQueue<>();
          this.channelToPendingWrites.put(key.channel(), pendingWrites);
        }
      }
    }
    pendingWrites.add(data);
    reactor.changeOps(key, SelectionKey.OP_WRITE);
  }
}
