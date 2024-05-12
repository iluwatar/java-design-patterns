---
title: Builder
category: Creational
language: vi
tag:
    - Gang of Four
---

## Mục tiêu

Tách rời việc xây dựng một đối tượng phức tạp khỏi cách thể hiện của nó, để cùng một quá trình xây dựng có thể tạo ra các cách thể hiện khác nhau.

## Giải thích

Ví dụ thực tế

> Hãy tưởng tượng một trình tạo nhân vật cho trò chơi nhập vai. Cách đơn giản nhất là để máy tính tạo nhân vật cho bạn. Nếu bạn muốn chọn các chi tiết về nhân vật như nghề nghiệp, giới tính, màu tóc, v.v. thì việc tạo nhân vật trở thành một quá trình từng bước và hoàn tất khi tất cả các lựa chọn đã sẵn sàng.

Nói một cách đơn giản

> Mẫu Builder cho phép bạn tạo ra các phiên bản khác nhau của một đối tượng trong khi tránh việc dùng hàm khởi tạo. Mẫu này hữu ích khi có thể có nhiều phiên bản của một đối tượng. Hoặc khi có nhiều bước liên quan tới việc tạo một đối tượng.

Theo Wikipedia

> Mẫu Builder là một mẫu thiết kế phần mềm dùng để tạo ra đối tượng, với mục đích tìm ra giải pháp cho phản thiết kế hàm khởi tạo quá nhiều tham số (telescoping constructor antipattern).

Để giải thích rõ hơn về lỗi phản thiết kế hàm khởi tạo có quá nhiều tham số là gì. Ở một thời điểm nào đó, tất cả chúng ta đều đã thấy một hàm khởi tạo như bên dưới:

```java
public Hero(Profession profession,String name,HairType hairType,HairColor hairColor,Armor armor,Weapon weapon){
        }
```

Như bạn có thể thấy, số lượng tham số của hàm khởi tạo có thể nhanh chóng trở nên rất nhiều, và có thể trở nên khó khăn cho việc sắp xếp các tham số. Hơn nữa, danh sách tham số này có thể tiếp tục tăng lên nếu bạn muốn bổ sung thêm tùy chọn trong tương lai. Đây được gọi là lỗi phản thiết kế hàm khởi tạo có quá nhiều tham số.

**Ví dụ lập trình**

Một giải pháp hợp lý hơn là sử dụng mẫu Builder. Trước hết, chúng ta có nhân vật anh hùng mà chúng ta muốn tạo ra:

```java
public final class Hero {
    private final Profession profession;
    private final String name;
    private final HairType hairType;
    private final HairColor hairColor;
    private final Armor armor;
    private final Weapon weapon;

    private Hero(Builder builder) {
        this.profession = builder.profession;
        this.name = builder.name;
        this.hairColor = builder.hairColor;
        this.hairType = builder.hairType;
        this.weapon = builder.weapon;
        this.armor = builder.armor;
    }
}
```

Sau đó chúng ta có lớp Builder:

```java
  public static class Builder {
    private final Profession profession;
    private final String name;
    private HairType hairType;
    private HairColor hairColor;
    private Armor armor;
    private Weapon weapon;

    public Builder(Profession profession, String name) {
        if (profession == null || name == null) {
            throw new IllegalArgumentException("profession and name can not be null");
        }
        this.profession = profession;
        this.name = name;
    }

    public Builder withHairType(HairType hairType) {
        this.hairType = hairType;
        return this;
    }

    public Builder withHairColor(HairColor hairColor) {
        this.hairColor = hairColor;
        return this;
    }

    public Builder withArmor(Armor armor) {
        this.armor = armor;
        return this;
    }

    public Builder withWeapon(Weapon weapon) {
        this.weapon = weapon;
        return this;
    }

    public Hero build() {
        return new Hero(this);
    }
}
```

Sau đó nó có thể được sử dụng như sau:

```java
var mage=new Hero.Builder(Profession.MAGE,"Riobard").withHairColor(HairColor.BLACK).withWeapon(Weapon.DAGGER).build();
```

## Class diagram

![alt text](../../../builder/etc/builder.urm.png "Builder class diagram")

## Ứng dụng

Sử dụng mẫu Builder khi:

* Thuật toán để tạo một đối tượng phức tạp phải độc lập với các bộ phận tạo nên đối tượng đó và cách chúng liên kết với nhau.
* Quá trình xây dựng phải cho phép các cách thể hiện khác nhau cho đối tượng được tạo ra.
* Đặc biệt hữu ích khi một sản phẩm cần nhiều bước để được tạo ra và khi các bước này cần được thực hiện theo một trình tự nhất định.

## Các trường hợp sử dụng đã biết

* Java.lang.StringBuilder
* Java.nio.ByteBuffer cũng như các bộ đệm tương tự như FloatBuffer, IntBuffer, và các bộ đệm khác.
* javax.swing.GroupLayout.Group#addComponent()

## Hậu quả

Ưu điểm:

* Kiểm soát tốt hơn quá trình xây dựng đối tượng so với các mẫu tạo đối tượng khác.
* Hỗ trợ xây dựng đối tượng theo từng bước, hoãn các bước xây dựng hoặc chạy các bước đệ quy.
* Có thể xây dựng các đối tượng đòi hỏi quá trình kết hợp phức tạp các đối tượng con. Sản phẩm cuối cùng được tách rời khỏi các bộ phận tạo nên nó, cũng như quá trình liên kết chúng.
* Nguyên tắc Đơn Trách nhiệm. Bạn có thể tách rời mã xây dựng phức tạp khỏi logic nghiệp vụ của sản phẩm.

Nhược điểm:

* Tổng thể độ phức tạp của code có thể tăng lên vì mẫu đòi hỏi phải tạo nhiều lớp mới.

## Hướng dẫn

* [Refactoring Guru](https://refactoring.guru/design-patterns/builder)
* [Oracle Blog](https://blogs.oracle.com/javamagazine/post/exploring-joshua-blochs-builder-design-pattern-in-java)
* [Journal Dev](https://www.journaldev.com/1425/builder-design-pattern-in-java)

## Các trường hợp sử dụng đã biết

* [java.lang.StringBuilder](http://docs.oracle.com/javase/8/docs/api/java/lang/StringBuilder.html)
* [java.nio.ByteBuffer](http://docs.oracle.com/javase/8/docs/api/java/nio/ByteBuffer.html#put-byte-) cũng như các bộ đệm tương tự như FloatBuffer, IntBuffer, v.v.
* [java.lang.StringBuffer](http://docs.oracle.com/javase/8/docs/api/java/lang/StringBuffer.html#append-boolean-)
* Tất cả các triển khai của [java.lang.Appendable](http://docs.oracle.com/javase/8/docs/api/java/lang/Appendable.html)
* [Apache Camel builders](https://github.com/apache/camel/tree/0e195428ee04531be27a0b659005e3aa8d159d23/camel-core/src/main/java/org/apache/camel/builder)
* [Apache Commons Option.Builder](https://commons.apache.org/proper/commons-cli/apidocs/org/apache/commons/cli/Option.Builder.html)

## Mẫu liên quan

* [Step Builder](https://java-design-patterns.com/patterns/step-builder/) là một biến thể của mẫu Builder tạo ra một đối tượng phức tạp theo cách tiếp cận từng bước. Mẫu Step Builder là một lựa chọn tốt khi bạn cần xây dựng một đối tượng với nhiều tham số tùy chọn, và bạn muốn tránh lỗi phản thiết kế hàm khởi tạo có quá nhiều tham số.

## Ghi nhận

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Effective Java](https://www.amazon.com/gp/product/0134685997/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0134685997&linkCode=as2&tag=javadesignpat-20&linkId=4e349f4b3ff8c50123f8147c828e53eb)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)