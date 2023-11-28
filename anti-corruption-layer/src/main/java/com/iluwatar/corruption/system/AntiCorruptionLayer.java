package com.iluwatar.corruption.system;

import com.iluwatar.corruption.system.modern.Customer;
import com.iluwatar.corruption.system.modern.ModernOrder;
import com.iluwatar.corruption.system.modern.ModernShop;
import com.iluwatar.corruption.system.modern.Shipment;
import com.iluwatar.corruption.system.legacy.LegacyShop;
import com.iluwatar.corruption.system.legacy.LegacyOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The class represents an anti-corruption layer.
 * The main purpose of the class is to provide a layer between the modern and legacy systems.
 * The class is responsible for converting the data from one system to another
 * decoupling the systems to each other
 * <p>
 * It allows using one system a domain model of the other system without changing the domain model of the system.
 */
@Service
public class AntiCorruptionLayer {

    @Autowired
    private ModernShop modernShop;

    @Autowired
    private LegacyShop legacyShop;

    public Optional<LegacyOrder> findOrderInModernSystem(String id) {
        return modernShop.findOrder(id).map(o ->
                new LegacyOrder(
                        o.getId(),
                        o.getCustomer().getAddress(),
                        o.getShipment().getItem(),
                        o.getShipment().getQty(),
                        o.getShipment().getPrice()
                ));
    }

    public Optional<ModernOrder> findOrderInLegacySystem(String id) {

        return legacyShop.findOrder(id).map(o ->
                new ModernOrder(
                        o.getId(),
                        new Customer(o.getCustomer()),
                        new Shipment(o.getItem(), o.getQty(), o.getPrice()),
                        ""
                )
        );
    }

}
