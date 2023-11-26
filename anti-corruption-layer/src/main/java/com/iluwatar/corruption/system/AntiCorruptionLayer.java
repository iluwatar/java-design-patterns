package com.iluwatar.corruption.system;

import com.iluwatar.corruption.system.modern.Customer;
import com.iluwatar.corruption.system.modern.ModernOrder;
import com.iluwatar.corruption.system.modern.ModernShop;
import com.iluwatar.corruption.system.modern.Shipment;
import com.iluwatar.corruption.system.obsolete.ObsoleteShop;
import com.iluwatar.corruption.system.obsolete.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class AntiCorruptionLayer {

    @Autowired
    private ModernShop modernShop;

    @Autowired
    private ObsoleteShop obsoleteShop;

    public Optional<Order> findOrderInModernSystem(String id) {
        return modernShop.findOrder(id).map(o ->
                new Order(
                        o.getId(),
                        o.getCustomer().getAddress(),
                        o.getShipment().getItem(),
                        o.getShipment().getQty(),
                        o.getShipment().getPrice()
                ));
    }

    public Optional<ModernOrder> findOrderInObsoleteSystem(String id) {

        return obsoleteShop.findOrder(id).map(o ->
                new ModernOrder(
                        o.getId(),
                        new Customer(o.getCustomer()),
                        new Shipment(o.getItem(), o.getQty(), o.getPrice()),
                        ""
                )
        );
    }

}
