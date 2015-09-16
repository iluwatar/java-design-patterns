package com.iluwatar.reactor.framework;

import java.nio.channels.SelectionKey;

/**
 * Represents the event dispatching strategy. When {@link NioReactor} senses any event on the
 * registered {@link AbstractNioChannel}s then it de-multiplexes the event type, read or write or
 * connect, and then calls the {@link Dispatcher} to dispatch the read events. This decouples the
 * I/O processing from application specific processing. <br/>
 * Dispatcher should call the {@link ChannelHandler} associated with the channel on which event
 * occurred.
 * 
 * <p>
 * The application can customize the way in which event is dispatched such as using the reactor
 * thread to dispatch event to channels or use a worker pool to do the non I/O processing.
 * 
 * @see SameThreadDispatcher
 * @see ThreadPoolDispatcher
 */
public interface Dispatcher {
  /**
   * This hook method is called when read event occurs on particular channel. The data read is
   * provided in <code>readObject</code>. The implementation should dispatch this read event to the
   * associated {@link ChannelHandler} of <code>channel</code>.
   * 
   * <p>
   * The type of <code>readObject</code> depends on the channel on which data was received.
   * 
   * @param channel on which read event occurred
   * @param readObject object read by channel
   * @param key on which event occurred
   */
  void onChannelReadEvent(AbstractNioChannel channel, Object readObject, SelectionKey key);

  /**
   * Stops dispatching events and cleans up any acquired resources such as threads.
   * 
   * @throws InterruptedException if interrupted while stopping dispatcher.
   */
  void stop() throws InterruptedException;
}
