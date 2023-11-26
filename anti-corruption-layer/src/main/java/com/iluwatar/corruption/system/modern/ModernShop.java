package com.iluwatar.corruption.system.modern;

import com.iluwatar.corruption.system.AntiCorruptionLayer;
import com.iluwatar.corruption.system.ShopException;
import com.iluwatar.corruption.system.obsolete.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ModernShop {
    @Autowired
    private Store store;

    @Autowired
    private AntiCorruptionLayer acl;

    public void placeOrder(ModernOrder order) throws ShopException {

        String id = order.getId();
        if (store.get(id).isPresent()) {
            throw ShopException.throwDupEx(id);
        }

        Optional<ModernOrder> orderInObsoleteSystem = acl.findOrderInObsoleteSystem(id);

        if (orderInObsoleteSystem.isPresent()) {
            var modernOrder = orderInObsoleteSystem.get();
            if (modernOrder.equals(order)) {
                store.put(order.getId(), order);
            } else {
                throw ShopException.throwIncorrectData(order.toString(), modernOrder.toString());
            }
        } else {
            store.put(order.getId(), order);
        }
    }

    public Optional<ModernOrder> findOrder(String orderId) {
        return store.get(orderId);
    }
}
