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
  public static Db getInstance() {
    if (instance == null) {
      synchronized (Db.class) {
        if (instance == null) {
          instance = new Db();
          instance.userName2User = new HashMap<>();
          instance.user2Account = new HashMap<>();
          instance.itemName2Product = new HashMap<>();
        }
      }
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
