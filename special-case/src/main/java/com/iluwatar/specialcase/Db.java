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

package com.iluwatar.specialcase;

import java.util.HashMap;
import java.util.Map;

public class Db {

  private static Db instance;
  private Map<String, User> userName2User;
  private Map<User, Account> user2Account;
  private Map<String, Product> itemName2Product;

  /**
   * Get the instance of Db.
   *
   * @return singleton instance of Db class
   */
  public static synchronized Db getInstance() {
    if (instance == null) {
      Db newInstance = new Db();
      newInstance.userName2User = new HashMap<>();
      newInstance.user2Account = new HashMap<>();
      newInstance.itemName2Product = new HashMap<>();
      instance = newInstance;
    }
    return instance;
  }

  /**
   * Seed a user into Db.
   *
   * @param userName of the user
   * @param amount of the user's account
   */
  public void seedUser(String userName, Double amount) {
    User user = new User(userName);
    instance.userName2User.put(userName, user);
    Account account = new Account(amount);
    instance.user2Account.put(user, account);
  }

  /**
   * Seed an item into Db.
   *
   * @param itemName of the item
   * @param price of the item
   */
  public void seedItem(String itemName, Double price) {
    Product item = new Product(price);
    itemName2Product.put(itemName, item);
  }

  /**
   * Find a user with the userName.
   *
   * @param userName of the user
   * @return instance of User
   */
  public User findUserByUserName(String userName) {
    if (!userName2User.containsKey(userName)) {
      return null;
    }
    return userName2User.get(userName);
  }

  /**
   * Find an account of the user.
   *
   * @param user in Db
   * @return instance of Account of the user
   */
  public Account findAccountByUser(User user) {
    if (!user2Account.containsKey(user)) {
      return null;
    }
    return user2Account.get(user);
  }

  /**
   * Find a product with the itemName.
   *
   * @param itemName of the item
   * @return instance of Product
   */
  public Product findProductByItemName(String itemName) {
    if (!itemName2Product.containsKey(itemName)) {
      return null;
    }
    return itemName2Product.get(itemName);
  }

  public class User {

    private String userName;

    public User(String userName) {
      this.userName = userName;
    }

    public String getUserName() {
      return userName;
    }

    public ReceiptDto purchase(Product item) {
      return new ReceiptDto(item.getPrice());
    }
  }

  public class Account {

    private Double amount;

    public Account(Double amount) {
      this.amount = amount;
    }

    /**
     * Withdraw the price of the item from the account.
     *
     * @param price of the item
     * @return instance of MoneyTransaction
     */
    public MoneyTransaction withdraw(Double price) {
      if (price > amount) {
        return null;
      }
      return new MoneyTransaction(amount, price);
    }

    public Double getAmount() {
      return amount;
    }
  }

  public class Product {

    private Double price;

    public Product(Double price) {
      this.price = price;
    }

    public Double getPrice() {
      return price;
    }
  }
}
