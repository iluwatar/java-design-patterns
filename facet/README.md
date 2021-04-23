---
layout: pattern
title: Facet
folder: facet
permalink: /patterns/facet/
categories: Creational
tags:
 - Gang of Four
---

## Intent

Use facet pattern to realize security, in order to ensure some privillage only can be granted to some certain users.

## Explanation

Real world example

> Lets say if some client can only be able to read information of an object, then the client should be provided with a read-only facet and for write operation should be forbiddened.

Wikipedia says

> Facets are used as a security pattern in CapabilityOrientedProgramming, in order to satisfy the PrincipleOfLeastAuthority.

**Programmatic Example**

For a facet, use method to create, query the registered class and set the registered class.
```java
public abstract class Facet {
  public static Facet create();
  public static Class[] query(Facet f, Class[] interfaces);
  public static Facet narrow(Facet f, Class[] interfaces);
} 
```
For a sentry, use method to connect the facet and context to make request.
```java
public interface Sentry {
  public abstract boolean execute(User user, Class interfaceClass);
}
```
For the context, it abstracts the logic for make access control decisions on behalf of a Facet.
```java
public interface Context {
  public boolean validateInterface(Class interfaceClass);
}
```
After validation, execute the method.
```java
public interface SecurityMethods {
  public static String delegate(User user);
}
```

## Class Diagram

![alt text](./etc/facet.urm.png "Facet pattern class diagram")

## Applicability

Use the Facet pattern when you care about the privilege for different user.

* restrict an interface to obtain a smaller interface that provides less authority.

## Real world examples

* [java.sql.Connection](https://docs.oracle.com/javase/8/docs/api/java/sql/Connection.html)