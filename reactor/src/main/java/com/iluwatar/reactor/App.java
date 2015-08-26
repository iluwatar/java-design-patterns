package com.iluwatar.reactor;

import java.io.IOException;

public class App {

	public static void main(String[] args) {
		try {
			NioReactor reactor = new NioReactor(new ThreadPoolDispatcher(2));
			LoggingHandler loggingHandler = new LoggingHandler();
			reactor
				.registerChannel(tcpChannel(6666, loggingHandler))
				.registerChannel(tcpChannel(6667, loggingHandler))
				.registerChannel(udpChannel(6668, loggingHandler))
			.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static AbstractNioChannel tcpChannel(int port, ChannelHandler handler) throws IOException {
		NioServerSocketChannel channel = new NioServerSocketChannel(port, handler);
		channel.bind();
		return channel;
	}
	
	private static AbstractNioChannel udpChannel(int port, ChannelHandler handler) throws IOException {
		NioDatagramChannel channel = new NioDatagramChannel(port, handler);
		channel.bind();
		return channel;
	}
}
