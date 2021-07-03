---
layout: pattern
title: Parameter Object
folder: parameter-object
permalink: /patterns/parameter-object/zh
categories: Behavioral
language: zh
tags:
 - Extensibility
---

## 目的

Java 语言的语法不允许您声明具有预定义值的方法
对于一个参数。在 Java 中实现默认方法参数的最佳选择可能是
通过使用方法重载。方法重载允许您声明多个方法
具有相同的名称但具有不同数量的参数。但主要问题是
方法重载作为默认参数值的解决方案
接受多个参数。为每种可能的组合创建重载方法
参数可能很麻烦。为了解决这个问题，使用了参数对象模式。

## 解释

参数对象只是一个方法的所有参数的包装对象。
它只不过是一个普通的 POJO。参数对象优于
常规方法参数列表是类字段可以具有默认值的事实。
一旦为方法参数列表创建了包装器类，相应的构建器类
也被创建。通常它是一个内部静态类。最后一步是使用构建器
构造一个新的参数对象。对于那些被跳过的参数，
将使用它们的默认值。


**程序示例**

这是简单的“SearchService”类，其中方法重载用于此处的默认值。要使用方法重载，参数的数量或参数类型必须不同。
```java
public class SearchService {
  //Method Overloading example. SortOrder is defaulted in this method
  public String search(String type, String sortBy) {
    return getQuerySummary(type, sortBy, SortOrder.DESC);
  }

  /* Method Overloading example. SortBy is defaulted in this method. Note that the type has to be 
  different here to overload the method */
  public String search(String type, SortOrder sortOrder) {
    return getQuerySummary(type, "price", sortOrder);
  }

  private String getQuerySummary(String type, String sortBy, SortOrder sortOrder) {
    return "Requesting shoes of type \"" + type + "\" sorted by \"" + sortBy + "\" in \""
        + sortOrder.getValue() + "ending\" order...";
  }
}

```

接下来，我们展示了使用 Builder 模式创建的带有 `ParameterObject` 的 `SearchService`。

```java
public class SearchService {

  /* Parameter Object example. Default values are abstracted into the Parameter Object 
  at the time of Object creation */
  public String search(ParameterObject parameterObject) {
    return getQuerySummary(parameterObject.getType(), parameterObject.getSortBy(),
        parameterObject.getSortOrder());
  }
  
  private String getQuerySummary(String type, String sortBy, SortOrder sortOrder) {
    return "Requesting shoes of type \"" + type + "\" sorted by \"" + sortBy + "\" in \""
        + sortOrder.getValue() + "ending\" order...";
  }
}

public class ParameterObject {
  public static final String DEFAULT_SORT_BY = "price";
  public static final SortOrder DEFAULT_SORT_ORDER = SortOrder.ASC;

  private String type;
  private String sortBy = DEFAULT_SORT_BY;
  private SortOrder sortOrder = DEFAULT_SORT_ORDER;

  private ParameterObject(Builder builder) {
    type = builder.type;
    sortBy = builder.sortBy != null && !builder.sortBy.isBlank() ? builder.sortBy : sortBy;
    sortOrder = builder.sortOrder != null ? builder.sortOrder : sortOrder;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  //Getters and Setters...

  public static final class Builder {

    private String type;
    private String sortBy;
    private SortOrder sortOrder;

    private Builder() {
    }

    public Builder withType(String type) {
      this.type = type;
      return this;
    }

    public Builder sortBy(String sortBy) {
      this.sortBy = sortBy;
      return this;
    }

    public Builder sortOrder(SortOrder sortOrder) {
      this.sortOrder = sortOrder;
      return this;
    }

    public ParameterObject build() {
      return new ParameterObject(this);
    }
  }
}


```

## 类图

![alt text](./etc/parameter-object.png "Parameter Object")

## 适用性

这种模式向我们展示了在 Java 中为方法设置默认参数的方式，因为该语言没有开箱即用的默认参数功能。

## 鸣谢

- [Does Java have default parameters?](http://dolszewski.com/java/java-default-parameters)
