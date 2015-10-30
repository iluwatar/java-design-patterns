package com.iluwatar.reactor.framework;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * A wrapper over {@link NioServerSocketChannel} which can read and write data on a
 * {@link SocketChannel}.
 */
public class NioServerSocketChannel extends AbstractNioChannel {

  private final int port;

  /**
   * Creates a {@link ServerSocketChannel} which will bind at provided port and use
   * <code>handler</code> to handle incoming events on this channel.
   * <p>
   * Note the constructor does not bind the socket, {@link #bind()} method should be called for
   * binding the socket.
   * 
   * @param port the port on which channel will be bound to accept incoming connection requests.
   * @param handler the handler that will handle incoming requests on this channel.
   * @throws IOException if any I/O error occurs.
   */
  public NioServerSocketChannel(int port, ChannelHandler handler) throws IOException {
    super(handler, ServerSocketChannel.open());
    this.port = port;
  }


  @Override
  public int getInterestedOps() {
    // being a server socket channel it is interested in accepting connection from remote peers.
    return SelectionKey.OP_ACCEPT;
  }

  /**
   * @return the underlying {@link ServerSocketChannel}.
   */
  @Override
  public ServerSocketChannel getJavaChannel() {
    return (ServerSocketChannel) super.getJavaChannel();
  }

  /**
   * Reads and returns {@link ByteBuffer} from the underlying {@link SocketChannel} represented by
   * the <code>key</code>. Due to the fact that there is a dedicated channel for each client
   * connection we don't need to store the sender.
   */
  @Override
  public ByteBuffer read(SelectionKey key) throws IOException {
    SocketChannel socketChannel = (SocketChannel) key.channel();
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    int read = socketChannel.read(buffer);
    buffer.flip();
    if (read == -1) {
      throw new IOException("Socket closed");
    }
    return buffer;
  }

  /**
   * Binds TCP socket on the provided <code>port</code>.
   * 
   * @throws IOException if any I/O error occurs.
   */
  @Override
  public void bind() throws IOException {
    ((ServerSocketChannel) getJavaChannel()).socket().bind(new InetSocketAddress(InetAddress.getLocalHost(), port));
    ((ServerSocketChannel) getJavaChannel()).configureBlocking(false);
    System.out.println("Bound TCP socket at port: " + port);
  }

  /**
   * Writes the pending {@link ByteBuffer} to the underlying channel sending data to the intended
   * receiver of the packet.
   */
  @Override
  protected void doWrite(Object pendingWrite, SelectionKey key) throws IOException {
    ByteBuffer pendingBuffer = (ByteBuffer) pendingWrite;
    ((SocketChannel) key.channel()).write(pendingBuffer);
  }
}
