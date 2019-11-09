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
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A wrapper over {@link NioServerSocketChannel} which can read and write data on a {@link
 * SocketChannel}.
 */
public class NioServerSocketChannel extends AbstractNioChannel {

  private static final Logger LOGGER = LoggerFactory.getLogger(NioServerSocketChannel.class);

  private final int port;

  /**
   * Creates a {@link ServerSocketChannel} which will bind at provided port and use
   * <code>handler</code> to handle incoming events on this channel.
   *
   * <p>Note the constructor does not bind the socket, {@link #bind()} method should be called for
   * binding the socket.
   *
   * @param port    the port on which channel will be bound to accept incoming connection requests.
   * @param handler the handler that will handle incoming requests on this channel.
   * @throws IOException if any I/O error occurs.
   */
  public NioServerSocketChannel(int port, ChannelHandler handler) throws IOException {
    super(handler, ServerSocketChannel.open());
    this.port = port;
  }


  @Override
  public int getInterestedOps() {
    // being a server socket channel it is interested in accepting connection from remote peers.
    return SelectionKey.OP_ACCEPT;
  }

  /**
   * Get server socket channel.
   *
   * @return the underlying {@link ServerSocketChannel}.
   */
  @Override
  public ServerSocketChannel getJavaChannel() {
    return (ServerSocketChannel) super.getJavaChannel();
  }

  /**
   * Reads and returns {@link ByteBuffer} from the underlying {@link SocketChannel} represented by
   * the <code>key</code>. Due to the fact that there is a dedicated channel for each client
   * connection we don't need to store the sender.
   */
  @Override
  public ByteBuffer read(SelectionKey key) throws IOException {
    SocketChannel socketChannel = (SocketChannel) key.channel();
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    int read = socketChannel.read(buffer);
    buffer.flip();
    if (read == -1) {
      throw new IOException("Socket closed");
    }
    return buffer;
  }

  /**
   * Binds TCP socket on the provided <code>port</code>.
   *
   * @throws IOException if any I/O error occurs.
   */
  @Override
  public void bind() throws IOException {
    getJavaChannel().socket().bind(
        new InetSocketAddress(InetAddress.getLocalHost(), port));
    getJavaChannel().configureBlocking(false);
    LOGGER.info("Bound TCP socket at port: {}", port);
  }

  /**
   * Writes the pending {@link ByteBuffer} to the underlying channel sending data to the intended
   * receiver of the packet.
   */
  @Override
  protected void doWrite(Object pendingWrite, SelectionKey key) throws IOException {
    ByteBuffer pendingBuffer = (ByteBuffer) pendingWrite;
    ((SocketChannel) key.channel()).write(pendingBuffer);
  }
}
