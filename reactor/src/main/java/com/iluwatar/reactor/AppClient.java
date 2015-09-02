package com.iluwatar.reactor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AppClient {
	private ExecutorService service = Executors.newFixedThreadPool(3);
	
	public static void main(String[] args) {
		new AppClient().start();
	}

	public void start() {
		service.execute(new LoggingClient("Client 1", 6666));
		service.execute(new LoggingClient("Client 2", 6667));
		service.execute(new UDPLoggingClient(6668));
	}
	
	public void stop() {
		service.shutdown();
		if (!service.isTerminated()) {
			service.shutdownNow();
			try {
				service.awaitTermination(1000, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
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
				writeLogs(writer, socket.getInputStream());
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

		private void writeLogs(PrintWriter writer, InputStream inputStream) throws IOException {
			for (int i = 0; i < 4; i++) {
				writer.println(clientName + " - Log request: " + i);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				writer.flush();
				byte[] data = new byte[1024];
				int read = inputStream.read(data, 0, data.length);
				if (read == 0) {
					System.out.println("Read zero bytes");
				} else {
					System.out.println(new String(data, 0, read));
				}
			}
		}
	}
	
	static class UDPLoggingClient implements Runnable {
		private int port;

		public UDPLoggingClient(int port) {
			this.port = port;
		}
		
		@Override
		public void run() {
			DatagramSocket socket = null;
			try {
				socket = new DatagramSocket();
				for (int i = 0; i < 4; i++) {
					String message = "UDP Client" + " - Log request: " + i;
					try {
						DatagramPacket packet = new DatagramPacket(message.getBytes(), message.getBytes().length, new InetSocketAddress(InetAddress.getLocalHost(), port));
						socket.send(packet);
						
						byte[] data = new byte[1024];
						DatagramPacket reply = new DatagramPacket(data, data.length);
						socket.receive(reply);
						if (reply.getLength() == 0) {
							System.out.println("Read zero bytes");
						} else {
							System.out.println(new String(reply.getData(), 0, reply.getLength()));
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (SocketException e1) {
				e1.printStackTrace();
			} finally {
				if (socket != null) {
					socket.close();
				}
			}
		}
	}
}
