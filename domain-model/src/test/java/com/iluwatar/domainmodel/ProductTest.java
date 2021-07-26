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

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import static org.joda.money.CurrencyUnit.USD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductTest {

    private ProductDao productDao;
    private Product product;

    @BeforeEach
    void setUp() {
        productDao = mock(ProductDaoImpl.class);

        product = Product.builder()
                .name("product")
                .price(Money.of(USD, 100.0))
                .expirationDate(LocalDate.now().plusDays(10))
                .productDao(productDao)
                .build();
    }

    @Test
    void shouldSaveProduct() throws SQLException {
        when(productDao.findByName("product")).thenReturn(Optional.empty());

        product.save();

        verify(productDao, times(1)).save(product);

        when(productDao.findByName("product")).thenReturn(Optional.of(product));

        product.save();

        verify(productDao, times(1)).update(product);
    }

    @Test
    void shouldGetSalePriceOfProduct() {
        assertEquals(Money.of(USD, 100), product.getSalePrice());

        product.setExpirationDate(LocalDate.now().plusDays(2));

        assertEquals(Money.of(USD, 80), product.getSalePrice());
    }
}
