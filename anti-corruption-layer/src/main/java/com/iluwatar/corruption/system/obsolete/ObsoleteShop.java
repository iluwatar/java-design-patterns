package com.iluwatar.corruption.system.obsolete;

import com.iluwatar.corruption.system.AntiCorruptionLayer;
import com.iluwatar.corruption.system.ShopException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ObsoleteShop {
    @Autowired
    private Store store;

    @Autowired
    private AntiCorruptionLayer acl;

    public void placeOrder(Order order) throws ShopException {

        String id = order.getId();
        if (store.get(id).isPresent()) {
            throw ShopException.throwDupEx(id);
        }

        Optional<Order> orderInModernSystem = acl.findOrderInModernSystem(id);

        if (orderInModernSystem.isPresent()) {
            var modernOrder = orderInModernSystem.get();
            if (modernOrder.equals(order)) {
                store.put(order.getId(), order);
            } else {
                throw ShopException.throwIncorrectData(order.toString(), modernOrder.toString());
            }
        } else {
            store.put(order.getId(), order);
        }
    }

    public Optional<Order> findOrder(String orderId) {
        return store.get(orderId);
    }
}
