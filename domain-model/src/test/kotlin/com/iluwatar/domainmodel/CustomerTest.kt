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

// ABOUTME: Tests for Customer domain model business logic.
// ABOUTME: Validates save, buy, and return product operations.
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

class CustomerTest {

    private lateinit var customerDao: CustomerDao
    private lateinit var customer: Customer
    private lateinit var product: Product

    @BeforeEach
    fun setUp() {
        customerDao = mockk(relaxed = true)

        customer = Customer(
            customerDao = customerDao,
            name = "customer",
            money = Money.of(CurrencyUnit.USD, 100.0)
        )

        product = Product(
            productDao = mockk(relaxed = true),
            name = "product",
            price = Money.of(CurrencyUnit.USD, 100.0),
            expirationDate = LocalDate.now().plusDays(10)
        )
    }

    @Test
    fun shouldSaveCustomer() {
        every { customerDao.findByName("customer") } returns null

        customer.save()

        verify(exactly = 1) { customerDao.save(customer) }

        every { customerDao.findByName("customer") } returns customer

        customer.save()

        verify(exactly = 1) { customerDao.update(customer) }
    }

    @Test
    fun shouldAddProductToPurchases() {
        product.price = Money.of(CurrencyUnit.USD, 200.0)

        customer.buyProduct(product)

        assertEquals(emptyList<Product>(), customer.purchases)
        assertEquals(Money.of(CurrencyUnit.USD, 100.0), customer.money)

        product.price = Money.of(CurrencyUnit.USD, 100.0)

        customer.buyProduct(product)

        assertEquals(listOf(product), customer.purchases)
        assertEquals(Money.zero(CurrencyUnit.USD), customer.money)
    }

    @Test
    fun shouldRemoveProductFromPurchases() {
        customer.purchases = mutableListOf(product)

        customer.returnProduct(product)

        assertEquals(emptyList<Product>(), customer.purchases)
        assertEquals(Money.of(CurrencyUnit.USD, 200.0), customer.money)

        customer.returnProduct(product)

        assertEquals(emptyList<Product>(), customer.purchases)
        assertEquals(Money.of(CurrencyUnit.USD, 200.0), customer.money)
    }
}
