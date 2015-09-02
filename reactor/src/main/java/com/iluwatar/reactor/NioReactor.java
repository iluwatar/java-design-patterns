package com.iluwatar.reactor;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
 * Abstractions
 * ---------------
 * 2 - Synchronous Event De-multiplexer
 */
public class NioReactor {

	private Selector selector;
	private Dispatcher dispatcher;
	private Queue<Command> pendingChanges = new ConcurrentLinkedQueue<>();
	private ExecutorService reactorService = Executors.newSingleThreadExecutor();
	
	public NioReactor(Dispatcher dispatcher) throws IOException {
		this.dispatcher = dispatcher;
		this.selector = Selector.open();
	}
	
	public NioReactor registerChannel(AbstractNioChannel channel) throws IOException {
		SelectionKey key = channel.getChannel().register(selector, channel.getInterestedOps());
		key.attach(channel);
		channel.setReactor(this);
		return this;
	}
	
	public void start() throws IOException {
		reactorService.execute(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("Reactor started, waiting for events...");
					eventLoop();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void stop() {
		reactorService.shutdownNow();
		selector.wakeup();
		try {
			reactorService.awaitTermination(4, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		dispatcher.stop();
	}

	private void eventLoop() throws IOException {
		while (true) {
			
			if (Thread.interrupted()) {
				break;
			}
			
			// honor any pending requests first
			processPendingChanges();
			
			selector.select();
			
			Set<SelectionKey> keys = selector.selectedKeys();

			Iterator<SelectionKey> iterator = keys.iterator();
			
			while (iterator.hasNext()) {
				SelectionKey key = iterator.next();
				if (!key.isValid()) {
					iterator.remove();
					continue;
				}
				processKey(key);
			}
			keys.clear();
		}
	}
	
	private void processPendingChanges() {
		Iterator<Command> iterator = pendingChanges.iterator();
		while (iterator.hasNext()) {
			Command command = iterator.next();
			command.execute();
			iterator.remove();
		}
	}

	private void processKey(SelectionKey key) throws IOException {
		if (key.isAcceptable()) {
			acceptConnection(key);
		} else if (key.isReadable()) {
			read(key);
		} else if (key.isWritable()) {
			write(key);
		}
	}

	private void write(SelectionKey key) throws IOException {
		AbstractNioChannel channel = (AbstractNioChannel) key.attachment();
		channel.write(key);
	}

	private void read(SelectionKey key) {
		Object readObject;
		try {
			readObject = ((AbstractNioChannel)key.attachment()).read(key);
			dispatchReadEvent(key, readObject);
		} catch (IOException e) {
			try {
				key.channel().close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void dispatchReadEvent(SelectionKey key, Object readObject) {
		dispatcher.onChannelReadEvent((AbstractNioChannel)key.attachment(), readObject, key);
	}

	private void acceptConnection(SelectionKey key) throws IOException {
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
		SocketChannel socketChannel = serverSocketChannel.accept();
		socketChannel.configureBlocking(false);
		SelectionKey readKey = socketChannel.register(selector, SelectionKey.OP_READ);
		readKey.attach(key.attachment());
	}

	interface Command {
		void execute();
	}
	
	public void changeOps(SelectionKey key, int interestedOps) {
		pendingChanges.add(new ChangeKeyOpsCommand(key, interestedOps));
		selector.wakeup();
	}
	
	class ChangeKeyOpsCommand implements Command {
		private SelectionKey key;
		private int interestedOps;
		
		public ChangeKeyOpsCommand(SelectionKey key, int interestedOps) {
			this.key = key;
			this.interestedOps = interestedOps;
		}
		
		public void execute() {
			key.interestOps(interestedOps);
		}
		
		@Override
		public String toString() {
			return "Change of ops to: " + interestedOps;
		}
	}
}