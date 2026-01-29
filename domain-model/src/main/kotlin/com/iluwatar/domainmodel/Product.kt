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

// ABOUTME: Implements the Product domain model with pricing and discount logic.
// ABOUTME: Contains data and behavior for a single product including expiration-based discounts.
package com.iluwatar.domainmodel

import io.github.oshai.kotlinlogging.KotlinLogging
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import java.math.RoundingMode
import java.sql.SQLException
import java.time.LocalDate
import java.time.temporal.ChronoUnit

private val logger = KotlinLogging.logger {}

/**
 * This class organizes domain logic of product. A single instance of this class contains both the
 * data and behavior of a single product.
 */
class Product(
    val productDao: ProductDao,
    var name: String,
    var price: Money,
    var expirationDate: LocalDate
) {

    /** Save product or update if product already exists. */
    fun save() {
        try {
            val product = productDao.findByName(name)
            if (product != null) {
                productDao.update(this)
            } else {
                productDao.save(this)
            }
        } catch (ex: SQLException) {
            logger.error { ex.message }
        }
    }

    /** Calculate sale price of product with discount. */
    val salePrice: Money
        get() = price.minus(calculateDiscount())

    private fun calculateDiscount(): Money {
        return if (ChronoUnit.DAYS.between(LocalDate.now(), expirationDate)
            < DAYS_UNTIL_EXPIRATION_WHEN_DISCOUNT_ACTIVE
        ) {
            price.multipliedBy(DISCOUNT_RATE, RoundingMode.DOWN)
        } else {
            Money.zero(CurrencyUnit.USD)
        }
    }

    companion object {
        private const val DAYS_UNTIL_EXPIRATION_WHEN_DISCOUNT_ACTIVE = 4
        private const val DISCOUNT_RATE = 0.2
    }
}
