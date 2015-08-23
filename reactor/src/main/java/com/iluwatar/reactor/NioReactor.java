package com.iluwatar.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class NioReactor {

	private int port;
	private Selector selector;
	private ServerSocketChannel serverSocketChannel;
	private NioChannelEventHandler nioEventhandler;

	public NioReactor(int port, NioChannelEventHandler handler) {
		this.port = port;
		this.nioEventhandler = handler;
	}
	
	
	public void start() throws IOException {
		startReactor();
		requestLoop();
	}

	private void startReactor() throws IOException {
		selector = Selector.open();
		serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		serverSocketChannel.configureBlocking(false);
		SelectionKey acceptorKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		acceptorKey.attach(new Acceptor());
		System.out.println("Reactor started listening on port: " + port);
	}
	
	private void requestLoop() throws IOException {
		while (true) {
			selector.select();
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
			((EventHandler)handler).handle();
		}
	}

	interface EventHandler {
		void handle() throws IOException;
	}

	private class Acceptor implements EventHandler {
		
		public void handle() throws IOException {
			// non-blocking accept as acceptor will only be called when accept event is available
			SocketChannel clientChannel = serverSocketChannel.accept();
			if (clientChannel != null) {
				new ChannelHandler(clientChannel).handle();
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

		public void handle() throws IOException {
			// only read events are supported.
			nioEventhandler.onReadable(clientChannel);
		}
	}
}
