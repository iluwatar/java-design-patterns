package com.iluwatar.reactor;

import java.nio.channels.SelectionKey;

public interface Dispatcher {
	void onChannelReadEvent(AbstractNioChannel channel, Object readObject, SelectionKey key);
}
