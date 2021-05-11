---
layout: pattern
title: Data Transfer Object
folder: data-transfer-object
permalink: /patterns/data-transfer-object/zh
categories: Architectural
language: zh
tags:
 - Performance
---

## 目的

次将具有多个属性的数据从客户端传递到服务器，以避免多次调用远程服务器。

## 解释

真实世界例子

> 我们需要从远程数据库中获取有关客户的信息。 我们不使用一次查询一个属性，而是使用DTO一次传送所有相关属性。

通俗的说

> 使用DTO，可以通过单个后端查询获取相关信息。

维基百科说

> 在编程领域，数据传输对象（DTO）是在进程之间承载数据的对象。 使用它的动机是，通常依靠远程接口（例如Web服务）来完成进程之间的通信，在这种情况下，每个调用都是昂贵的操作。
> 
> 因为每个（方法）调用的大部分成本与客户端和服务器之间的往返时间有关，所以减少调用数量的一种方法是使用一个对象（DTO）来聚合将要在多次调用间传输的数据，但仅由一个调用提供。

**程序示例**

让我们来介绍我们简单的`CustomerDTO` 类

```java
public class CustomerDto {
  private final String id;
  private final String firstName;
  private final String lastName;

  public CustomerDto(String id, String firstName, String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }
}
```

`CustomerResource` 类充当客户信息的服务器。

```java
public class CustomerResource {
  private final List<CustomerDto> customers;

  public CustomerResource(List<CustomerDto> customers) {
    this.customers = customers;
  }

  public List<CustomerDto> getAllCustomers() {
    return customers;
  }

  public void save(CustomerDto customer) {
    customers.add(customer);
  }

  public void delete(String customerId) {
    customers.removeIf(customer -> customer.getId().equals(customerId));
  }
}
```

现在拉取客户信息变得简单自从我们有了DTOs。

```java
    var allCustomers = customerResource.getAllCustomers();
    allCustomers.forEach(customer -> LOGGER.info(customer.getFirstName()));
    // Kelly
    // Alfonso
```

## 类图

![alt text](../../data-transfer-object/etc/data-transfer-object.urm.png "data-transfer-object")

## 适用性

使用数据传输对象模式当

* 客户端请求多种信息。信息都是相关的
* 当你想提高获取资源的性能
* 你想降低远程方法调用的次数

## 鸣谢

* [Design Pattern - Transfer Object Pattern](https://www.tutorialspoint.com/design_pattern/transfer_object_pattern.htm)
* [Data Transfer Object](https://msdn.microsoft.com/en-us/library/ff649585.aspx)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=f27d2644fbe5026ea448791a8ad09c94)
* [Patterns of Enterprise Application Architecture](https://www.amazon.com/gp/product/0321127420/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321127420&linkCode=as2&tag=javadesignpat-20&linkId=014237a67c9d46f384b35e10151956bd)
