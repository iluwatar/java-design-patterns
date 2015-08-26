package com.iluwatar.reactor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NioServerSocketChannel extends AbstractNioChannel {

	private int port;

	public NioServerSocketChannel(int port, ChannelHandler handler) throws IOException {
		super(handler, ServerSocketChannel.open());
		this.port = port;
	}

	@Override
	public int getInterestedOps() {
		return SelectionKey.OP_ACCEPT;
	}

	@Override
	public ServerSocketChannel getChannel() {
		return (ServerSocketChannel) super.getChannel();
	}
	
	@Override
	public ByteBuffer read(SelectionKey key) throws IOException {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		int read = socketChannel.read(buffer);
		if (read == -1) {
			throw new IOException("Socket closed");
		}
		return buffer;
	}

	public void bind() throws IOException {
		((ServerSocketChannel)getChannel()).socket().bind(new InetSocketAddress(InetAddress.getLocalHost(), port));
		((ServerSocketChannel)getChannel()).configureBlocking(false);
		System.out.println("Bound TCP socket at port: " + port);
	}

	@Override
	protected void doWrite(ByteBuffer pendingWrite, SelectionKey key) throws IOException {
		System.out.println("Writing on channel");
		((SocketChannel)key.channel()).write(pendingWrite);
	}
}
