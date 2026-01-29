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

// ABOUTME: Tests for Product domain model business logic.
// ABOUTME: Validates save operations and discount price calculation.
package com.iluwatar.domainmodel

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ProductTest {

    private lateinit var productDao: ProductDao
    private lateinit var product: Product

    @BeforeEach
    fun setUp() {
        productDao = mockk(relaxed = true)

        product = Product(
            productDao = productDao,
            name = "product",
            price = Money.of(CurrencyUnit.USD, 100.0),
            expirationDate = LocalDate.now().plusDays(10)
        )
    }

    @Test
    fun shouldSaveProduct() {
        every { productDao.findByName("product") } returns null

        product.save()

        verify(exactly = 1) { productDao.save(product) }

        every { productDao.findByName("product") } returns product

        product.save()

        verify(exactly = 1) { productDao.update(product) }
    }

    @Test
    fun shouldGetSalePriceOfProduct() {
        assertEquals(Money.of(CurrencyUnit.USD, 100.0), product.salePrice)

        product.expirationDate = LocalDate.now().plusDays(2)

        assertEquals(Money.of(CurrencyUnit.USD, 80.0), product.salePrice)
    }
}
