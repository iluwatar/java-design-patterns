package com.iluwatar.reactor;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

import com.iluwatar.reactor.NioDatagramChannel.DatagramPacket;

public class LoggingHandler implements ChannelHandler {

	@Override
	public void handleChannelRead(AbstractNioChannel channel, Object readObject, SelectionKey key) {
		if (readObject instanceof ByteBuffer) {
			byte[] data = ((ByteBuffer)readObject).array();
			doLogging(data);
			sendReply(channel, data, key);
		} else if (readObject instanceof DatagramPacket) {
			DatagramPacket datagram = (DatagramPacket)readObject;
			byte[] data = datagram.getData().array();
			doLogging(data);
			sendReply(channel, datagram, key);
		}
	}

	private void sendReply(AbstractNioChannel channel, DatagramPacket datagram, SelectionKey key) {
		DatagramPacket replyPacket = new DatagramPacket(ByteBuffer.wrap("Data logged successfully".getBytes()));
		replyPacket.setReceiver(datagram.getSender());
		channel.write(replyPacket, key);
	}

	private void sendReply(AbstractNioChannel channel, byte[] data, SelectionKey key) {
		ByteBuffer buffer = ByteBuffer.wrap("Data logged successfully".getBytes());
		channel.write(buffer, key);
	}

	private void doLogging(byte[] data) {
		// assuming UTF-8 :(
		System.out.println(new String(data));
	}
}
