---
title: Acyclic Visitor
category: Behavioral
language: vi
tag:
 - Extensibility
---

## Mục đích

Cho phép thêm các hàm mới vào cấu trúc các lớp hiện có mà không làm ảnh hưởng đến cấu trúc của chúng, đồng thời tránh tạo ra các phụ thuộc vòng (dependency cycles) của Visitor Pattern trong Gang of Four.

## Giải thích

Ví dụ thực tế

> Chúng ta có một hệ thống các lớp modem. Những modem trong hệ thống này cần được duyệt qua bởi một thuật toán bên ngoài dựa trên một tiêu chí lọc (ví dụ như chỉ lấy các loại modem tương thích với hệ điều hành Unix hoặc DOS).

Nói đơn giản hơn

> Acyclic Visitor cho phép thêm các hàm vào các hệ thống lớp hiện có mà không cần sửa đổi cấu trúc của chúng.

Theo [WikiWikiWeb](https://wiki.c2.com/?AcyclicVisitor)

> Mẫu thiết kế Acyclic Visitor cho phép thêm các hàm mới vào các hệ thống lớp hiện có mà không ảnh hưởng đến các hệ thống đó, và không tạo ra các phụ thuộc vòng (dependency cycles) như là một phần không thể tránh của Visitor Pattern trong Gang of Four.

**Mã nguồn mẫu**

Dưới đây là cấu trúc của class `Modem`.

```java
public abstract class Modem {
  public abstract void accept(ModemVisitor modemVisitor);
}

public class Zoom extends Modem {
  ...
  @Override
  public void accept(ModemVisitor modemVisitor) {
    if (modemVisitor instanceof ZoomVisitor) {
      ((ZoomVisitor) modemVisitor).visit(this);
    } else {
      LOGGER.info("Only ZoomVisitor is allowed to visit Zoom modem");
    }
  }
}

public class Hayes extends Modem {
  ...
  @Override
  public void accept(ModemVisitor modemVisitor) {
    if (modemVisitor instanceof HayesVisitor) {
      ((HayesVisitor) modemVisitor).visit(this);
    } else {
      LOGGER.info("Only HayesVisitor is allowed to visit Hayes modem");
    }
  }
}
```

Tiếp theo, là cấu trúc của lớp `ModemVisitor`.

```java
public interface ModemVisitor {
}

public interface HayesVisitor extends ModemVisitor {
  void visit(Hayes hayes);
}

public interface ZoomVisitor extends ModemVisitor {
  void visit(Zoom zoom);
}

public interface AllModemVisitor extends ZoomVisitor, HayesVisitor {
}

public class ConfigureForDosVisitor implements AllModemVisitor {
  ...
  @Override
  public void visit(Hayes hayes) {
    LOGGER.info(hayes + " used with Dos configurator.");
  }
  @Override
  public void visit(Zoom zoom) {
    LOGGER.info(zoom + " used with Dos configurator.");
  }
}

public class ConfigureForUnixVisitor implements ZoomVisitor {
  ...
  @Override
  public void visit(Zoom zoom) {
    LOGGER.info(zoom + " used with Unix configurator.");
  }
}
```

Cuối cùng, chúng ta sẽ thấy các lớp visitor hoạt động.

```java
    var conUnix = new ConfigureForUnixVisitor();
    var conDos = new ConfigureForDosVisitor();
    var zoom = new Zoom();
    var hayes = new Hayes();
    hayes.accept(conDos);
    zoom.accept(conDos);
    hayes.accept(conUnix);
    zoom.accept(conUnix);   
```

Kết quả khi chạy chương trình:

```
    // Hayes modem used with Dos configurator.
    // Zoom modem used with Dos configurator.
    // Only HayesVisitor is allowed to visit Hayes modem
    // Zoom modem used with Unix configurator.
```

## Sơ đồ lớp
![alt text](../../../acyclic-visitor/etc/acyclic-visitor.png "Acyclic Visitor")

## Áp dụng
Mẫu thiết kế này có thể được sử dụng trong các trường hợp:

* Khi bạn cần thêm một hàm mới vào một hệ thống lớp hiện có mà không cần phải sửa đổi hoặc ảnh hưởng đến hệ thống đó.
* Khi có các hàm thao tác trên một hệ thống lớp nhưng những hàm này không nên thuộc về cùng cấu trúc của hệ thống đó. Ví dụ như vấn đề ConfigureForDOS / ConfigureForUnix / ConfigureForX.
* Khi bạn cần thực hiện các thao tác hoàn toàn khác nhau trên một đối tượng dựa vào kiểu của nó.
* Khi hệ thống lớp được duyệt sẽ thường xuyên được mở rộng với các lớp dẫn xuất mới của lớp Element.
* Khi việc biên dịch lại, liên kết lại, kiểm thử lại hoặc phân phối lại các lớp dẫn xuất của Element rất tốn kém.

## Hướng dẫn

* [Ví dụ về mẫu thiết kế Acyclic Visitor](https://codecrafter.blogspot.com/2012/12/the-acyclic-visitor-pattern.html)

## Kết luận

Mặt tích cực:

* Không có phụ thuộc vòng giữa các hệ thống lớp.
* Không cần phải biên dịch lại tất cả các lớp visitor nếu có một lớp mới được thêm vào.
* Không gây ra lỗi biên dịch ở các lớp visitor hiện có nếu hệ thống lớp có một thành viên mới.

Mặt tiêu cực:

* Vi phạm [Nguyên tắc thay thế của Liskov](https://java-design-patterns.com/principles/#liskov-substitution-principle) bằng cách cho thấy rằng nó có thể chấp nhận tất cả các lớp visitor nhưng thực sự chỉ quan tâm đến một số lớp visitor cụ thể.
* Phải tạo ra một cấu trúc song song các lớp visitor cho tất cả các thành viên trong hệ thống lớp có thể được duyệt.

## Các mẫu liên quan

* [Visitor Pattern](https://java-design-patterns.com/patterns/visitor/)

## Người đóng góp

* [Acyclic Visitor bởi Robert C. Martin](http://condor.depaul.edu/dmumaugh/OOT/Design-Principles/acv.pdf)
* [Acyclic Visitor trên WikiWikiWeb](https://wiki.c2.com/?AcyclicVisitor)
