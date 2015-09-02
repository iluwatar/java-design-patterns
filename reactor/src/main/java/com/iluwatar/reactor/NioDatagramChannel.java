package com.iluwatar.reactor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
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
	public Object read(SelectionKey key) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		SocketAddress sender = getChannel().receive(buffer);
		DatagramPacket packet = new DatagramPacket(buffer);
		packet.setSender(sender);
		return packet;
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
	protected void doWrite(Object pendingWrite, SelectionKey key) throws IOException {
		DatagramPacket pendingPacket = (DatagramPacket) pendingWrite;
		getChannel().send(pendingPacket.getData(), pendingPacket.getReceiver());
	}
	
	static class DatagramPacket {
		private SocketAddress sender;
		private ByteBuffer data;
		private SocketAddress receiver;

		public DatagramPacket(ByteBuffer data) {
			this.data = data;
		}

		public SocketAddress getSender() {
			return sender;
		}
		
		public void setSender(SocketAddress sender) {
			this.sender = sender;
		}

		public SocketAddress getReceiver() {
			return receiver;
		}
		
		public void setReceiver(SocketAddress receiver) {
			this.receiver = receiver;
		}

		public ByteBuffer getData() {
			return data;
		}
	}
}