package com.iluwatar.reactor;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

public interface Dispatcher {
	void onChannelReadEvent(AbstractNioChannel channel, ByteBuffer readBytes, SelectionKey key);
}
