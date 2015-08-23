package com.iluwatar.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import com.iluwatar.reactor.NioReactor.NioChannelEventHandler;

public class App {

	public static void main(String[] args) {
		try {
			NioReactor reactor = new NioReactor();

			reactor
				.registerChannel(tcpChannel(6666))
				.registerChannel(tcpChannel(6667))
			.start();

			reactor.registerHandler(new LoggingServer());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static SelectableChannel udpChannel(int port) throws IOException {
		DatagramChannel channel = DatagramChannel.open();
		channel.socket().bind(new InetSocketAddress(port));
		channel.configureBlocking(false);
		System.out.println("Bound UDP socket at port: " + port);
		return channel;
	}

	private static SelectableChannel tcpChannel(int port) throws IOException {
		ServerSocketChannel channel = ServerSocketChannel.open();
		channel.socket().bind(new InetSocketAddress(port));
		channel.configureBlocking(false);
		System.out.println("Bound TCP socket at port: " + port);
		return channel;
	}

	static class LoggingServer implements NioChannelEventHandler {

		@Override
		public void onReadable(SocketChannel channel) {
			ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
			try {
				int byteCount = channel.read(requestBuffer);
				if (byteCount > 0) {
					byte[] logRequestContents = new byte[byteCount];
					byte[] array = requestBuffer.array();
					System.arraycopy(array, 0, logRequestContents, 0, byteCount);
					doLogging(new String(logRequestContents));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private void doLogging(String log) {
			// do logging at server side
			System.out.println(log);
		}
	}
}
