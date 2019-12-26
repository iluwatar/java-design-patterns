/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.reactor.app;

import com.iluwatar.reactor.framework.AbstractNioChannel;
import com.iluwatar.reactor.framework.ChannelHandler;
import com.iluwatar.reactor.framework.Dispatcher;
import com.iluwatar.reactor.framework.NioDatagramChannel;
import com.iluwatar.reactor.framework.NioReactor;
import com.iluwatar.reactor.framework.NioServerSocketChannel;
import com.iluwatar.reactor.framework.ThreadPoolDispatcher;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This application demonstrates Reactor pattern. The example demonstrated is a Distributed Logging
 * Service where it listens on multiple TCP or UDP sockets for incoming log requests.
 *
 * <p><i>INTENT</i> <br>
 * The Reactor design pattern handles service requests that are delivered concurrently to an
 * application by one or more clients. The application can register specific handlers for processing
 * which are called by reactor on specific events.
 *
 * <p><i>PROBLEM</i> <br>
 * Server applications in a distributed system must handle multiple clients that send them service
 * requests. Following forces need to be resolved:
 * <ul>
 * <li>Availability</li>
 * <li>Efficiency</li>
 * <li>Programming Simplicity</li>
 * <li>Adaptability</li>
 * </ul>
 *
 * <p><i>PARTICIPANTS</i> <br>
 * <ul>
 * <li>Synchronous Event De-multiplexer
 * <p>
 *     {@link NioReactor} plays the role of synchronous event de-multiplexer.
 * It waits for events on multiple channels registered to it in an event loop.
 * </p>
 * </li>
 * <li>Initiation Dispatcher
 * <p>
 *     {@link NioReactor} plays this role as the application specific {@link ChannelHandler}s
 * are registered to the reactor.
 * </p>
 * </li>
 * <li>Handle
 * <p>
 *     {@link AbstractNioChannel} acts as a handle that is registered to the reactor.
 * When any events occur on a handle, reactor calls the appropriate handler.
 * </p>
 * </li>
 * <li>Event Handler
 * <p>
 *      {@link ChannelHandler} acts as an event handler, which is bound to a
 * channel and is called back when any event occurs on any of its associated handles. Application
 * logic resides in event handlers.
 * </p>
 * </li>
 * </ul>
 * The application utilizes single thread to listen for requests on all ports. It does not create a
 * separate thread for each client, which provides better scalability under load (number of clients
 * increase).
 * The example uses Java NIO framework to implement the Reactor.
 */
public class App {

  private NioReactor reactor;
  private List<AbstractNioChannel> channels = new ArrayList<>();
  private Dispatcher dispatcher;

  /**
   * Creates an instance of App which will use provided dispatcher for dispatching events on
   * reactor.
   *
   * @param dispatcher the dispatcher that will be used to dispatch events.
   */
  public App(Dispatcher dispatcher) {
    this.dispatcher = dispatcher;
  }

  /**
   * App entry.
   */
  public static void main(String[] args) throws IOException {
    new App(new ThreadPoolDispatcher(2)).start();
  }

  /**
   * Starts the NIO reactor.
   *
   * @throws IOException if any channel fails to bind.
   */
  public void start() throws IOException {
    /*
     * The application can customize its event dispatching mechanism.
     */
    reactor = new NioReactor(dispatcher);

    /*
     * This represents application specific business logic that dispatcher will call on appropriate
     * events. These events are read events in our example.
     */
    LoggingHandler loggingHandler = new LoggingHandler();

    /*
     * Our application binds to multiple channels and uses same logging handler to handle incoming
     * log requests.
     */
    reactor.registerChannel(tcpChannel(6666, loggingHandler))
        .registerChannel(tcpChannel(6667, loggingHandler))
        .registerChannel(udpChannel(6668, loggingHandler)).start();
  }

  /**
   * Stops the NIO reactor. This is a blocking call.
   *
   * @throws InterruptedException if interrupted while stopping the reactor.
   * @throws IOException          if any I/O error occurs
   */
  public void stop() throws InterruptedException, IOException {
    reactor.stop();
    dispatcher.stop();
    for (AbstractNioChannel channel : channels) {
      channel.getJavaChannel().close();
    }
  }

  private AbstractNioChannel tcpChannel(int port, ChannelHandler handler) throws IOException {
    NioServerSocketChannel channel = new NioServerSocketChannel(port, handler);
    channel.bind();
    channels.add(channel);
    return channel;
  }

  private AbstractNioChannel udpChannel(int port, ChannelHandler handler) throws IOException {
    NioDatagramChannel channel = new NioDatagramChannel(port, handler);
    channel.bind();
    channels.add(channel);
    return channel;
  }
}
