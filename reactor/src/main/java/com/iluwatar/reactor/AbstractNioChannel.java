package com.iluwatar.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class AbstractNioChannel {
	
	private SelectableChannel channel;
	private ChannelHandler handler;
	private Map<SelectableChannel, Queue<ByteBuffer>> channelToPendingWrites = new ConcurrentHashMap<>();
	private NioReactor reactor;
	
	public AbstractNioChannel(ChannelHandler handler, SelectableChannel channel) {
		this.handler = handler;
		this.channel = channel;
	}
	
	public void setReactor(NioReactor reactor) {
		this.reactor = reactor;
	}

	public SelectableChannel getChannel() {
		return channel;
	}

	public abstract int getInterestedOps();

	public abstract ByteBuffer read(SelectionKey key) throws IOException;

	public void setHandler(ChannelHandler handler) {
		this.handler = handler;
	}
	
	public ChannelHandler getHandler() {
		return handler;
	}

	// Called from the context of reactor thread
	public void write(SelectionKey key) throws IOException {
		Queue<ByteBuffer> pendingWrites = channelToPendingWrites.get(key.channel());
		while (true) {
			ByteBuffer pendingWrite = pendingWrites.poll();
			if (pendingWrite == null) {
				System.out.println("No more pending writes");
				reactor.changeOps(key, SelectionKey.OP_READ);
				break;
			}
			
			doWrite(pendingWrite, key);
		}
	}

	protected abstract void doWrite(ByteBuffer pendingWrite, SelectionKey key) throws IOException;

	public void write(ByteBuffer buffer, SelectionKey key) {
		Queue<ByteBuffer> pendingWrites = this.channelToPendingWrites.get(key.channel());
		if (pendingWrites == null) {
			synchronized (this.channelToPendingWrites) {
				pendingWrites = this.channelToPendingWrites.get(key.channel());
				if (pendingWrites == null) {
					pendingWrites = new ConcurrentLinkedQueue<>();
					this.channelToPendingWrites.put(key.channel(), pendingWrites);
				}
			}
		}
		pendingWrites.add(buffer);
		reactor.changeOps(key, SelectionKey.OP_WRITE);
	}
}
