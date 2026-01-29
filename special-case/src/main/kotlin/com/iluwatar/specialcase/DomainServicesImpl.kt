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

// ABOUTME: Domain service implementation handling purchase validation and processing.
// ABOUTME: Returns special case objects for invalid users, out of stock items, and insufficient funds.
package com.iluwatar.specialcase

/**
 * Implementation of DomainServices for special case.
 */
class DomainServicesImpl : DomainServices {

    /**
     * Domain purchase with userName and itemName, with validation for userName.
     *
     * @param userName of the user
     * @param itemName of the item
     * @return instance of ReceiptViewModel
     */
    fun purchase(userName: String, itemName: String): ReceiptViewModel {
        val user = Db.getInstance().findUserByUserName(userName)
            ?: return InvalidUser(userName)

        val account = Db.getInstance().findAccountByUser(user)
        return purchase(user, account!!, itemName)
    }

    /**
     * Domain purchase with user, account and itemName, with validation for whether product is out of
     * stock and whether user has insufficient funds in the account.
     *
     * @param user in Db
     * @param account in Db
     * @param itemName of the item
     * @return instance of ReceiptViewModel
     */
    private fun purchase(user: Db.User, account: Db.Account, itemName: String): ReceiptViewModel {
        val item = Db.getInstance().findProductByItemName(itemName)
            ?: return OutOfStock(user.userName, itemName)

        val receipt = user.purchase(item)
        val transaction = account.withdraw(receipt.price)
            ?: return InsufficientFunds(user.userName, account.amount, itemName)

        return receipt
    }
}
