package com.callusage.domain;

public class MessageData<T> {

    private T data;

    public MessageData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "MessageData{" +
                "data=" + data +
                '}';
    }
}
