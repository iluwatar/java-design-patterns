---
标题：摘要文件
类别：结构
语言：en
标签：
 - 可扩展性
---

## 意图

使用动态属性并实现无类型语言的灵活性，同时保持类型安全。

＃＃ 解释

抽象文档模式可以处理额外的非静态属性。这种模式
使用特征的概念来实现类型安全并将不同类的属性分离到
一组接口。

现实世界的例子
> 考虑一辆由多个零件组成的汽车。但是，我们不知道具体的汽车是否真的拥有所有零件，或者只是其中的一部分。我们的汽车充满活力且极其灵活。

简单来说

> 抽象文档模式允许在对象不知道的情况下将属性附加到对象。

维基百科说

> 一种面向对象的结构设计模式，用于在松散类型的键值存储中组织对象并公开
使用类型化视图的数据。模式的目的是实现组件之间的高度灵活性
在强类型语言中，可以动态地将新属性添加到对象树中，而不会丢失
支持类型安全。该模式利用特征将一个类的不同属性分成不同的
接口
**程序示例**

让我们首先定义基类 `Document` 和 `AbstractDocument`。他们基本上使对象拥有一个属性
地图和任意数量的子对象。

```java
public interface Document {

  Void put(String key, Object value);

  Object get(String key);

  <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor);
}

public abstract class AbstractDocument implements Document {

  private final Map<String, Object> properties;

  protected AbstractDocument(Map<String, Object> properties) {
    Objects.requireNonNull(properties, "properties map is required");
    this.properties = properties;
  }

  @Override
  public Void put(String key, Object value) {
    properties.put(key, value);
    return null;
  }

  @Override
  public Object get(String key) {
    return properties.get(key);
  }

  @Override
  public <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor) {
    return Stream.ofNullable(get(key))
        .filter(Objects::nonNull)
        .map(el -> (List<Map<String, Object>>) el)
        .findAny()
        .stream()
        .flatMap(Collection::stream)
        .map(constructor);
  }
  ...
}
```
接下来我们定义一个枚举“Property”和一组用于类型、价格、型号和零件的接口。这允许我们创建
我们的“汽车”类的静态界面。

```java
public enum Property {

  PARTS, TYPE, PRICE, MODEL
}

public interface HasType extends Document {

  default Optional<String> getType() {
    return Optional.ofNullable((String) get(Property.TYPE.toString()));
  }
}

public interface HasPrice extends Document {

  default Optional<Number> getPrice() {
    return Optional.ofNullable((Number) get(Property.PRICE.toString()));
  }
}
public interface HasModel extends Document {

  default Optional<String> getModel() {
    return Optional.ofNullable((String) get(Property.MODEL.toString()));
  }
}

public interface HasParts extends Document {

  default Stream<Part> getParts() {
    return children(Property.PARTS.toString(), Part::new);
  }
}
```

现在我们准备介绍“汽车”。

```java
public class Car extends AbstractDocument implements HasModel, HasPrice, HasParts {

  public Car(Map<String, Object> properties) {
    super(properties);
  }
}
```

最后，这是我们如何在完整示例中构建和使用“汽车”的方式。
```java
    LOGGER.info("Constructing parts and car");

    var wheelProperties = Map.of(
        Property.TYPE.toString(), "wheel",
        Property.MODEL.toString(), "15C",
        Property.PRICE.toString(), 100L);

    var doorProperties = Map.of(
        Property.TYPE.toString(), "door",
        Property.MODEL.toString(), "Lambo",
        Property.PRICE.toString(), 300L);

    var carProperties = Map.of(
        Property.MODEL.toString(), "300SL",
        Property.PRICE.toString(), 10000L,
        Property.PARTS.toString(), List.of(wheelProperties, doorProperties));

    var car = new Car(carProperties);

    LOGGER.info("Here is our car:");
    LOGGER.info("-> model: {}", car.getModel().orElseThrow());
    LOGGER.info("-> price: {}", car.getPrice().orElseThrow());
    LOGGER.info("-> parts: ");
    car.getParts().forEach(p -> LOGGER.info("\t{}/{}/{}",
        p.getType().orElse(null),
        p.getModel().orElse(null),
        p.getPrice().orElse(null))
    );

    // 构建零件和汽车
    // 这是我们的车：
    // 型号：300SL
    // 价格：10000
    // 部分：
    // 轮子/15C/100
    // 门/兰博/300
```

##类图

![alt text](./etc/abstract-document.png "抽象文档特征和域")

## 适用性

在以下情况下使用抽象文档模式

* 需要动态添加新属性
*您想要一种灵活的方式来组织树状结构中的域
* 你想要更松耦合的系统

## 学分

* [维基百科：抽象文档模式](https://en.wikipedia.org/wiki/Abstract_Document_Pattern)
* [Martin Fowler：处理属性](http://martinfowler.com/apsupp/properties.pdf)
* [面向模式的软件架构第 4 卷：分布式计算的模式语言 (v. 4)](https://www.amazon.com/gp/product/0470059028/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative =9325&linkCode=as2&creativeASIN=0470059028&linkId=e3aacaea7017258acf184f9f3283b492)
