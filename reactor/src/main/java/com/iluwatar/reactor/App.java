package com.iluwatar.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import com.iluwatar.reactor.NioReactor.NioChannelEventHandler;

public class App {

	public static void main(String[] args) {
		try {
			new NioReactor(6666, new LoggingServer()).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
