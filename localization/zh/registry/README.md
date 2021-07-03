---
layout: pattern
title: Registry
folder: registry
permalink: /patterns/registry/zh
categories: Creational
language: zh
tags:
 - Instantiation
---

## 意图
存储单个类的对象并提供对它们的全局访问点。
类似于 Multiton 模式，唯一的区别是在注册表中没有对象数量的限制。

## 解释

用简单的话说

> Registry 是一个众所周知的对象，其他对象可以使用它来查找公共对象和服务。

**程序示例**
下面是一个“客户”类
```java
public class Customer {

  private final String id;
  private final String name;

  public Customer(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

}
```

`Customer` 对象的这个注册表是 `CustomerRegistry`
```java
public final class CustomerRegistry {

  private static final CustomerRegistry instance = new CustomerRegistry();

  public static CustomerRegistry getInstance() {
    return instance;
  }

  private final Map<String, Customer> customerMap;

  private CustomerRegistry() {
    customerMap = new ConcurrentHashMap<>();
  }

  public Customer addCustomer(Customer customer) {
    return customerMap.put(customer.getId(), customer);
  }

  public Customer getCustomer(String id) {
    return customerMap.get(id);
  }

}
```

## 类图
![Registry](./etc/registry.png)

## 适用性
使用注册表模式时

* 客户端需要某个对象的引用，因此客户端可以在对象的注册表中查找该对象。

## 结果
大量庞大的对象添加到注册表会导致大量内存消耗，因为注册表中的对象不会被垃圾收集。

## 鸣谢
* https://www.martinfowler.com/eaaCatalog/registry.html
* https://wiki.c2.com/?RegistryPattern
