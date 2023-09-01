---
title: Adapter
category: Structural
language: vi
tag:
 - Gang of Four
---

## Còn được gọi là
Wrapper

## Mục đích
Chuyển đổi giao diện của một lớp thành một giao diện khác mà mình mong muốn. Adapter cho phép các lớp làm việc cùng nhau, mặc dù ban đầu chúng không thể làm việc cùng nhau do giao diện không tương thích.

## Giải thích

Ví dụ thực tế

> Hãy tưởng tượng rằng bạn có một số hình ảnh trên thẻ nhớ của bạn và bạn cần chuyển chúng sang máy tính của bạn. Để chuyển chúng, bạn cần một loại adapter nào đó tương thích với các cổng máy tính của bạn để bạn có thể kết nối thẻ nhớ vào máy tính của bạn. Trong trường hợp này, đầu đọc thẻ là một cái adapter.
> Ví dụ nổi tiếng khác là adapter điện; một phích cắm ba chân không thể kết nối vào một ổ cắm hai chân, nó cần sử dụng một adapter điện để làm cho nó tương thích với ổ cắm hai chân.
> Một ví dụ khác là thông dịch viên dịch lời của người này cho người khác hiểu được.

## Nói đơn giản hơn

> Mẫu Adapter cho phép bạn bọc một đối tượng ban đầu không tương thích vào một adapter để làm cho nó tương thích với một lớp khác.

Theo Wikipedia

> Trong kỹ thuật phần mềm, mẫu thiết kế Adapter là một mẫu thiết kế phần mềm cho phép giao diện của một lớp hiện có được sử dụng như một giao diện khác. Nó thường được sử dụng để làm cho các lớp hiện có làm việc với các lớp khác mà không cần sửa đổi mã nguồn của chúng.

**Mã nguồn mẫu**

Hãy xem xét một thuyền trưởng chỉ có thể sử dụng thuyền chèo và không thể thả neo.

Đầu tiên, chúng ta có các giao diện `RowingBoat` và `FishingBoat`

```java
public interface RowingBoat {
  void row();
}

@Slf4j
public class FishingBoat {
  public void sail() {
    LOGGER.info("The fishing boat is sailing");
  }
}
```

Và thuyền trưởng mong đợi một lớp là triển khai của giao diện `RowingBoat` mà có thể di chuyển được

```java
public class Captain {

  private final RowingBoat rowingBoat;
  // default constructor and setter for rowingBoat
  public Captain(RowingBoat rowingBoat) {
    this.rowingBoat = rowingBoat;
  }

  public void row() {
    rowingBoat.row();
  }
}
```

Bây giờ hãy nói rằng hải tặc đang đến và thuyền trưởng của chúng ta cần trốn thoát nhưng chỉ có một thuyền câu sẵn có. Chúng ta cần tạo một adapter cho phép thuyền trưởng vận hành thuyền câu với kỹ năng chèo của mình.

```java
@Slf4j
public class FishingBoatAdapter implements RowingBoat {

  private final FishingBoat boat;

  public FishingBoatAdapter() {
    boat = new FishingBoat();
  }

  @Override
  public void row() {
    boat.sail();
  }
}
```

Và bây giờ `Captain` có thể sử dụng `FishingBoat` để trốn thoát khỏi hải tặc.

```java
var captain = new Captain(new FishingBoatAdapter());
captain.row();
```

## Sơ đồ lớp
![alt text](../../../adapter/etc/adapter.urm.png "Adapter class diagram")

## Áp dụng
Sử dụng mẫu Adapter khi:

* Bạn muốn sử dụng một lớp hiện có, và giao diện của nó không phù hợp với giao diện bạn cần
* Bạn muốn tạo một lớp có thể tái sử dụng làm việc với các lớp không liên quan hoặc không thể dự đoán trước, tức là các lớp không nhất thiết phải có giao diện tương thích
* Bạn cần sử dụng nhiều lớp con hiện có, nhưng việc điều chỉnh giao diện của chúng thông qua việc kế thừa là không hiệu quả. Một đối tượng adapter có thể điều chỉnh giao diện của lớp cha của nó.
* Hầu hết các ứng dụng sử dụng thư viện bên ngoài sử dụng adapter như một lớp trung gian giữa ứng dụng và thư viện bên ngoài. Nếu muốn sử dụng thư viện khác, chỉ cần tạo một adapter cho thư viện mới mà không cần thay đổi mã nguồn của ứng dụng.

## Hướng dẫn

* [Dzone](https://dzone.com/articles/adapter-design-pattern-in-java)
* [Refactoring Guru](https://refactoring.guru/design-patterns/adapter/java/example)
* [Baeldung](https://www.baeldung.com/java-adapter-pattern)

## Kết luận 

Lớp adapter và đối tượng adapter có những lợi ích và khả năng thích ứng khác nhau. Một lớp adapter

* Chuyển đổi một lớp Adaptee để phù hợp với Target bằng cách ràng buộc với một lớp Adaptee cụ thể. Kết quả là, khi chúng ta muốn thích ứng một lớp và tất cả các lớp con của nó, thì lớp adapter sẽ không hoạt động.
* Cho phép Adapter ghi đè một số hành vi của Adaptee vì Adapter là một lớp con của Adaptee.
* Chỉ giới thiệu một đối tượng và không cần thêm con trỏ để truy cập gián tiếp đến Adaptee.

Một đối tượng Adapter 

* Cho phép một Adapter làm việc với nhiều Adaptees, tức là chính Adaptee và tất cả các lớp con của nó (nếu có). Adapter cũng có thể thêm chức năng cho tất cả Adaptee cùng một lúc.
* Làm cho việc ghi đè hành vi của Adaptee trở nên khó khăn hơn. Nó sẽ yêu cầu việc kế thừa Adaptee và làm cho Adapter tham chiếu đến lớp con thay vì chính Adaptee.

## Ví dụ trong thực tế

* [java.util.Arrays#asList()](http://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#asList%28T...%29)
* [java.util.Collections#list()](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#list-java.util.Enumeration-)
* [java.util.Collections#enumeration()](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#enumeration-java.util.Collection-)
* [javax.xml.bind.annotation.adapters.XMLAdapter](http://docs.oracle.com/javase/8/docs/api/javax/xml/bind/annotation/adapters/XmlAdapter.html#marshal-BoundType-)

## Người đóng góp

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
