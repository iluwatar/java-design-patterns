package com.iluwatar.reactor;

import java.nio.channels.SelectionKey;

public class SameThreadDispatcher implements Dispatcher {

	@Override
	public void onChannelReadEvent(AbstractNioChannel channel, Object readObject, SelectionKey key) {
		if (channel.getHandler() != null) {
			channel.getHandler().handleChannelRead(channel, readObject, key);
		}
	}

	@Override
	public void stop() {
		// no-op
	}
}
