---
title: Anti-corruption layer
category: Architectural
language: en
tag:
  - Cloud distributed
  - Decoupling
  - Microservices
---

## Intent

Implement a façade or adapter layer between different subsystems that don't share the same semantics. 
This layer translates requests that one subsystem makes to the other subsystem. 
Use this pattern to ensure that an application's design is not limited by dependencies on outside subsystems. 
This pattern was first described by Eric Evans in Domain-Driven Design.

## Explanation

### Context and problem
Most applications rely on other systems for some data or functionality. 
For example, when a legacy application is migrated to a modern system, 
it may still need existing legacy resources. 
New features must be able to call the legacy system. 
This is especially true of gradual migrations, 
where different features of a larger application are moved to a modern system over time.

Often these legacy systems suffer from quality issues such as convoluted data schemas or obsolete APIs. 
The features and technologies used in legacy systems can vary widely from more modern systems. 
To interoperate with the legacy system, the new application may need to support outdated infrastructure, protocols, data models, APIs, 
or other features that you wouldn't otherwise put into a modern application.

Maintaining access between new and legacy systems can force the new system to adhere to at least some of the legacy system's APIs or other semantics. 
When these legacy features have quality issues, supporting them "corrupts" what might otherwise be a cleanly designed modern application.
Similar issues can arise with any external system that your development team doesn't control, not just legacy systems.

### Solution
Isolate the different subsystems by placing an anti-corruption layer between them. 
This layer translates communications between the two systems, allowing one system to remain unchanged 
while the other can avoid compromising its design and technological approach.

### Programmatic example
#### Introduction
The example shows why the anti-corruption layer is needed.
Here are 2 shop-ordering systems: `Legacy` and `Modern`. \
(
*It is important to state the separation is very conditional, and is drawn for learning purposes*. 
*In reality the pattern does not depend on the so-called ageness but rather relies on the different domain models.*)

The aforementioned systems have different domain models and have to operate simultaneously.
Since they work independently the orders can come either from the `Legacy` or `Modern` system.
Therefore, the system that receives the legacyOrder needs to check if the legacyOrder is valid and not present in the other system.
Then it can place the legacyOrder in its own system.

But for that, the system needs to know the domain model of the other system and to avoid that, 
the anti-corruption layer(ACL) is introduced. 
The ACL is a layer that translates the domain model of the `Legacy` system to the domain model of the `Modern` system and vice versa.
Also, it hides all other operations with the other system, uncoupling the systems.

#### Domain model of the `Legacy` system
```java
public class LegacyOrder {
    private String id;
    private String customer;
    private String item;
    private String qty;
    private String price;
}
```
#### Domain model of the `Modern` system
```java
public class ModernOrder {
    private String id;
    private Customer customer;

    private Shipment shipment;

    private String extra;
}
public class Customer {
    private String address;
}
public class Shipment {
    private String item;
    private String qty;
    private String price;
}
```
#### Anti-corruption layer
```java
public class AntiCorruptionLayer {

    @Autowired
    private ModernShop modernShop;

    @Autowired
    private LegacyShop legacyShop;

    public Optional<LegacyOrder> findOrderInModernSystem(String id) {
        return modernShop.findOrder(id).map(o -> /* map to legacyOrder*/);
    }

    public Optional<ModernOrder> findOrderInLegacySystem(String id) {
        return legacyShop.findOrder(id).map(o -> /* map to modernOrder*/);
    }

}
```
#### The connection
Wherever the `Legacy` or `Modern` system needs to communicate with the counterpart 
the ACL needs to be used to avoid corrupting the current domain model.
The example below shows how the `Legacy` system places an order with a validation from the `Modern` system.
```java
public class LegacyShop {
    @Autowired
    private AntiCorruptionLayer acl;

    public void placeOrder(LegacyOrder legacyOrder) throws ShopException {

        String id = legacyOrder.getId();

        Optional<LegacyOrder> orderInModernSystem = acl.findOrderInModernSystem(id);

        if (orderInModernSystem.isPresent()) {
            // order is already in the modern system
        } else {
            // place order in the current system
        }
    }
}
```

### Issues and considerations
 - The anti-corruption layer may add latency to calls made between the two systems.
 - The anti-corruption layer adds an additional service that must be managed and maintained.
 - Consider how your anti-corruption layer will scale.
 - Consider whether you need more than one anti-corruption layer. You may want to decompose functionality into multiple services using different technologies or languages, or there may be other reasons to partition the anti-corruption layer.
 - Consider how the anti-corruption layer will be managed in relation with your other applications or services. How will it be integrated into your monitoring, release, and configuration processes?
 - Make sure transaction and data consistency are maintained and can be monitored.
 - Consider whether the anti-corruption layer needs to handle all communication between different subsystems, or just a subset of features.
 - If the anti-corruption layer is part of an application migration strategy, consider whether it will be permanent, or will be retired after all legacy functionality has been migrated.
 - This pattern is illustrated with distinct subsystems above, but can apply to other service architectures as well, such as when integrating legacy code together in a monolithic architecture.

## Applicability

Use this pattern when:

 - A migration is planned to happen over multiple stages, but integration between new and legacy systems needs to be maintained.
 - Two or more subsystems have different semantics, but still need to communicate.

This pattern may not be suitable if there are no significant semantic differences between new and legacy systems.

## Tutorials

* [Microsoft - Anti-Corruption Layer](https://learn.microsoft.com/en-us/azure/architecture/patterns/anti-corruption-layer)
* [Amazon - Anti-Corruption Layer](https://docs.aws.amazon.com/prescriptive-guidance/latest/cloud-design-patterns/acl.html)

## Credits

* [Domain-Driven Design. Eric Evans](https://www.amazon.com/Domain-Driven-Design-Tackling-Complexity-Software/dp/0321125215)