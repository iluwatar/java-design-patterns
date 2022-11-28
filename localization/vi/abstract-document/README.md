---
title: Abstract Document
category: Structural
language: vi
tag: 
 - Extensibility
---

## Mục tiêu

Tận dụng thuộc tính và khả năng linh động của các ngôn ngữ không định kiểu trong khi vẫn giữ được đặc tính an toàn kiểu.

## Giải thích

Tài Liệu Trừu Tượng (thuật ngữ tiếng Anh: Abstract Document) là mẫu cho phép sử dụng các thuộc tính bổ sung, không tĩnh.
Mẫu này sử dụng khái niệm "đặc tính" (thuật ngữ gốc: "trait") để cho phép an toàn kiểu và phân tách thuộc tính của các lớp (class) khác nhau thành các tập giao diện (interface).

Ví dụ thực tế

>  Chiếc ô tô có thể bao gồm nhiều thành phần. Tuy nhiên, chúng ta không biết liệu một chiếc ô tô cụ thể có thể chứa những thành phần nào, tất cả hay chỉ một vài thành phần nhất định. Có thể nói rằng, những chiếc ô tô này là động và rất linh hoạt.

Một cách đơn giản

> Mẫu tài liệu trừu tượng cho phép cấy thêm thuộc tính vào đối tượng mà không để cho nó biết về điều đó.

Wikipedia viết (tạm dịch)

> Là mẫu hướng đối tượng loại cấu trúc dùng để tổ chức các đối tượng theo dạng lưu trữ khóa-giá trị và phơi bày dữ liệu dưới dạng định kiểu. Mục đích của mẫu này là để đạt được khả năng linh hoạt cao giữa các thành phần trong ngôn ngữ định kiểu mạnh, nơi mà những thuộc tính mới có thể được thêm vào cây đối tượng một cách linh hoạt (trong khi chạy chương trình), nhưng vẫn giữ được sự hỗ trợ của an toàn kiểu. Mẫu này sử dụng khái niệm "đặc tính" (thuật ngữ gốc: "trait") để phân tách thuộc tính của các lớp (class) thành các tập giao diện (interface).


**Chương trình ví dụ**

Đầu tiên, định nghĩa lớp cơ sở `Document` và `AbstractDocument`. 
2 lớp này định nghĩa đối tượng sẽ giữ bảng thuộc tính và các đối tượng con có kiểu bất kì. 

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

Tiếp theo, chúng ta định nghĩa một enum `Property` và một tập các interface cho thuộc tính: `type`, `price`, `model`, `parts`.
Điều này cho phép ta tạo ra một giao diện tĩnh cho lớp `Car`.

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

Bây giờ, chúng ta đã sẵn sàng cho lớp `Car`.

```java
public class Car extends AbstractDocument implements HasModel, HasPrice, HasParts {

  public Car(Map<String, Object> properties) {
    super(properties);
  }
}
```

Và cuối cùng là cách khởi tạo và sử dụng `Car` trong một ví dụ hoàn chỉnh. 

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

    // Constructing parts and car
    // Here is our car:
    // model: 300SL
    // price: 10000
    // parts: 
    // wheel/15C/100
    // door/Lambo/300
```

## Sơ đồ lớp

![alt text](../../../abstract-document/etc/abstract-document.png "Abstract Document Traits and Domain")

## Ứng dụng

Sử dụng mẫu Tài Liệu Trừu Tượng khi

* Có nhu cầu thêm các thuộc tính mới trong lúc chạy (khái niệm gốc: "on the fly")
* Muốn có khả năng linh hoạt trong tổ chức nghiệp vụ dưới dạng sơ đồ cây
* Muốn hệ thống lỏng lẻo hơn

## Tham khảo

* [Wikipedia: Abstract Document Pattern](https://en.wikipedia.org/wiki/Abstract_Document_Pattern)
* [Martin Fowler: Dealing with properties](http://martinfowler.com/apsupp/properties.pdf)
* [Pattern-Oriented Software Architecture Volume 4: A Pattern Language for Distributed Computing (v. 4)](https://www.amazon.com/gp/product/0470059028/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0470059028&linkId=e3aacaea7017258acf184f9f3283b492)
