---
title: Abstract Factory
category: Creational
language: vi
tag:
 - Gang of Four
---

## Còn được biết đến như

Kit

## Mục tiêu

Cung cấp giao diện (interface) để khởi tạo các đối tượng liên quan 
hoặc có phụ thuộc mà không cần chỉ định các lớp cụ thể của chúng.

## Giải thích

Nhà Máy Trừu Tượng (thuật ngữ tiếng Anh: Abstract Factory).

Ví dụ thực tế

> Để tạo ra một vương quốc, chúng ta cần các đối tượng có chủ đề chung. Vương quốc Elf cần một vị vua Elf, lâu đài Elf và quân đội Elf trong khi vương quốc Orc cần một vị vua Orc, lâu đài Orc và quân đội Orc. Các đối tượng trong vương quốc phụ thuộc lẫn nhau.

Một cách đơn giản

> Một nhà máy của nhiều nhà máy; một nhà máy nhóm các nhà máy riêng lẻ nhưng có liên quan hoặc phụ thuộc lại với nhau mà không chỉ định các lớp cụ thể của chúng.

Wikipedia viết (tạm dịch)

> Mẫu Nhà Máy Trừu Tượng cung cấp cách làm để đóng gói một nhóm các nhà máy riêng lẻ có chủ đề chung mà không cần chỉ định các lớp cụ thể của chúng

**Chương trình ví dụ**

Sử dụng ví dụ về vương quốc đã nêu ở trên. 
Trước hết, chúng ta có một số giao diện (interface) và lớp (class) được hiện thực hóa cho các đối tượng trong vương quốc.

```java
public interface Castle {
  String getDescription();
}

public interface King {
  String getDescription();
}

public interface Army {
  String getDescription();
}

// Hiện thực hóa tộc Elf ->
public class ElfCastle implements Castle {
  static final String DESCRIPTION = "This is the elven castle!";
  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
public class ElfKing implements King {
  static final String DESCRIPTION = "This is the elven king!";
  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
public class ElfArmy implements Army {
  static final String DESCRIPTION = "This is the elven Army!";
  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}

// Tương tự, hiện thực hóa tộc Orc -> ...

```

Sau đó, chúng ta có trừu tượng hóa và hiện thực hóa cho nhà máy vương quốc.

```java
public interface KingdomFactory {
  Castle createCastle();
  King createKing();
  Army createArmy();
}

public class ElfKingdomFactory implements KingdomFactory {

  @Override
  public Castle createCastle() {
    return new ElfCastle();
  }

  @Override
  public King createKing() {
    return new ElfKing();
  }

  @Override
  public Army createArmy() {
    return new ElfArmy();
  }
}

public class OrcKingdomFactory implements KingdomFactory {

  @Override
  public Castle createCastle() {
    return new OrcCastle();
  }

  @Override
  public King createKing() {
    return new OrcKing();
  }
  
  @Override
  public Army createArmy() {
    return new OrcArmy();
  }
}
```

Bây giờ, chúng ta có Nhà Máy Trừu Tượng cho phép chúng ta tạo một nhóm các đối tượng liên quan, tức là nhà máy vương quốc Elf tạo ra lâu đài, vua và quân đội của tộc Elf, v.v.

```java
var factory = new ElfKingdomFactory();
var castle = factory.createCastle();
var king = factory.createKing();
var army = factory.createArmy();

castle.getDescription();
king.getDescription();
army.getDescription();
```

Đầu ra khi chạy chương trình:

```java
This is the elven castle!
This is the elven king!
This is the elven Army!
```

Bây giờ, chúng ta có thể thiết kế một nhà máy cho các nhà máy vương quốc khác nhau. Trong ví dụ này, chúng tôi đã tạo `FactoryMaker`, chịu trách nhiệm trả về một thể hiện của` ElfKingdomFactory` hoặc `OrcKingdomFactory`.
Người dùng có thể sử dụng `FactoryMaker` để tạo ra nhà máy mà họ mong muốn, sau đó, `FactoryMaker` sẽ tạo ra các đối tượng cụ thể (là dẫn xuất của `Army`, `King`, `Castle`).
Trong ví dụ này, chúng tôi cũng sử dụng một enum để tham số hóa các loại nhà máy vương quốc mà khách hàng sẽ yêu cầu.

```java
public static class FactoryMaker {

    public enum KingdomType {
        ELF, ORC
    }

    public static KingdomFactory makeFactory(KingdomType type) {
        return switch (type) {
            case ELF -> new ElfKingdomFactory();
            case ORC -> new OrcKingdomFactory();
            default -> throw new IllegalArgumentException("KingdomType not supported.");
        };
    }
}

    public static void main(String[] args) {
        var app = new App();

        LOGGER.info("Elf Kingdom");
        app.createKingdom(FactoryMaker.makeFactory(KingdomType.ELF));
        LOGGER.info(app.getArmy().getDescription());
        LOGGER.info(app.getCastle().getDescription());
        LOGGER.info(app.getKing().getDescription());

        LOGGER.info("Orc Kingdom");
        app.createKingdom(FactoryMaker.makeFactory(KingdomType.ORC));
        --Tương tự với nhà máy Orc
    }
```

## Sơ đồ lớp

![alt text](../../../abstract-factory/etc/abstract-factory.urm.png "Abstract Factory class diagram")

## Ứng dụng
Sử dụng mẫu Nhà Máy Trừu Tượng khi

* Hệ thống nên độc lập với cách mà nó khởi tạo/kết hợp/trình bày sản phẩm của nó.
* Hệ thống nên được cấu hình với một trong các họ sản phẩm.
* Họ của các đối tượng sản phẩm được thiết kế để sử dụng cùng nhau, và bạn cần áp đặt ràng buộc này.
* Bạn muốn cung cấp một thư viện lớp các sản phẩm, và bạn chỉ muốn tiết lộ giao diện (interface), ẩn giấu hiện thực hóa của chúng.
* Thời gian tồn tại của đối tượng phụ thuộc thì ngắn hơn thời gian tồn tại của đối tượng sử dụng.
* Bạn cần một/vài giá trị trong lúc chạy để khởi tạo một/vài đối tượng phụ thuộc cụ thể.
* Bạn muốn quyết định sản phẩm nào được gọi từ một họ trong lúc chạy.
* Bạn cần tính nhất quán giữa các sản phẩm.
* Bạn không muốn sửa mã nguồn khi thêm những sản phẩm mới hoặc họ của các sản phẩm vào chương trình.

Ví dụ về ca sử dụng

* Lựa chọn để gọi đến hiện thực hóa phù hợp của FileSystemAcmeService hoặc DatabaseAcmeService hoặc NetworkAcmeService trong lúc thực thi.
* Kiểm thử đơn vị trở nên cực kì dễ dàng.
* Công cụ giao diện cho các hệ điều hành khác nhau.

## Hệ quả

* Sự tiêm phụ thuộc (dependency injection) trong Java che đậy các lớp dịch vụ phụ thuộc nên nó có thể dẫn tới lỗi trong quá trình thực thi, điều mà lẽ ra có thể bắt được trong quá trình biên dịch.
* Trong khi mẫu này sử dụng hiệu quả với các đối tượng đã được định nghĩa sẵn, thêm một/vài đối tượng mới có thể sẽ khó khăn.
* Mã nguồn trở nên phức tạp hơn do nó có thêm giao diện (interface) và lớp (class) được giới thiệu đi kèm theo mẫu.

## Hướng dẫn

* [Abstract Factory Pattern Tutorial](https://www.journaldev.com/1418/abstract-factory-design-pattern-in-java) 

## Thông dụng

* [javax.xml.parsers.DocumentBuilderFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/parsers/DocumentBuilderFactory.html)
* [javax.xml.transform.TransformerFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/transform/TransformerFactory.html#newInstance--)
* [javax.xml.xpath.XPathFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/xpath/XPathFactory.html#newInstance--)

## Mẫu liên quan

* [Factory Method](https://java-design-patterns.com/patterns/factory-method/)
* [Factory Kit](https://java-design-patterns.com/patterns/factory-kit/)

## Tham khảo
* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
