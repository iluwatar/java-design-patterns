---
title: Singleton
category: Creational
language: tr
tag:
- Gang of Four
---

## Amaç

Bir sınıfın yalnızca bir örneğine sahip olduğundan emin olun ve ona global bir erişim noktası sağlayın.


## Açıklama

Örnek

> Büyücülerin büyülerini çalıstıkları tek bir fildişi kule olabilir. Aynı büyülü fildişi kule,
> büyücüler tarafından her zaman kullanılır. Buradaki fildişi kulesi singleton tasarım desenine örnektir.
Özetle

> Belirli bir sınıftan yalnızca bir nesnenin oluşturulmasını sağlar.
Wikipedia açıklaması



> Yazılım mühendisliğinde, tekil desen, bir sınıfın somutlaştırılmasını tek bir nesneyle sınırlayan
> bir yazılım tasarım modelidir.Bu,sistemdeki eylemleri koordine etmek için
> tam olarak bir nesne gerektiğinde kullanışlıdır.
**Örnek**

Joshua Bloch, Effective Java 2nd Edition p.18

> Enum singleton tasarım desenini uygulamak için en iyi yoldur.
```java
public enum EnumIvoryTower {
  INSTANCE
}
```

Tanımladıktan sonra kullanmak için:

```java
var enumIvoryTower1 = EnumIvoryTower.INSTANCE;
var enumIvoryTower2 = EnumIvoryTower.INSTANCE;
assertEquals(enumIvoryTower1, enumIvoryTower2); // true
```

## Sınıf diagramı

![alt text](etc/singleton.urm.png)

## Uygulanabilirlik

Singleton tasarım deseni şu durumlarda kullanılmalıdır

* Bir sınıfın tam olarak bir örneği olmalı ve iyi bilinen bir erişim noktasından istemciler tarafından erişilebilir olmalıdır.
* Tek örnek alt sınıflandırma yoluyla genişletilebilir olduğunda ve istemciler, kodlarını değiştirmeden genişletilmiş bir örnek kullanabilmelidir

## Use Case

* Logging sınıflarında
* Database bağlantılarını yönetmek için
* File manager

## Gerçek dünya örnekleri

* [java.lang.Runtime#getRuntime()](http://docs.oracle.com/javase/8/docs/api/java/lang/Runtime.html#getRuntime%28%29)
* [java.awt.Desktop#getDesktop()](http://docs.oracle.com/javase/8/docs/api/java/awt/Desktop.html#getDesktop--)
* [java.lang.System#getSecurityManager()](http://docs.oracle.com/javase/8/docs/api/java/lang/System.html#getSecurityManager--)


## Sonuçlar

* Kendi yaratımını ve yaşam döngüsünü kontrol ederek Tek Sorumluluk İlkesini (SRP) ihlal ediyor.
* Bu nesne tarafından kullanılan bir nesnenin ve kaynakların serbest bırakılmasını önleyen global bir paylaşılan örnek kullanmayı teşvik eder.
* Birbirine sıkı bağlı kod oluşturur. Singleton tasarım deseni kullanan istemci sınıflarını test etmek zorlaşır.
* Bir Singleton tasarım deseninden alt sınıflar oluşturmak neredeyse imkansız hale gelir.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Effective Java](https://www.amazon.com/gp/product/0134685997/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0134685997&linkCode=as2&tag=javadesignpat-20&linkId=4e349f4b3ff8c50123f8147c828e53eb)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)