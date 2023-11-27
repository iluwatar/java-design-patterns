package com.iluwatar.corruption.system.modern;

import com.iluwatar.corruption.system.AntiCorruptionLayer;
import com.iluwatar.corruption.system.ShopException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The class represents a modern shop system.
 * The main purpose of the class is to place orders and find orders.
 */
@Service
public class ModernShop {
    @Autowired
    private ModernStore store;

    @Autowired
    private AntiCorruptionLayer acl;

    /**
     * Places the order in the modern system.
     * If the order is already present in the legacy system, then the order is placed only if the data is the same.
     * If the order is not present in the legacy system, then the order is placed in the modern system.
     */
    public void placeOrder(ModernOrder order) throws ShopException {

        String id = order.getId();
        if (store.get(id).isPresent()) {
            throw ShopException.throwDupEx(id);
        }

        Optional<ModernOrder> orderInObsoleteSystem = acl.findOrderInLegacySystem(id);

        if (orderInObsoleteSystem.isPresent()) {
            var legacyOrder = orderInObsoleteSystem.get();
            if (!order.equals(legacyOrder)) {
                throw ShopException.throwIncorrectData(legacyOrder.toString(), order.toString());
            }
        } else {
            store.put(order.getId(), order);
        }
    }

    /**
     * Finds the order in the modern system.
     */
    public Optional<ModernOrder> findOrder(String orderId) {
        return store.get(orderId);
    }
}
