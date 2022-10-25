package com.iluwatar.tabledatagateway;

public abstract class DataBase<T> {
    public abstract T select(T obj);
    public abstract T delete(int id);
    public abstract T update(int id, T obj);
    public abstract T insert(T obj);
}
