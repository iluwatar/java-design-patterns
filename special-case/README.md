---
layout: pattern
title: Special Case
folder: special-case
permalink: /patterns/special-case/
categories: Behavioral
language: en
tags:
 - Extensibility
---

## Intent

Define some special cases, and encapsulates them into subclasses that provide different special behaviors.

## Explanation

Real world example

> In an e-commerce system, presentation layer expects application layer to produce certain view model.
> We have a successful scenario, in which receipt view model contains actual data from the purchase,
> and a couple of failure scenarios.

In plain words

> Special Case pattern allows returning non-null real objects that perform special behaviors.

In [Patterns of Enterprise Application Architecture](https://martinfowler.com/books/eaa.html) says 
the difference from Null Object Pattern

> If you’ll pardon the unresistable pun, I see Null Object as special case of Special Case.

**Programmatic Example**

To focus on the pattern itself, we implement DB and maintenance lock of the e-commerce system by the singleton instance.

```java
public class Db {
  private static Db instance;
  private Map<String, User> userName2User;
  private Map<User, Account> user2Account;
  private Map<String, Product> itemName2Product;

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

  public void seedUser(String userName, Double amount) {
    User user = new User(userName);
    instance.userName2User.put(userName, user);
    Account account = new Account(amount);
    instance.user2Account.put(user, account);
  }

  public void seedItem(String itemName, Double price) {
    Product item = new Product(price);
    itemName2Product.put(itemName, item);
  }

  public User findUserByUserName(String userName) {
    if (!userName2User.containsKey(userName)) {
      return null;
    }
    return userName2User.get(userName);
  }

  public Account findAccountByUser(User user) {
    if (!user2Account.containsKey(user)) {
      return null;
    }
    return user2Account.get(user);
  }

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

public class MaintenanceLock {
  private static final Logger LOGGER = LoggerFactory.getLogger(MaintenanceLock.class);

  private static MaintenanceLock instance;
  private boolean lock = true;

  public static MaintenanceLock getInstance() {
    if (instance == null) {
      synchronized (MaintenanceLock.class) {
        if (instance == null) {
          instance = new MaintenanceLock();
        }
      }
    }
    return instance;
  }

  public boolean isLock() {
    return lock;
  }

  public void setLock(boolean lock) {
    this.lock = lock;
    LOGGER.info("Maintenance lock is set to: " + lock);
  }
}
```

Let's first introduce presentation layer, the receipt view model interface and its implementation of successful scenario.

```java
public interface ReceiptViewModel {
  void show();
}

public class ReceiptDto implements ReceiptViewModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReceiptDto.class);

  private Double price;

  public ReceiptDto(Double price) {
    this.price = price;
  }

  public Double getPrice() {
    return price;
  }

  @Override
  public void show() {
    LOGGER.info("Receipt: " + price + " paid");
  }
}
```

And here are the implementations of failure scenarios, which are the special cases.

```java
public class DownForMaintenance implements ReceiptViewModel {
  private static final Logger LOGGER = LoggerFactory.getLogger(DownForMaintenance.class);

  @Override
  public void show() {
    LOGGER.info("Down for maintenance");
  }
}

public class InvalidUser implements ReceiptViewModel {
  private static final Logger LOGGER = LoggerFactory.getLogger(InvalidUser.class);

  private final String userName;

  public InvalidUser(String userName) {
    this.userName = userName;
  }

  @Override
  public void show() {
    LOGGER.info("Invalid user: " + userName);
  }
}

public class OutOfStock implements ReceiptViewModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(OutOfStock.class);

  private String userName;
  private String itemName;

  public OutOfStock(String userName, String itemName) {
    this.userName = userName;
    this.itemName = itemName;
  }

  @Override
  public void show() {
    LOGGER.info("Out of stock: " + itemName + " for user = " + userName + " to buy");
  }
}

public class InsufficientFunds implements ReceiptViewModel {
  private static final Logger LOGGER = LoggerFactory.getLogger(InsufficientFunds.class);

  private String userName;
  private Double amount;
  private String itemName;

  public InsufficientFunds(String userName, Double amount, String itemName) {
    this.userName = userName;
    this.amount = amount;
    this.itemName = itemName;
  }

  @Override
  public void show() {
    LOGGER.info("Insufficient funds: " + amount + " of user: " + userName
        + " for buying item: " + itemName);
  }
}
```

Second, here's the application layer, the application services implementation and the domain services implementation.

```java
public class ApplicationServicesImpl implements ApplicationServices {
  private DomainServicesImpl domain = new DomainServicesImpl();

  @Override
  public ReceiptViewModel loggedInUserPurchase(String userName, String itemName) {
    if (isDownForMaintenance()) {
      return new DownForMaintenance();
    }
    return this.domain.purchase(userName, itemName);
  }

  private boolean isDownForMaintenance() {
    return MaintenanceLock.getInstance().isLock();
  }
}

public class DomainServicesImpl implements DomainServices {
  public ReceiptViewModel purchase(String userName, String itemName) {
    Db.User user = Db.getInstance().findUserByUserName(userName);
    if (user == null) {
      return new InvalidUser(userName);
    }

    Db.Account account = Db.getInstance().findAccountByUser(user);
    return purchase(user, account, itemName);
  }

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
```

Finally, the client send requests the application services to get the presentation view.

```java
    // DB seeding
    LOGGER.info("Db seeding: " + "1 user: {\"ignite1771\", amount = 1000.0}, "
        + "2 products: {\"computer\": price = 800.0, \"car\": price = 20000.0}");
    Db.getInstance().seedUser("ignite1771", 1000.0);
    Db.getInstance().seedItem("computer", 800.0);
    Db.getInstance().seedItem("car", 20000.0);

    var applicationServices = new ApplicationServicesImpl();
    ReceiptViewModel receipt;

    LOGGER.info("[REQUEST] User: " + "abc123" + " buy product: " + "tv");
    receipt = applicationServices.loggedInUserPurchase("abc123", "tv");
    receipt.show();
    MaintenanceLock.getInstance().setLock(false);
    LOGGER.info("[REQUEST] User: " + "abc123" + " buy product: " + "tv");
    receipt = applicationServices.loggedInUserPurchase("abc123", "tv");
    receipt.show();
    LOGGER.info("[REQUEST] User: " + "ignite1771" + " buy product: " + "tv");
    receipt = applicationServices.loggedInUserPurchase("ignite1771", "tv");
    receipt.show();
    LOGGER.info("[REQUEST] User: " + "ignite1771" + " buy product: " + "car");
    receipt = applicationServices.loggedInUserPurchase("ignite1771", "car");
    receipt.show();
    LOGGER.info("[REQUEST] User: " + "ignite1771" + " buy product: " + "computer");
    receipt = applicationServices.loggedInUserPurchase("ignite1771", "computer");
    receipt.show();
```

Program output of every request:

```
    Down for maintenance
    Invalid user: abc123
    Out of stock: tv for user = ignite1771 to buy
    Insufficient funds: 1000.0 of user: ignite1771 for buying item: car
    Receipt: 800.0 paid    
```

## Class diagram

![alt text](./etc/special_case_urm.png "Special Case")

## Applicability

Use the Special Case pattern when

* You have multiple places in the system that have the same behavior after a conditional check 
for a particular class instance, or the same behavior after a null check.
* Return a real object that performs the real behavior, instead of a null object that performs nothing.

## Tutorial 

* [Special Case Tutorial](https://www.codinghelmet.com/articles/reduce-cyclomatic-complexity-special-case)

## Credits

* [How to Reduce Cyclomatic Complexity Part 2: Special Case Pattern](https://www.codinghelmet.com/articles/reduce-cyclomatic-complexity-special-case)
* [Patterns of Enterprise Application Architecture](https://martinfowler.com/books/eaa.html)
* [Special Case](https://www.martinfowler.com/eaaCatalog/specialCase.html)