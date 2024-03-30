/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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


    /**
     * Test the anti-corruption layer.
     * Main intention is to demonstrate how the anti-corruption layer works.
     * <p>
     * The 2 shops (modern and legacy) should operate independently and in the same time synchronize the data.
     * To avoid corrupting the domain models of the 2 shops, we use an anti-corruption layer
     * that transforms one model to another under the hood.
     *
     */
    @Test
    public void antiCorruptionLayerTest() throws ShopException {

        // a new order comes to the legacy shop.
        LegacyOrder legacyOrder = new LegacyOrder("1", "addr1", "item1", 1, 1);
        // place the order in the legacy shop.
        legacyShop.placeOrder(legacyOrder);
        // the order is placed as usual since there is no other orders with the id in the both systems.
        Optional<LegacyOrder> legacyOrderWithIdOne = legacyShop.findOrder("1");
        assertEquals(Optional.of(legacyOrder), legacyOrderWithIdOne);

        // a new order (or maybe just the same order) appears in the modern shop.
        ModernOrder modernOrder = new ModernOrder("1", new Customer("addr1"), new Shipment("item1", 1, 1), "");

        // the system places it, but it checks if there is an order with the same id in the legacy shop.
        modernShop.placeOrder(modernOrder);

        Optional<ModernOrder> modernOrderWithIdOne = modernShop.findOrder("1");
        // there is no new order placed since there is already an order with the same id in the legacy shop.
        assertTrue(modernOrderWithIdOne.isEmpty());

    }
    /**
     * Test the anti-corruption layer.
     * Main intention is to demonstrate how the anti-corruption layer works.
     * <p>
     * This test tests the anti-corruption layer from the rule the orders should be the same in the both systems.
     *
     */
    @Test(expected = ShopException.class)
    public void antiCorruptionLayerWithExTest() throws ShopException {

        // a new order comes to the legacy shop.
        LegacyOrder legacyOrder = new LegacyOrder("1", "addr1", "item1", 1, 1);
        // place the order in the legacy shop.
        legacyShop.placeOrder(legacyOrder);
        // the order is placed as usual since there is no other orders with the id in the both systems.
        Optional<LegacyOrder> legacyOrderWithIdOne = legacyShop.findOrder("1");
        assertEquals(Optional.of(legacyOrder), legacyOrderWithIdOne);

        // a new order but with the same id and different data appears in the modern shop
        ModernOrder modernOrder = new ModernOrder("1", new Customer("addr1"), new Shipment("item1", 10, 1), "");

        // the system rejects the order since there are 2 orders with contradiction there.
        modernShop.placeOrder(modernOrder);


    }
}