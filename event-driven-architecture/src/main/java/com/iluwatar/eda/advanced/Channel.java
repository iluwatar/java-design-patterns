package com.iluwatar.eda.advanced;


public interface Channel<E extends Message> {
    public void dispatch(E message);
}