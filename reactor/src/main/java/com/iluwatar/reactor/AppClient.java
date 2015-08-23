package com.iluwatar.reactor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class AppClient {

	public static void main(String[] args) {
		new Thread(new LoggingClient("Client 1", 6666)).start();
		new Thread(new LoggingClient("Client 2", 6667)).start();
	}

	
	/*
	 * A logging client that sends logging requests to logging server
	 */
	static class LoggingClient implements Runnable {

		private int serverPort;
		private String clientName;

		public LoggingClient(String clientName, int serverPort) {
			this.clientName = clientName;
			this.serverPort = serverPort;
		}

		public void run() {
			Socket socket = null;
			try {
				socket = new Socket(InetAddress.getLocalHost(), serverPort);
				OutputStream outputStream = socket.getOutputStream();
				PrintWriter writer = new PrintWriter(outputStream);
				writeLogs(writer);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		private void writeLogs(PrintWriter writer) {
			for (int i = 0; i < 10; i++) {
				writer.println(clientName + " - Log request: " + i);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				writer.flush();
			}
		}
	}
}
