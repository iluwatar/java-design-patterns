/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.domainmodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CustomerTest {

    private CustomerDao customerDao;
    private Customer customer;
    private Product product;

    @BeforeEach
    void setUp() {
        customerDao = mock(CustomerDao.class);

        customer = Customer.builder()
                .name("customer")
                .money(100.0)
                .customerDao(customerDao)
                .build();

        product = Product.builder()
                .name("product")
                .price(100.0)
                .expirationDate(LocalDate.now().plusDays(10))
                .productDao(mock(ProductDao.class))
                .build();
    }

    @Test
    void saveTest() throws SQLException {
        when(customerDao.findByName("customer")).thenReturn(Optional.empty());

        customer.save();

        verify(customerDao, times(1)).save(customer);

        when(customerDao.findByName("customer")).thenReturn(Optional.of(customer));

        customer.save();

        verify(customerDao, times(1)).update(customer);
    }

    @Test
    void buyProductTest() {
        product.setPrice(200.0);

        customer.buyProduct(product);

        assertEquals(customer.getPurchases(), new ArrayList<>());
        assertEquals(customer.getMoney(), 100);

        product.setPrice(100.0);

        customer.buyProduct(product);

        assertEquals(customer.getPurchases(), new ArrayList<>(Arrays.asList(product)));
        assertEquals(customer.getMoney(), 0);
    }

    @Test
    void returnProductTest() {
        customer.setPurchases(new ArrayList<>(Arrays.asList(product)));

        customer.returnProduct(product);

        assertEquals(customer.getPurchases(), new ArrayList<>());
        assertEquals(customer.getMoney(), 200);

        customer.returnProduct(product);

        assertEquals(customer.getPurchases(), new ArrayList<>());
        assertEquals(customer.getMoney(), 200);
    }
}

