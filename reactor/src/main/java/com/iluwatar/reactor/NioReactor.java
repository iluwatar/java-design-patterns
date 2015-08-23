package com.iluwatar.reactor;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/*
 * Abstractions
 * ---------------
 * 
 * 1 - Dispatcher
 * 2 - Synchronous Event De-multiplexer
 * 3 - Event
 * 4 - Event Handler & concrete event handler (application business logic)
 * 5 - Selector
 */
public class NioReactor {

	private Selector selector;
	private Acceptor acceptor;
	private List<NioChannelEventHandler> eventHandlers = new CopyOnWriteArrayList<>();
	
	public NioReactor() throws IOException {
		this.acceptor = new Acceptor();
		this.selector = Selector.open();
	}
	
	public NioReactor registerChannel(SelectableChannel channel) throws IOException {
		SelectionKey key = channel.register(selector, SelectionKey.OP_ACCEPT);
		key.attach(acceptor);
		return this;
	}
	
	public void registerHandler(NioChannelEventHandler handler) {
		eventHandlers.add(handler);
	}
	
	public void start() throws IOException {
		new Thread( new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("Reactor started, waiting for events...");
					eventLoop();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void eventLoop() throws IOException {
		while (true) {
			selector.select(1000);
			Set<SelectionKey> keys = selector.selectedKeys();
			for (SelectionKey key : keys) {
				dispatchEvent(key);
			}
			keys.clear();
		}
	}
	
	private void dispatchEvent(SelectionKey key) throws IOException {
		Object handler = key.attachment();
		if (handler != null) {
			((EventHandler)handler).handle(key.channel());
		}
	}

	interface EventHandler {
		void handle(SelectableChannel channel) throws IOException;
	}

	private class Acceptor implements EventHandler {
		
		public void handle(SelectableChannel channel) throws IOException {
			// non-blocking accept as acceptor will only be called when accept event is available
			SocketChannel clientChannel = ((ServerSocketChannel)channel).accept();
			if (clientChannel != null) {
				new ChannelHandler(clientChannel).handle(clientChannel);
			}
			System.out.println("Connection established with a client");
		}
	}
	
	public static interface NioChannelEventHandler {
		void onReadable(SocketChannel channel);
	}
	
	private class ChannelHandler implements EventHandler {
		
		private SocketChannel clientChannel;
		private SelectionKey selectionKey;

		public ChannelHandler(SocketChannel clientChannel) throws IOException {
			this.clientChannel = clientChannel;
			clientChannel.configureBlocking(false);
			selectionKey = clientChannel.register(selector, 0);
			selectionKey.attach(this);
			selectionKey.interestOps(SelectionKey.OP_READ);
			selector.wakeup();
		}

		public void handle(SelectableChannel channel) throws IOException {
			// only read events are supported.
			for (NioChannelEventHandler eventHandler : eventHandlers) {
				eventHandler.onReadable(clientChannel);
			}
		}
	}
}
