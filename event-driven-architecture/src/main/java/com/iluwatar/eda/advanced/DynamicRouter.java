package com.iluwatar.eda.advanced;

public interface DynamicRouter<E extends Message> {
    public void registerChannel(Class<? extends E> contentType, Channel<? extends E> channel);
    public void dispatch(E content);
}