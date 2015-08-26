package com.iluwatar.reactor;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

public class LoggingHandler implements ChannelHandler {

	@Override
	public void handleChannelRead(AbstractNioChannel channel, ByteBuffer readBytes, SelectionKey key) {
		byte[] data = readBytes.array();
		doLogging(data);
		sendEchoReply(channel, data, key);
	}

	private void sendEchoReply(AbstractNioChannel channel, byte[] data, SelectionKey key) {
		ByteBuffer buffer = ByteBuffer.wrap("Data logged successfully".getBytes());
		channel.write(buffer, key);
	}

	private void doLogging(byte[] data) {
		// assuming UTF-8 :(
		System.out.println(new String(data));
	}
}
