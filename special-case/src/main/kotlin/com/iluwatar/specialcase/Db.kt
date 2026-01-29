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

// ABOUTME: In-memory database singleton storing users, accounts, and products.
// ABOUTME: Provides methods to seed and query data for the special case pattern demo.
package com.iluwatar.specialcase

/**
 * DB class for seeding user info.
 */
class Db private constructor() {

    private val userName2User: MutableMap<String, User> = HashMap()
    private val user2Account: MutableMap<User, Account> = HashMap()
    private val itemName2Product: MutableMap<String, Product> = HashMap()

    /**
     * Seed a user into Db.
     *
     * @param userName of the user
     * @param amount of the user's account
     */
    fun seedUser(userName: String, amount: Double) {
        val user = User(userName)
        userName2User[userName] = user
        val account = Account(amount)
        user2Account[user] = account
    }

    /**
     * Seed an item into Db.
     *
     * @param itemName of the item
     * @param price of the item
     */
    fun seedItem(itemName: String, price: Double) {
        val item = Product(price)
        itemName2Product[itemName] = item
    }

    /**
     * Find a user with the userName.
     *
     * @param userName of the user
     * @return instance of User or null
     */
    fun findUserByUserName(userName: String): User? {
        return userName2User[userName]
    }

    /**
     * Find an account of the user.
     *
     * @param user in Db
     * @return instance of Account of the user or null
     */
    fun findAccountByUser(user: User): Account? {
        return user2Account[user]
    }

    /**
     * Find a product with the itemName.
     *
     * @param itemName of the item
     * @return instance of Product or null
     */
    fun findProductByItemName(itemName: String): Product? {
        return itemName2Product[itemName]
    }

    /**
     * User class to store user info.
     */
    inner class User(val userName: String) {
        fun purchase(item: Product): ReceiptDto {
            return ReceiptDto(item.price)
        }
    }

    /**
     * Account info.
     */
    class Account(val amount: Double) {
        /**
         * Withdraw the price of the item from the account.
         *
         * @param price of the item
         * @return instance of MoneyTransaction or null
         */
        fun withdraw(price: Double): MoneyTransaction? {
            if (price > amount) {
                return null
            }
            return MoneyTransaction(amount, price)
        }
    }

    /**
     * Product info.
     */
    class Product(val price: Double)

    companion object {
        @Volatile
        private var instance: Db? = null

        /**
         * Get the instance of Db.
         *
         * @return singleton instance of Db class
         */
        @JvmStatic
        @Synchronized
        fun getInstance(): Db {
            if (instance == null) {
                instance = Db()
            }
            return instance!!
        }
    }
}
