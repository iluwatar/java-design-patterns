package com.iluwatar.eda.framework;

/**
 * A {@link DynamicRouter} is responsible for selecting the proper path of a {@link Message}
 * Messages can be associated to Channels through the registerChannel method and dispatched by
 * calling the dispatch method.
 */
public interface DynamicRouter<E extends Message> {

  void registerChannel(Class<? extends E> contentType, Channel<?> channel);

  void dispatch(E content);
}