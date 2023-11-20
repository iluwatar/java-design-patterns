---
title: Decorator
category: Structural
language: id
tag:
 - Gang of Four
 - Extensibility
---

## Juga dikenal sebagai

Pembungkus

## Tujuan

Menyematkan tanggung jawab tambahan ke suatu objek secara dinamis. Dekorator memberikan alternatif yang fleksibel terhadap subkelas untuk memperluas fungsionalitas.

## Penjelasan

Contoh dunia nyata

> Ada troll pemarah yang tinggal di perbukitan terdekat. Biasanya ia pergi dengan tangan kosong, tetapi terkadang ia
> punya senjata. Untuk mempersenjatai troll, Anda tidak perlu membuat troll baru, melainkan mendekorasinya
> secara dinamis dengan senjata yang sesuai.

Dengan kata sederhana

> Pola dekorator memungkinkan Anda mengubah perilaku objek secara dinamis saat run time dengan membungkus
> mereka dalam objek kelas dekorator.

Wikipedia(en) mengatakan

> Dalam pemrograman berorientasi objek, pola dekorator adalah pola desain yang memungkinkan perilaku 
> ditambahkan ke objek individual, baik secara statis maupun dinamis, tanpa memengaruhi perilaku 
> objek lain dari kelas yang sama. Pola dekorator sering kali berguna untuk mematuhi Prinsip Tanggung 
> Jawab Tunggal, karena memungkinkan fungsionalitas dibagi antara kelas-kelas dengan area perhatian yang 
> unik serta Prinsip Terbuka-Tertutup, dengan memungkinkan fungsionalitas suatu kelas diperluas tanpa diubah.

**Contoh Program**

Mari kita ambil contoh troll. Pertama-tama kita memiliki `SimpleTroll` yang mengimplementasikan antarmuka
`Troll`:

```java
public interface Troll {
  void attack();
  int getAttackPower();
  void fleeBattle();
}

@Slf4j
public class SimpleTroll implements Troll {

  @Override
  public void attack() {
    LOGGER.info("Troll itu mencoba menangkapmu!");
  }

  @Override
  public int getAttackPower() {
    return 10;
  }

  @Override
  public void fleeBattle() {
    LOGGER.info("Troll itu menjerit ketakutan dan melarikan diri!");
  }
}
```

Kemudian kita ingin menambahkan gada untuk troll tersebut. Kita dapat melakukannya secara dinamis dengan menggunakan dekorator:

```java
@Slf4j
public class ClubbedTroll implements Troll {

  private final Troll decorated;

  public ClubbedTroll(Troll decorated) {
    this.decorated = decorated;
  }

  @Override
  public void attack() {
    decorated.attack();
    LOGGER.info("Troll itu mengayunkan gada ke arahmu!");
  }

  @Override
  public int getAttackPower() {
    return decorated.getAttackPower() + 10;
  }

  @Override
  public void fleeBattle() {
    decorated.fleeBattle();
  }
}
```

Berikut aksi troll tersebut:

```java
// simple troll
LOGGER.info("Troll biasa mendekat.");
var troll = new SimpleTroll();
troll.attack();
troll.fleeBattle();
LOGGER.info("Kekuatan troll sederhana: {}.\n", troll.getAttackPower());

// change the behavior of the simple troll by adding a decorator
LOGGER.info("Troll dengan gada besar mengejutkanmu.");
var clubbedTroll = new ClubbedTroll(troll);
clubbedTroll.attack();
clubbedTroll.fleeBattle();
LOGGER.info("Kekuatan troll dengan gada: {}.\n", clubbedTroll.getAttackPower());
```

Output program:

```java
Troll biasa mendekat.
Troll itu mencoba menangkapmu!
Troll itu menjerit ketakutan dan melarikan diri!
Kekuatan troll sederhana: 10.

Troll dengan gada besar mengejutkanmu.
Troll itu mencoba menangkapmu!
Troll itu mengayunkan gada ke arahmu!
Troll itu menjerit ketakutan dan melarikan diri!
Kekuatan troll dengan gada: 20.
```

## Diagram kelas

![alt text](./etc/decorator.urm.png "Diagram kelas pola dekorator")

## Penerapan

Dekorator digunakan untuk:

* Tambahkan tanggung jawab ke objek individual secara dinamis dan transparan, tanpa
memengaruhi objek lain.
* Untuk tanggung jawab yang dapat ditarik/dihapus.
* Dimana ekstensi dengan subkelas tidak praktis; Ketika sejumlah besar ekstensi independen
mungkin dilakukan dan akan menghasilkan ledakan subkelas untuk mendukung setiap kombinasi, atau definisi 
kelas mungkin tersembunyi dan/atau tidak tersedia untuk subkelas.

## Tutorial

* [Tutorial Pola Dekorator](https://www.journaldev.com/1540/decorator-design-pattern-in-java-example)

## Kegunaan yang diketahui

 * [java.io.InputStream](http://docs.oracle.com/javase/8/docs/api/java/io/InputStream.html), [java.io.OutputStream](http://docs.oracle.com/javase/8/docs/api/java/io/OutputStream.html),
 [java.io.Reader](http://docs.oracle.com/javase/8/docs/api/java/io/Reader.html) dan [java.io.Writer](http://docs.oracle.com/javase/8/docs/api/java/io/Writer.html)
 * [java.util.Collections#synchronizedXXX()](http://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#synchronizedCollection-java.util.Collection-)
 * [java.util.Collections#unmodifiableXXX()](http://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#unmodifiableCollection-java.util.Collection-)
 * [java.util.Collections#checkedXXX()](http://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#checkedCollection-java.util.Collection-java.lang.Class-)


## Kredit

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Functional Programming in Java: Harnessing the Power of Java 8 Lambda Expressions](https://www.amazon.com/gp/product/1937785467/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=1937785467&linkCode=as2&tag=javadesignpat-20&linkId=7e4e2fb7a141631491534255252fd08b)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=f27d2644fbe5026ea448791a8ad09c94)
