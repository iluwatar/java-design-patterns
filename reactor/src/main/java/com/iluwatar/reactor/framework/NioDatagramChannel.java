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
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A wrapper over {@link DatagramChannel} which can read and write data on a DatagramChannel.
 */
public class NioDatagramChannel extends AbstractNioChannel {

  private static final Logger LOGGER = LoggerFactory.getLogger(NioDatagramChannel.class);

  private final int port;

  /**
   * Creates a {@link DatagramChannel} which will bind at provided port and use <code>handler</code>
   * to handle incoming events on this channel.
   *
   * <p>Note the constructor does not bind the socket, {@link #bind()} method should be called for
   * binding the socket.
   *
   * @param port    the port to be bound to listen for incoming datagram requests.
   * @param handler the handler to be used for handling incoming requests on this channel.
   * @throws IOException if any I/O error occurs.
   */
  public NioDatagramChannel(int port, ChannelHandler handler) throws IOException {
    super(handler, DatagramChannel.open());
    this.port = port;
  }

  @Override
  public int getInterestedOps() {
    /*
     * there is no need to accept connections in UDP, so the channel shows interest in reading data.
     */
    return SelectionKey.OP_READ;
  }

  /**
   * Reads and returns a {@link DatagramPacket} from the underlying channel.
   *
   * @return the datagram packet read having the sender address.
   */
  @Override
  public DatagramPacket read(SelectionKey key) throws IOException {
    var buffer = ByteBuffer.allocate(1024);
    var sender = ((DatagramChannel) key.channel()).receive(buffer);

    /*
     * It is required to create a DatagramPacket because we need to preserve which socket address
     * acts as destination for sending reply packets.
     */
    buffer.flip();
    var packet = new DatagramPacket(buffer);
    packet.setSender(sender);

    return packet;
  }

  /**
   * Get datagram channel.
   *
   * @return the underlying datagram channel.
   */
  @Override
  public DatagramChannel getJavaChannel() {
    return (DatagramChannel) super.getJavaChannel();
  }

  /**
   * Binds UDP socket on the provided <code>port</code>.
   *
   * @throws IOException if any I/O error occurs.
   */
  @Override
  public void bind() throws IOException {
    getJavaChannel().socket().bind(new InetSocketAddress(InetAddress.getLocalHost(), port));
    getJavaChannel().configureBlocking(false);
    LOGGER.info("Bound UDP socket at port: {}", port);
  }

  /**
   * Writes the pending {@link DatagramPacket} to the underlying channel sending data to the
   * intended receiver of the packet.
   */
  @Override
  protected void doWrite(Object pendingWrite, SelectionKey key) throws IOException {
    var pendingPacket = (DatagramPacket) pendingWrite;
    getJavaChannel().send(pendingPacket.getData(), pendingPacket.getReceiver());
  }

  /**
   * Writes the outgoing {@link DatagramPacket} to the channel. The intended receiver of the
   * datagram packet must be set in the <code>data</code> using {@link
   * DatagramPacket#setReceiver(SocketAddress)}.
   */
  @Override
  public void write(Object data, SelectionKey key) {
    super.write(data, key);
  }

  /**
   * Container of data used for {@link NioDatagramChannel} to communicate with remote peer.
   */
  public static class DatagramPacket {
    private SocketAddress sender;
    private ByteBuffer data;
    private SocketAddress receiver;

    /**
     * Creates a container with underlying data.
     *
     * @param data the underlying message to be written on channel.
     */
    public DatagramPacket(ByteBuffer data) {
      this.data = data;
    }

    /**
     * Get sender address.
     *
     * @return the sender address.
     */
    public SocketAddress getSender() {
      return sender;
    }

    /**
     * Sets the sender address of this packet.
     *
     * @param sender the sender address.
     */
    public void setSender(SocketAddress sender) {
      this.sender = sender;
    }

    /**
     * Get receiver address.
     *
     * @return the receiver address.
     */
    public SocketAddress getReceiver() {
      return receiver;
    }

    /**
     * Sets the intended receiver address. This must be set when writing to the channel.
     *
     * @param receiver the receiver address.
     */
    public void setReceiver(SocketAddress receiver) {
      this.receiver = receiver;
    }

    /**
     * Get data.
     *
     * @return the underlying message that will be written on channel.
     */
    public ByteBuffer getData() {
      return data;
    }
  }
}
