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

// ABOUTME: Implements the Customer domain model with business logic for purchases.
// ABOUTME: Contains data and behavior for a single customer including buy/return operations.
package com.iluwatar.domainmodel

import io.github.oshai.kotlinlogging.KotlinLogging
import org.joda.money.Money
import java.sql.SQLException

private val logger = KotlinLogging.logger {}

/**
 * This class organizes domain logic of customer. A single instance of this class contains both the
 * data and behavior of a single customer.
 */
class Customer(
    val customerDao: CustomerDao,
    var name: String,
    var money: Money,
    var purchases: MutableList<Product> = mutableListOf()
) {

    /** Save customer or update if customer already exists. */
    fun save() {
        try {
            val customer = customerDao.findByName(name)
            if (customer != null) {
                customerDao.update(this)
            } else {
                customerDao.save(this)
            }
        } catch (ex: SQLException) {
            logger.error { ex.message }
        }
    }

    /**
     * Add product to purchases, save to db and withdraw money.
     *
     * @param product to buy.
     */
    fun buyProduct(product: Product) {
        logger.info {
            "${name} want to buy ${product.name}($${String.format("%.2f", product.salePrice.amount)})..."
        }
        try {
            withdraw(product.salePrice)
        } catch (ex: IllegalArgumentException) {
            logger.error { ex.message }
            return
        }
        try {
            customerDao.addProduct(product, this)
            purchases.add(product)
            logger.info { "$name bought ${product.name}!" }
        } catch (exception: SQLException) {
            receiveMoney(product.salePrice)
            logger.error { exception.message }
        }
    }

    /**
     * Remove product from purchases, delete from db and return money.
     *
     * @param product to return.
     */
    fun returnProduct(product: Product) {
        logger.info {
            "${name} want to return ${product.name}($${String.format("%.2f", product.salePrice.amount)})..."
        }
        if (product in purchases) {
            try {
                customerDao.deleteProduct(product, this)
                purchases.remove(product)
                receiveMoney(product.salePrice)
                logger.info { "$name returned ${product.name}!" }
            } catch (ex: SQLException) {
                logger.error { ex.message }
            }
        } else {
            logger.error { "$name didn't buy ${product.name}..." }
        }
    }

    /** Print customer's purchases. */
    fun showPurchases() {
        val purchasesToShow = purchases
            .map { "${it.name} - $${it.salePrice.amount}" }
            .reduceOrNull { p1, p2 -> "$p1, $p2" }

        if (purchasesToShow != null) {
            logger.info { "$name bought: $purchasesToShow" }
        } else {
            logger.info { "$name didn't bought anything" }
        }
    }

    /** Print customer's money balance. */
    fun showBalance() {
        logger.info { "$name balance: $money" }
    }

    private fun withdraw(amount: Money) {
        if (money.compareTo(amount) < 0) {
            throw IllegalArgumentException("Not enough money!")
        }
        money = money.minus(amount)
    }

    private fun receiveMoney(amount: Money) {
        money = money.plus(amount)
    }
}
