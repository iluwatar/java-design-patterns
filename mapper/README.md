---
layout: pattern
title: Mapper
folder: mapper
permalink: /patterns/mapper/
categories: Creational
language: en
tags:
 - Gang of Four
---

## Intent

To set up communications between two subsystems that still need to stay ignorant of each other.


## Explanation

Real world example

> The customer want to lease an object. It will use the pricing mapper
> to make a communication with pricing package.

In plain words

> To let two subsystems communicate with each other with ignorance.

**Programmatic Example**

Say you want to lease a product from the product repository. But you don't want to operate the whole repository. Then you can call the mapper to execute the operation.
```java
public Product lease(){
  return mapper.leaseProduct();
}
```
Then for the mapper, it needs to return a product and call the repository that the product has been leased.
```java
public Product leaseProduct(){
  Product product = repository.searchProduct();
  repository.setLeased(product);
  return product;
}
```
When the product is returned, mapper needs to return a product and call the repository to update.
```java
public void returnProduct(){
  repository.setReturned(product);
}
```

## Class diagram

![alt text](./etc/mapper.urm.png "Mapper pattern class diagram")

## Applicability

Use the Mapper pattern when

* you need to ensure that neither subsystem has a dependency on the interaction.

## Credits

* [Patterns of Enterprise Application Architecture: Pattern Enterpr Applica Arch](https://books.google.fi/books?id=vqTfNFDzzdIC&pg=PA473#v=onepage&q&f=false)
