---
layout: pattern
title: Data Transfer Hash
folder: data-transfer-hash
permalink: /patterns/data-transfer-hash/
categories: Architectural
language: en
tags:
 - Structural
 - Decoupling
---

## Intent

Pass data with the help of a data transfer hash object 
to reduce coupling between objects communicating with DTOs.

## Explanation

In plain words
> The Data Transfer Hash Pattern aims to reduce coupling between objects communicating
 between DTOs with the help of a data transfer hash object, which enables DTOs to send
 data and receive data using well-known keys.

> Data transfer hashes may be implemented as simple hash tables (or HashMaps).
 A more robust implementation uses a container object to hold the hash, as well as identifying information, type-safe data retrieval methods, and well-known keys.

**Programmatic Example**

Let's first introduce our simple `DataTransferHashObject` class, which is the data transfer hash object
and stores data for transport between layers as a set of values associated with well-known keys.

```java
public class DataTransferHashObject {
 private final HashMap<String, Object> hash;

 DataTransferHashObject() {
  hash = new HashMap<>();
 }

 public void put(final String key, final Object value) {
  hash.put(key, value);
 }

 public Object getValue(final String key) {
  return this.hash.get(key);
 }
}
```

`Business` class acts as business object, which is one of the two layers that is involved in data transportation.

```java
public class Business {

 public void createHash(final String k, final Object v, final DataTransferHashObject hash) {
  hash.put(k, v);
 }

 public Object getData(final String k, final DataTransferHashObject hash) {
  return hash.getValue(k);
 }
}
```

`Presentation` class acts as presentation tier, which is one of the two layers that is involved in data transportation.

```java
public class Presentation {

 public void createHash(final String k, final Object v, final DataTransferHashObject hash) {
  hash.put(k, v);
 }

 public Object getData(final String k, final DataTransferHashObject hash) {
  return hash.getValue(k);
 }
}
```

Now two layers could transport data with low coupling through the transport data hash object.

```java
public class DataTransferHashApp {
 public static void main(final String[] args) {
  var hash = new DataTransferHashObject();
  var business = new Business();
  business.createHash("Alice", "88887777", hash);
  LOGGER.info("Business object adds Alice's phone number 88887777.");
  business.createHash("Bob", "67189923", hash);
  LOGGER.info("Business object adds Bob's phone number 67189923");
  business.createHash("Trump", "33521179", hash);
  LOGGER.info("Business object adds Trump's phone number 33521179");
  var presentation = new Presentation();
  var alicePhoneNumber = presentation.getData("Alice", hash);
  LOGGER.info("Presentation tier receives Alice's phone number: " + alicePhoneNumber);
  var bobPhoneNumber = presentation.getData("Bob", hash);
  LOGGER.info("Presentation tier receives Bob's phone number: " + bobPhoneNumber);
  var trumpPhoneNumber = presentation.getData("Trump", hash);
  LOGGER.info("Presentation tier receives Trump's phone number: " + trumpPhoneNumber);
 }
}
```

## Class diagram

![alt text](./etc/data-transfer-hash.png "data-transfer-hash")

## Applicability

Use the Data Transfer Hash pattern when:

* You want to reduce coupling between objects communicating with DTOs.

## Credits

* [J2EE Design Patterns](https://www.oreilly.com/library/view/j2ee-design-patterns/0596004273/re12.html)
