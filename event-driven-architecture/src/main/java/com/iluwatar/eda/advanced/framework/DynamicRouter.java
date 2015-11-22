package com.iluwatar.eda.advanced.framework;

public interface DynamicRouter<E extends Message> {
    void registerChannel(Class<? extends E> contentType, Channel channel);
    void dispatch(E content);
}