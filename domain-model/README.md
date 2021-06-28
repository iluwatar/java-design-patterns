---
layout: pattern
title: Domain Model
folder: domain-model
permalink: /patterns/domain-module/
categories: Structural
tags:
 - Data access
---
## Intent
Domain model pattern provides an object-oriented way of dealing with complicated logic. Instead of having one procedure that handles all business logic for a user action there are multiple objects and each of them handles a slice of domain logic that is relevant to it.
## Explanation

Real world example

> Let's assume that we need to build e-commerce web application. While analyzing requirements you will notice that there are few nouns you talk about repeatedly. It’s your Customer, and a Product the customer looks for. These two are your domain-specific classes and each of that classes will include some business logic specific to its domain.

In plain words

> The Domain Model is an object model of the domain that incorporates both behavior and data.

Programmatic Example

In the example of the e-commerce app, we need to deal with the domain logic of customers who want to buy products and return them if they want. We can use the domain module pattern and create classes `Customer` and `Product` where every single instance of that class incorporates both behavior and data and represents only one record in the underlying table.

Here is the `Product` domain class with fields `name`, `price`, `expirationDate` which is specific for each product, `productDao` for working with DB, `save` method for saving product and `getSalePrice` method which return price for this product with discount.

```java
@Slf4j
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Product {

    private static final int DAYS_UNTIL_EXPIRATION_WHEN_DISCOUNT_ACTIVE = 4;
    private static final double DISCOUNT_RATE = 0.2;

    @NonNull private final ProductDao productDao;
    @NonNull private String name;
    @NonNull private Double price;
    @NonNull private LocalDate expirationDate;

    /**
     * Save product or update if product already exist.
     */
    public void save() {
        try {
            Optional<Product> product = productDao.findByName(name);
            if (product.isPresent()) {
                productDao.update(this);
            } else {
                productDao.save(this);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    /**
     * Calculate sale price of product with discount.
     */
    public double getSalePrice() {
        return price - calculateDiscount();
    }

    private double calculateDiscount() {
        if (ChronoUnit.DAYS.between(LocalDate.now(), expirationDate)
                < DAYS_UNTIL_EXPIRATION_WHEN_DISCOUNT_ACTIVE) {

            return price * DISCOUNT_RATE;
        }

        return 0;
    }
}
```

Here is the `Customer` domain class with fields `name`, `money` which is specific for each customer, `customerDao` for working with DB, `save` for saving customer, `buyProduct` which add a product to purchases and withdraw money, `returnProduct` which remove product from purchases and return money, `showPurchases` and `showBalance` methods for printing customer's purchases and money balance.

```java
@Slf4j
@Getter
@Setter
@Builder
public class Customer {

    @NonNull private final CustomerDao customerDao;
    @Builder.Default private List<Product> purchases = new ArrayList<>();
    @NonNull private String name;
    @NonNull private Double money;

    /**
     * Save customer or update if customer already exist.
     */
    public void save() {
        try {
            Optional<Customer> customer = customerDao.findByName(name);
            if (customer.isPresent()) {
                customerDao.update(this);
            } else {
                customerDao.save(this);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    /**
     * Add product to purchases, save to db and withdraw money.
     *
     * @param product to buy.
     */
    public void buyProduct(Product product) {
        LOGGER.info(
                String.format(
                        "%s want to buy %s($%.2f)...", name, product.getName(), product.getSalePrice()));
        try {
            try {
                withdraw(product.getSalePrice());
            } catch (IllegalArgumentException ex) {
                LOGGER.error(ex.getMessage());
                return;
            }
            customerDao.addProduct(product, this);
            purchases.add(product);
            LOGGER.info(String.format("%s bought %s!", name, product.getName()));
        } catch (SQLException exception) {
            receiveMoney(product.getSalePrice());
            LOGGER.error(exception.getMessage());
        }
    }

    /**
     * Remove product from purchases, delete from db and return money.
     *
     * @param product to return.
     */
    public void returnProduct(Product product) {
        LOGGER.info(
                String.format(
                        "%s want to return %s($%.2f)...", name, product.getName(), product.getSalePrice()));
        if (purchases.contains(product)) {
            try {
                customerDao.deleteProduct(product, this);
                purchases.remove(product);
                receiveMoney(product.getSalePrice());
                LOGGER.info(String.format("%s return %s!", name, product.getName()));
            } catch (SQLException ex) {
                LOGGER.error(ex.getMessage());
            }
        } else {
            LOGGER.error(String.format("%s didn't buy %s...", name, product.getName()));
        }
    }

    /**
     * Print customer's purchases.
     */
    public void showPurchases() {
        if (purchases.isEmpty()) {
            LOGGER.info(name + " didn't buy anything");
        } else {
            LOGGER.info(
                    name + " bought: "
                            + purchases.stream()
                            .map(p -> p.getName() + " - $" + p.getSalePrice())
                            .reduce((p1, p2) -> p1 + ", " + p2)
                            .get());
        }
    }

    /**
     * Print customer's money balance.
     */
    public void showBalance() {
        LOGGER.info(name + " balance: $" + money);
    }

    private void withdraw(Double amount) throws IllegalArgumentException {
        if (money - amount < 0) {
            throw new IllegalArgumentException("Not enough money!");
        }
        money -= amount;
    }

    private void receiveMoney(Double amount) {
        money += amount;
    }
```

In the class `App`, we create a new instance of class Customer which represents customer Tom and handle data and actions of that customer and creating three products that Tom wants to buy.


```java
// Create data source and create the customers, products and purchases tables
final var dataSource = createDataSource();
deleteSchema(dataSource);
createSchema(dataSource);

// create customer
// var customerDao = new CustomerDaoImpl(dataSource);

var tom = Customer.builder().name("Tom").money(30.0).customerDao(customerDao).build();

tom.save();

// create products
var productDao = new ProductDaoImpl(dataSource);

var eggs =
    Product.builder()
        .name("Eggs")
        .price(10.0)
        .expirationDate(LocalDate.now().plusDays(7))
        .productDao(productDao)
        .build();

var butter =
    Product.builder()
        .name("Butter")
        .price(20.00)
        .expirationDate(LocalDate.now().plusDays(9))
        .productDao(productDao)
        .build();

var cheese =
    Product.builder()
        .name("Cheese")
        .price(25.0)
        .expirationDate(LocalDate.now().plusDays(2))
        .productDao(productDao)
        .build();

eggs.save();
butter.save();
cheese.save();

// show money balance of customer after each purchase
tom.showBalance();
tom.showPurchases();

// buy eggs
tom.buyProduct(eggs);
tom.showBalance();

// buy butter
tom.buyProduct(butter);
tom.showBalance();

// trying to buy cheese, but receive a refusal
// because he didn't have enough money
tom.buyProduct(cheese);
tom.showBalance();

// return butter and get money back
tom.returnProduct(butter);
tom.showBalance();

// Tom can buy cheese now because he has enough money
// and there is a discount on cheese because it expires in 2 days
tom.buyProduct(cheese);

tom.save();

// show money balance and purchases after shopping
tom.showBalance();
tom.showPurchases();
```

The program output:

```java
16:33:58.602 [main] INFO com.iluwatar.domainmodel.Customer - Tom balance: $30.0
16:33:58.608 [main] INFO com.iluwatar.domainmodel.Customer - Tom didn't buy anything
16:33:58.616 [main] INFO com.iluwatar.domainmodel.Customer - Tom want to buy Eggs($10.00)...
16:33:58.631 [main] INFO com.iluwatar.domainmodel.Customer - Tom bought Eggs!
16:33:58.631 [main] INFO com.iluwatar.domainmodel.Customer - Tom balance: $20.0
16:33:58.631 [main] INFO com.iluwatar.domainmodel.Customer - Tom want to buy Butter($20.00)...
16:33:58.639 [main] INFO com.iluwatar.domainmodel.Customer - Tom bought Butter!
16:33:58.639 [main] INFO com.iluwatar.domainmodel.Customer - Tom balance: $0.0
16:33:58.639 [main] INFO com.iluwatar.domainmodel.Customer - Tom want to buy Cheese($20.00)...
16:33:58.639 [main] ERROR com.iluwatar.domainmodel.Customer - Not enough money!
16:33:58.639 [main] INFO com.iluwatar.domainmodel.Customer - Tom balance: $0.0
16:33:58.639 [main] INFO com.iluwatar.domainmodel.Customer - Tom want to return Butter($20.00)...
16:33:58.649 [main] INFO com.iluwatar.domainmodel.Customer - Tom returned Butter!
16:33:58.649 [main] INFO com.iluwatar.domainmodel.Customer - Tom balance: $20.0
16:33:58.650 [main] INFO com.iluwatar.domainmodel.Customer - Tom want to buy Cheese($20.00)...
16:33:58.657 [main] INFO com.iluwatar.domainmodel.Customer - Tom bought Cheese!
16:33:58.669 [main] INFO com.iluwatar.domainmodel.Customer - Tom balance: $0.0
16:33:58.674 [main] INFO com.iluwatar.domainmodel.Customer - Tom bought: Eggs - $10.0, Cheese - $20.0
```

## Class diagram

![](./etc/domain-model.urm.png "domain model")

## Applicability

Use a Domain model pattern when your domain logic is complex and that complexity can rapidly grow because this pattern handles increasing complexity very well. Otherwise, it's a more complex solution for organizing domain logic, so shouldn't use Domain Model pattern for systems with simple domain logic, because the cost of understanding it and complexity of data source exceeds the benefit of this pattern.

## Related patterns

- [Transaction Script](https://java-design-patterns.com/patterns/transaction-script/)

- [Table Module](https://java-design-patterns.com/patterns/table-module/)

## Credits

* [Domain Model Pattern](https://martinfowler.com/eaaCatalog/domainModel.html)
* [Patterns of Enterprise Application Architecture](https://www.amazon.com/gp/product/0321127420/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0321127420&linkId=18acc13ba60d66690009505577c45c04)
* [Architecture patterns: domain model and friends](https://inviqa.com/blog/architecture-patterns-domain-model-and-friends)