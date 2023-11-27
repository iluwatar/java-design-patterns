package com.iluwatar.corruption.system;

import com.iluwatar.corruption.system.legacy.LegacyOrder;
import com.iluwatar.corruption.system.legacy.LegacyShop;
import com.iluwatar.corruption.system.modern.Customer;
import com.iluwatar.corruption.system.modern.ModernOrder;
import com.iluwatar.corruption.system.modern.ModernShop;
import com.iluwatar.corruption.system.modern.Shipment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AntiCorruptionLayerTest {

    @Autowired
    private LegacyShop legacyShop;

    @Autowired
    private ModernShop modernShop;


    @Test
    public void legacyShopTest() throws ShopException {

        LegacyOrder legacyOrder = new LegacyOrder("1", "addr1", "item1", 1, 1);
        legacyShop.placeOrder(legacyOrder);
        Optional<LegacyOrder> order = legacyShop.findOrder("1");
        assertEquals(Optional.of(legacyOrder), order);

        ModernOrder modernOrder = new ModernOrder("2", new Customer("addr2"), new Shipment("item2", 2, 2), "");
        modernShop.placeOrder(modernOrder);



        LegacyOrder legacyOrder2 = new LegacyOrder("2", "addr2", "item2", 2, 2);

        legacyShop.placeOrder(legacyOrder2);

        Optional<LegacyOrder> order2 = legacyShop.findOrder("2");

        assertEquals(Optional.empty(), order2);

    }
}