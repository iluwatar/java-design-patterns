package com.iluwatar.reactor.app;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

import com.iluwatar.reactor.framework.AbstractNioChannel;
import com.iluwatar.reactor.framework.ChannelHandler;
import com.iluwatar.reactor.framework.NioDatagramChannel.DatagramPacket;

/**
 * Logging server application logic. It logs the incoming requests on standard console and returns a
 * canned acknowledgement back to the remote peer.
 */
public class LoggingHandler implements ChannelHandler {

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
      doLogging(((ByteBuffer) readObject));
      sendReply(channel, key);
    } else if (readObject instanceof DatagramPacket) {
      DatagramPacket datagram = (DatagramPacket) readObject;
      doLogging(datagram.getData());
      sendReply(channel, datagram, key);
    } else {
      throw new IllegalStateException("Unknown data received");
    }
  }

  private void sendReply(AbstractNioChannel channel, DatagramPacket incomingPacket, SelectionKey key) {
    /*
     * Create a reply acknowledgement datagram packet setting the receiver to the sender of incoming
     * message.
     */
    DatagramPacket replyPacket = new DatagramPacket(ByteBuffer.wrap(ACK));
    replyPacket.setReceiver(incomingPacket.getSender());

    channel.write(replyPacket, key);
  }

  private void sendReply(AbstractNioChannel channel, SelectionKey key) {
    ByteBuffer buffer = ByteBuffer.wrap(ACK);
    channel.write(buffer, key);
  }

  private void doLogging(ByteBuffer data) {
    // assuming UTF-8 :(
    System.out.println(new String(data.array(), 0, data.limit()));
  }
}
