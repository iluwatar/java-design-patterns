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

package com.iluwatar.reactor.app;

import com.iluwatar.reactor.framework.AbstractNioChannel;
import com.iluwatar.reactor.framework.ChannelHandler;
import com.iluwatar.reactor.framework.NioDatagramChannel.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logging server application logic. It logs the incoming requests on standard console and returns a
 * canned acknowledgement back to the remote peer.
 */
public class LoggingHandler implements ChannelHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(LoggingHandler.class);

  private static final byte[] ACK = "Data logged successfully".getBytes();

  /**
   * Decodes the received data and logs it on standard console.
   */
  @Override
  public void handleChannelRead(AbstractNioChannel channel, Object readObject, SelectionKey key) {
    /*
     * As this handler is attached with both TCP and UDP channels we need to check whether the data
     * received is a ByteBuffer (from TCP channel) or a DatagramPacket (from UDP channel).
     */
    if (readObject instanceof ByteBuffer) {
      doLogging((ByteBuffer) readObject);
      sendReply(channel, key);
    } else if (readObject instanceof DatagramPacket) {
      var datagram = (DatagramPacket) readObject;
      doLogging(datagram.getData());
      sendReply(channel, datagram, key);
    } else {
      throw new IllegalStateException("Unknown data received");
    }
  }

  private static void sendReply(
      AbstractNioChannel channel,
      DatagramPacket incomingPacket,
      SelectionKey key
  ) {
    /*
     * Create a reply acknowledgement datagram packet setting the receiver to the sender of incoming
     * message.
     */
    var replyPacket = new DatagramPacket(ByteBuffer.wrap(ACK));
    replyPacket.setReceiver(incomingPacket.getSender());

    channel.write(replyPacket, key);
  }

  private static void sendReply(AbstractNioChannel channel, SelectionKey key) {
    var buffer = ByteBuffer.wrap(ACK);
    channel.write(buffer, key);
  }

  private static void doLogging(ByteBuffer data) {
    // assuming UTF-8 :(
    LOGGER.info(new String(data.array(), 0, data.limit()));
  }
}
