package com.iluwatar.reactor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;

public class NioDatagramChannel extends AbstractNioChannel {

	private int port;

	public NioDatagramChannel(int port, ChannelHandler handler) throws IOException {
		super(handler, DatagramChannel.open());
		this.port = port;
	}

	@Override
	public int getInterestedOps() {
		return SelectionKey.OP_READ;
	}

	@Override
	public ByteBuffer read(SelectionKey key) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		getChannel().receive(buffer);
		return buffer;
	}
	
	@Override
	public DatagramChannel getChannel() {
		return (DatagramChannel) super.getChannel();
	}
	
	public void bind() throws IOException {
		getChannel().socket().bind(new InetSocketAddress(InetAddress.getLocalHost(), port));
		getChannel().configureBlocking(false);
		System.out.println("Bound UDP socket at port: " + port);
	}

	@Override
	protected void doWrite(ByteBuffer pendingWrite, SelectionKey key) throws IOException {
		pendingWrite.flip();
		getChannel().write(pendingWrite);
	}
}