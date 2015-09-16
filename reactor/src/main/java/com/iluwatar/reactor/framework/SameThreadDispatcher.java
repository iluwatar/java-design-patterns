package com.iluwatar.reactor.framework;

import java.nio.channels.SelectionKey;

/**
 * Dispatches the events in the context of caller thread. This implementation is a good fit for
 * small applications where there are limited clients. Using this implementation limits the
 * scalability because the I/O thread performs the application specific processing.
 * 
 * <p>
 * For better performance use {@link ThreadPoolDispatcher}.
 * 
 * @see ThreadPoolDispatcher
 */
public class SameThreadDispatcher implements Dispatcher {

  /**
   * Dispatches the read event in the context of caller thread. <br/>
   * Note this is a blocking call. It returns only after the associated handler has handled the read
   * event.
   */
  @Override
  public void onChannelReadEvent(AbstractNioChannel channel, Object readObject, SelectionKey key) {
    /*
     * Calls the associated handler to notify the read event where application specific code
     * resides.
     */
    channel.getHandler().handleChannelRead(channel, readObject, key);
  }

  /**
   * No resources to free.
   */
  @Override
  public void stop() {
    // no-op
  }
}
