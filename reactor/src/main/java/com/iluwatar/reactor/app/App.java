package com.iluwatar.reactor.app;

import java.io.IOException;

import com.iluwatar.reactor.framework.AbstractNioChannel;
import com.iluwatar.reactor.framework.ChannelHandler;
import com.iluwatar.reactor.framework.NioDatagramChannel;
import com.iluwatar.reactor.framework.NioReactor;
import com.iluwatar.reactor.framework.NioServerSocketChannel;
import com.iluwatar.reactor.framework.ThreadPoolDispatcher;

/**
 * This application demonstrates Reactor pattern. It represents a Distributed Logging Service
 * where it can listen on multiple TCP or UDP sockets for incoming log requests.
 * 
 * <p>
 * <i>INTENT</i>
 * <br/>
 * The Reactor design pattern handles service requests that are delivered concurrently to an 
 * application by one or more clients. The application can register specific handlers for processing
 * which are called by reactor on specific events.
 * 
 * <p>
 * <i>PROBLEM</i>
 * <br/>
 * Server applications in a distributed system must handle multiple clients that send them service 
 * requests. Following forces need to be resolved:
 * <ul>
 * <li>Availability</li>
 * <li>Efficiency</li>
 * <li>Programming Simplicity</li>
 * <li>Adaptability</li>
 * </ul>
 * 
 * <p>
 * The application utilizes single thread to listen for requests on all ports. It does not create
 * a separate thread for each client, which provides better scalability under load (number of clients
 * increase).
 * 
 * <p>
 * The example uses Java NIO framework to implement the Reactor.
 * 
 * @author npathai
 *
 */
public class App {

	private NioReactor reactor;

	/**
	 * App entry.
	 */
	public static void main(String[] args) {
		try {
			new App().start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Starts the NIO reactor.
	 * @throws IOException if any channel fails to bind.
	 */
	public void start() throws IOException {
		/*
		 * The application can customize its event dispatching mechanism.
		 */
		reactor = new NioReactor(new ThreadPoolDispatcher(2));
		
		/*
		 *  This represents application specific business logic that dispatcher will call
		 *  on appropriate events. These events are read and write event in our example.
		 */
		LoggingHandler loggingHandler = new LoggingHandler();
		
		/*
		 * Our application binds to multiple I/O channels and uses same logging handler to handle
		 * incoming log requests.
		 */
		reactor
			.registerChannel(tcpChannel(6666, loggingHandler))
			.registerChannel(tcpChannel(6667, loggingHandler))
			.registerChannel(udpChannel(6668, loggingHandler))
		.start();
	}
	
	/**
	 * Stops the NIO reactor. This is a blocking call.
	 */
	public void stop() {
		reactor.stop();
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
