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
package com.iluwatar.specialcase;

/**
 * Implementation of DomainServices for special case.
 */
public class DomainServicesImpl implements DomainServices {

  /**
   * Domain purchase with userName and itemName, with validation for userName.
   *
   * @param userName of the user
   * @param itemName of the item
   * @return instance of ReceiptViewModel
   */
  public ReceiptViewModel purchase(String userName, String itemName) {
    Db.User user = Db.getInstance().findUserByUserName(userName);
    if (user == null) {
      return new InvalidUser(userName);
    }

    Db.Account account = Db.getInstance().findAccountByUser(user);
    return purchase(user, account, itemName);
  }

  /**
   * Domain purchase with user, account and itemName,
   * with validation for whether product is out of stock
   * and whether user has insufficient funds in the account.
   *
   * @param user in Db
   * @param account in Db
   * @param itemName of the item
   * @return instance of ReceiptViewModel
   */
  private ReceiptViewModel purchase(Db.User user, Db.Account account, String itemName) {
    Db.Product item = Db.getInstance().findProductByItemName(itemName);
    if (item == null) {
      return new OutOfStock(user.getUserName(), itemName);
    }

    ReceiptDto receipt = user.purchase(item);
    MoneyTransaction transaction = account.withdraw(receipt.getPrice());
    if (transaction == null) {
      return new InsufficientFunds(user.getUserName(), account.getAmount(), itemName);
    }

    return receipt;
  }
}
