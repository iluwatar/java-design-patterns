package com.iluwatar.reactor;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

public interface ChannelHandler {

	void handleChannelRead(AbstractNioChannel channel, ByteBuffer readBytes, SelectionKey key);
}
