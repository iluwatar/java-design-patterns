---
title: Special Case
category: Structural
language: en
tag:
    - Abstraction
    - Code simplification
    - Decoupling
    - Error handling
    - Polymorphism
    - Runtime
---

## Also known as

* Exceptional Case

## Intent

To handle exceptional cases or specific conditions without cluttering the main code logic.

## Explanation

Real-world example

> Consider a toll booth system on a highway. Normally, vehicles pass through the booth, and the system charges a toll based on the vehicle type. However, there are special cases: emergency vehicles like ambulances and fire trucks, which should not be charged.
>
> In this scenario, the "Special Case" design pattern can be applied by creating a class for the toll booth system that handles regular vehicles and another for emergency vehicles. The emergency vehicle class would override the toll calculation method to ensure no charge is applied, encapsulating this special behavior without cluttering the main toll calculation logic with conditional checks. This keeps the codebase clean and ensures the special case is handled consistently.

In plain words

> The Special Case design pattern encapsulates and isolates exceptional conditions and specific scenarios to simplify the main code logic and enhance maintainability.

In [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR) Martin Fowler says:

> If you’ll pardon the unresistable pun, I see [Null Object](https://java-design-patterns.com/patterns/null-object/) as special case of Special Case.

**Programmatic Example**

The Special Case Pattern is a software design pattern that is used to handle a specific, often uncommon, case separately from the general case in the code. This pattern is useful when a class has behavior that requires conditional logic based on its state. Instead of cluttering the class with conditional logic, we can encapsulate the special behavior in a subclass.

In an e-commerce system, the presentation layer relies on the application layer to generate a specific view model. There is a successful scenario where the receipt view model includes actual purchase data, along with a few failure scenarios.

The `Db` class is a singleton that holds data for users, accounts, and products. It provides methods to seed data into the database and find data in the database.

```java

@RequiredArgsConstructor
@Getter
public class Db {
    // Singleton instance of Db
    private static Db instance;

    // Maps to hold data
    private Map<String, User> userName2User;
    private Map<User, Account> user2Account;
    private Map<String, Product> itemName2Product;

    // Singleton method to get instance of Db
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

    // Methods to seed data into Db
    public void seedUser(String userName, Double amount) { /*...*/ }

    public void seedItem(String itemName, Double price) { /*...*/ }

    // Methods to find data in Db
    public User findUserByUserName(String userName) { /*...*/ }

    public Account findAccountByUser(User user) { /*...*/ }

    public Product findProductByItemName(String itemName) { /*...*/ }
}
```

Next, here are the presentation layer, the receipt view model interface and its implementation of successful scenario.

```java
public interface ReceiptViewModel {
    void show();
}
```

```java
@RequiredArgsConstructor
@Getter
public class ReceiptDto implements ReceiptViewModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiptDto.class);

    private final Double price;

    @Override
    public void show() {
        LOGGER.info(String.format("Receipt: %s paid", price));
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
```

```java
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
```

```java
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
```

```java
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

Here is the `App` and its `main` function that executes the different scenarios.

```java
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    private static final String LOGGER_STRING = "[REQUEST] User: {} buy product: {}";
    private static final String TEST_USER_1 = "ignite1771";
    private static final String TEST_USER_2 = "abc123";
    private static final String ITEM_TV = "tv";
    private static final String ITEM_CAR = "car";
    private static final String ITEM_COMPUTER = "computer";

    public static void main(String[] args) {
        // DB seeding
        LOGGER.info("Db seeding: " + "1 user: {\"ignite1771\", amount = 1000.0}, "
                + "2 products: {\"computer\": price = 800.0, \"car\": price = 20000.0}");
        Db.getInstance().seedUser(TEST_USER_1, 1000.0);
        Db.getInstance().seedItem(ITEM_COMPUTER, 800.0);
        Db.getInstance().seedItem(ITEM_CAR, 20000.0);

        final var applicationServices = new ApplicationServicesImpl();
        ReceiptViewModel receipt;

        LOGGER.info(LOGGER_STRING, TEST_USER_2, ITEM_TV);
        receipt = applicationServices.loggedInUserPurchase(TEST_USER_2, ITEM_TV);
        receipt.show();
        MaintenanceLock.getInstance().setLock(false);
        LOGGER.info(LOGGER_STRING, TEST_USER_2, ITEM_TV);
        receipt = applicationServices.loggedInUserPurchase(TEST_USER_2, ITEM_TV);
        receipt.show();
        LOGGER.info(LOGGER_STRING, TEST_USER_1, ITEM_TV);
        receipt = applicationServices.loggedInUserPurchase(TEST_USER_1, ITEM_TV);
        receipt.show();
        LOGGER.info(LOGGER_STRING, TEST_USER_1, ITEM_CAR);
        receipt = applicationServices.loggedInUserPurchase(TEST_USER_1, ITEM_CAR);
        receipt.show();
        LOGGER.info(LOGGER_STRING, TEST_USER_1, ITEM_COMPUTER);
        receipt = applicationServices.loggedInUserPurchase(TEST_USER_1, ITEM_COMPUTER);
        receipt.show();
    }
}
```

Here is the output from running the example.

```
11:23:48.669 [main] INFO com.iluwatar.specialcase.App -- Db seeding: 1 user: {"ignite1771", amount = 1000.0}, 2 products: {"computer": price = 800.0, "car": price = 20000.0}
11:23:48.672 [main] INFO com.iluwatar.specialcase.App -- [REQUEST] User: abc123 buy product: tv
11:23:48.672 [main] INFO com.iluwatar.specialcase.DownForMaintenance -- Down for maintenance
11:23:48.672 [main] INFO com.iluwatar.specialcase.MaintenanceLock -- Maintenance lock is set to: false
11:23:48.672 [main] INFO com.iluwatar.specialcase.App -- [REQUEST] User: abc123 buy product: tv
11:23:48.673 [main] INFO com.iluwatar.specialcase.InvalidUser -- Invalid user: abc123
11:23:48.674 [main] INFO com.iluwatar.specialcase.App -- [REQUEST] User: ignite1771 buy product: tv
11:23:48.674 [main] INFO com.iluwatar.specialcase.OutOfStock -- Out of stock: tv for user = ignite1771 to buy
11:23:48.674 [main] INFO com.iluwatar.specialcase.App -- [REQUEST] User: ignite1771 buy product: car
11:23:48.676 [main] INFO com.iluwatar.specialcase.InsufficientFunds -- Insufficient funds: 1000.0 of user: ignite1771 for buying item: car
11:23:48.676 [main] INFO com.iluwatar.specialcase.App -- [REQUEST] User: ignite1771 buy product: computer
11:23:48.676 [main] INFO com.iluwatar.specialcase.ReceiptDto -- Receipt: 800.0 paid
```

In conclusion, the Special Case Pattern helps to keep the code clean and easy to understand by separating the special case from the general case. It also promotes code reuse and makes the code easier to maintain.

## Applicability

* Use when you want to encapsulate and handle special cases or error conditions in a manner that avoids conditional logic scattered throughout the main codebase.
* Useful in scenarios where certain operations have known exceptional cases that require different handling.

## Known Uses

* Implementing null object patterns to avoid null checks.
* Handling specific business rules or validation logic in e-commerce applications.
* Managing different file formats or protocols in data processing applications.

## Consequences

Benefits:

* Simplifies the main logic by removing special case handling from the core algorithms.
* Enhances code readability and maintainability by isolating special cases.

Trade-offs:

* May introduce additional classes or interfaces, increasing the number of components in the system.
* Requires careful design to ensure that special cases are correctly encapsulated and do not introduce unexpected behaviors.

## Related Patterns

* [Decorator](https://java-design-patterns.com/patterns/decorator/): Can be used to add special case behavior to objects dynamically without modifying their code.
* [Null Object](https://java-design-patterns.com/patterns/null-object/): Used to provide a default behavior for null references, which is a specific type of special case.
* [Strategy](https://java-design-patterns.com/patterns/strategy/): Allows dynamic switching of special case behaviors by encapsulating them in different strategy classes.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
* [Special Case (Martin Fowler)](https://www.martinfowler.com/eaaCatalog/specialCase.html)
