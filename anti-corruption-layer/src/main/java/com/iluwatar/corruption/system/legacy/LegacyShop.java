package com.iluwatar.corruption.system.legacy;

import com.iluwatar.corruption.system.AntiCorruptionLayer;
import com.iluwatar.corruption.system.ShopException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * The class represents a legacy shop system.
 * The main purpose is to place the order from the customers.
 */
public class LegacyShop {
    @Autowired
    private Store store;

    @Autowired
    private AntiCorruptionLayer acl;

    /**
     * Places the order in the legacy system.
     * If the order is already present in the modern system, then the order is placed only if the data is the same.
     * If the order is not present in the modern system, then the order is placed in the legacy system.
     */
    public void placeOrder(LegacyOrder legacyOrder) throws ShopException {

        String id = legacyOrder.getId();
        if (store.get(id).isPresent()) {
            throw ShopException.throwDupEx(id);
        }
        // ensure that the order is not present in the modern system
        Optional<LegacyOrder> orderInModernSystem = acl.findOrderInModernSystem(id);
        // if the order is present in the modern system, then check that the data is the same
        if (orderInModernSystem.isPresent()) {
            var modernOrder = orderInModernSystem.get();
            if (modernOrder.equals(legacyOrder)) {
                store.put(legacyOrder.getId(), legacyOrder);
            } else {
                throw ShopException.throwIncorrectData(legacyOrder.toString(), modernOrder.toString());
            }
        } else {
            store.put(legacyOrder.getId(), legacyOrder);
        }
    }

    /**
     * Finds the order in the legacy system.
     */
    public Optional<LegacyOrder> findOrder(String orderId) {
        return store.get(orderId);
    }
}
