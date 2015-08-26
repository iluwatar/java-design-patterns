package com.iluwatar.reactor;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

public class SameThreadDispatcher implements Dispatcher {

	@Override
	public void onChannelReadEvent(AbstractNioChannel channel, ByteBuffer readBytes, SelectionKey key) {
		if (channel.getHandler() != null) {
			channel.getHandler().handleChannelRead(channel, readBytes, key);
		}
	}
}
