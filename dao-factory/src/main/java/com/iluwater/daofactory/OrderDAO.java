package com.iluwater.daofactory;

/**
 * An interface that determine the condition that
 * OrderDAO must support
 */
public interface OrderDAO {
    public boolean insertOrder(String senderName, String rcvrName, String destination);
    public Order findOrder(String senderName, String rcvrName, String destination);
    public boolean deleteOrder(String senderName, String rcvrName, String destination);
    public boolean updateOrder(Order ord);
}
