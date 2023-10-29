---
title: Microservice Architecture
category: Architectural
language: en
tag:
- Service Oriented
- Scalability
- Microservices
---
## Intent

Microservice Architecture breaks down a monolithic application into smaller, loosely coupled services that can be developed, deployed, and scaled independently.[1]

## Explanation

Real world example

> Imagine a large e-commerce platform. Instead of a single large application managing everything - user management, product catalog, order processing, and payment - the Microservice Architecture divides these into individual services. Each service focuses on a specific domain, possesses its own database, and communicates via APIs.

In plain words

> Microservice Architecture dissects an application into mini services. Each embodies its domain logic, data storage, and has distinct ways to communicate.

Wikipedia says

> A microservice architecture is a variant of the service-oriented architecture structural style. It is an architectural pattern that arranges an application as a collection of loosely coupled, fine-grained services, communicating through lightweight protocols.[2]


**Programmatic Example**
It is difficult to use code to reflect microservices alone. Microservices are not only in the structure of code, but also in deployment, operation and maintenance, isolation, service discovery, load balancing, communication and data independence.

Expanding on the e-commerce example, let's delve deeper into several services:

1. **User Management Service**

```java


@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @PostMapping
  public User createUser(@RequestBody User user) {
    return userRepository.save(user);
  }

  @GetMapping("/{userId}")
  public User getUser(@PathVariable Long userId) {
    return userRepository.findById(userId).orElse(null);
  }

  @PutMapping("/{userId}")
  public User updateUser(@PathVariable Long userId, @RequestBody User user) {
    User existingUser = userRepository.findById(userId).orElse(null);
    if (existingUser != null) {
      existingUser.updateFrom(user);
      return userRepository.save(existingUser);
    }
    return null; // or throw an exception
  }

  @DeleteMapping("/{userId}")
  public void deleteUser(@PathVariable Long userId) {
    userRepository.deleteById(userId);
  }
}

```

2. **Product Catalog Service**

```java
@RestController
@RequestMapping("/products")
public class ProductController {

  @Autowired
  private ProductRepository productRepository;

  @PostMapping
  public Product createProduct(@RequestBody Product product) {
    return productRepository.save(product);
  }

  @GetMapping("/{productId}")
  public Product getProduct(@PathVariable Long productId) {
    return productRepository.findById(productId).orElse(null);
  }

  @PutMapping("/{productId}")
  public Product updateProduct(@PathVariable Long productId, @RequestBody Product product) {
    Product existingProduct = productRepository.findById(productId).orElse(null);
    if (existingProduct != null) {
      existingProduct.updateFrom(product);
      return productRepository.save(existingProduct);
    }
    return null; 
  }

  @DeleteMapping("/{productId}")
  public void deleteProduct(@PathVariable Long productId) {
    productRepository.deleteById(productId);
  }
}
```

3. **Order Processing Service**

```java
@RestController
@RequestMapping("/orders")
public class OrderController {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private ProductClient productClient; //Remote call of Product microservice.

  @PostMapping
  public Order createOrder(@RequestBody Order order) {
    //Retrieve product information from Product microservice
    Product product = productClient.getProduct(order.getProductId()); 
    return orderRepository.save(order);
  }

  @GetMapping("/{orderId}")
  public Order getOrder(@PathVariable Long orderId) {
    return orderRepository.findById(orderId).orElse(null);
  }

  @PutMapping("/{orderId}")
  public Order updateOrder(@PathVariable Long orderId, @RequestBody Order order) {
    Order existingOrder = orderRepository.findById(orderId).orElse(null);
    if (existingOrder != null) {
      existingOrder.updateFrom(order);
      return orderRepository.save(existingOrder);
    }
    return null; 
  }

  @DeleteMapping("/{orderId}")
  public void deleteOrder(@PathVariable Long orderId) {
    orderRepository.deleteById(orderId);
  }
}

```

4. **Inventory Service**

```java
@RestController
@RequestMapping("/inventory")
public class InventoryController {

  @Autowired
  private InventoryRepository inventoryRepository;

  @PostMapping
  public Inventory addInventory(@RequestBody Inventory inventory) {
    return inventoryRepository.save(inventory);
  }

  @GetMapping("/{productId}")
  public Inventory getInventory(@PathVariable Long productId) {
    return inventoryRepository.findByProductId(productId).orElse(null);
  }

  @PutMapping("/{productId}")
  public Inventory updateInventory(@PathVariable Long productId, @RequestBody Inventory inventory) {
    Inventory existingInventory = inventoryRepository.findByProductId(productId).orElse(null);
    if (existingInventory != null) {
      existingInventory.updateFrom(inventory);
      return inventoryRepository.save(existingInventory);
    }
    return null;
  }

  @DeleteMapping("/{productId}")
  public void deleteInventory(@PathVariable Long productId) {
    inventoryRepository.deleteByProductId(productId);
  }
}
```

Each of these services would have its own logic, database operations, and could even be developed using different technologies tailored for the specific service's needs.

Communication between services can be established using REST APIs, message brokers, or other communication protocols.

## Class diagram

![alt text](./microservice-architecture/etc/microservice-architecture.png "Microservice Architecture")

## Applicability

Use the Microservice Architecture when:

- Individual parts of your application need to be scaled separately.
- You have a large development team, desiring concurrent development and deployment of distinct services.
- Aiming for high availability and resilience.
- Wishing to utilize diverse technologies and languages within various services.

## Known issues

- Service communication and coordination complexity.
- Distributed system challenges: network latency, fault tolerance, and data synchronization.
- The necessity for proficient monitoring and logging to troubleshoot across services.

## Credits

* [1][What are microservices?](https://www.ibm.com/topics/microservices)
* [2][Microservices on Wikipedia](https://en.wikipedia.org/wiki/Microservices)


---
