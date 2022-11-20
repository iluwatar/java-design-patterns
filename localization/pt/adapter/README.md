---
title: Adapter
category: Structural
language: pt
tag:
 - Gang of Four
---

## Também conhecido como
Wrapper

## Propósito
Converter a interface de uma classe em outra que o cliente espera.
O padrão Adapter permite que uma classe funcione em conjunto com outras classes que não poderiam por problemas de imcompatibilidade de interfaces.

## Explicação

Exemplo do mundo real

> Considere que você possui algumas imagens no seu cartão de memória e precisa transferí-las para seu computador. Para isso, você irá precisar de algum tipo de adaptador que seja compatível com alguma entrada de seu computador, então você poderá conectar o cartão de memória no seu computador. Nesse caso, o leitor de cartões de memória é um adaptador (Adapter).
> Outro exemplo poderia ser o famoso adaptador elétrico; um plug de três pinos não pode ser conectado a uma tomada de dois oríficios, é necessário usar um adaptador que fará com que haja compatibilidade entre a tomada e o plugue.
> Outro exemplo ainda poderia ser um tradutor traduzindo palavras ditas por uma pessoa para outra.

Em outras palavras

> O padrão Adapter permite que você envolva um objeto incompatível em um adaptador que o transforma em um objeto compatível com outra classe.

Wikipedia diz

> Em engenharia de software, o padrão Adapter é um padrão de projeto de software que permite a interface de uma classe existente ser usada como outra interface. É frequentemente usada para fazer classes já existentes funcionarem com outras, sem modificar seu código fonte.

**Exemplo de programação**

Considere um capitão que pode apenas usar botes a remo e não pode navegar de outro modo.

Primeiramente, temos as interfaces `RowingBoat` e `FishingBoat`

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

E o capitão espera uma implementação da interface `RowingBoat` para ser capaz de se mover

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

Agora, digamos que os piratas estão chegando e nosso capitão precisa escapar, mas há apenas um bote de pesca disponível. Nós precisamos criar um adaptador que permita o capitão operar o bote de pesca com suas habilidades de condução do bote a remo.

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

E agora o `Captain` pode usar o `FishingBoat` para escapar dos piratas.

```java
var captain = new Captain(new FishingBoatAdapter());
captain.row();
```

## Diagrama de classes
![alt text](./etc/adapter.urm.png "Diagrama de classes do Adapter")

## Aplicabilidade
Use o padrão Adapter quando

* Você quer usar uma classe existente, e sua interface não combina exatamente com o que você precisa
* Você quer criar uma classe reusável que contribui com classes não relacionadas ou não previstas, isto é, classes que não necessariamente tem interfaces compatíveis
* Você precisa utilizar várias subclasses existentes, mas é impraticável adaptar suas interfaces criando novas subclasses para cada uma. Um adapter pode adaptar a interface da sua classe pai.
* A maioria das aplicações que usa bibliotecas de terceiros usa apdaters como uma camada intermediária entre a aplicação e a biblioteca de terceiro para desacoplar a aplicação da biblioteca externa. Se outra biblioteca precisar ser usada, será necessário apenas adequar o adapter para essa nova biblioteca, sem necessidade de alterar muito código.

## Tutoriais (em inglês)

* [Dzone](https://dzone.com/articles/adapter-design-pattern-in-java)
* [Refactoring Guru](https://refactoring.guru/design-patterns/adapter/java/example)
* [Baeldung](https://www.baeldung.com/java-adapter-pattern)

## Consequências
Classes e objetos adapter tem diferentes prós e contras. Uma classe adapter

* 	Converte o objeto adaptado para o alvo por meio de uma classe concreta. Como consequência, uma classe adapter não irá funcionar se desejamos adaptar uma classe e todas as suas subclasses.
* 	Deixa o Adapter sobrescrever algum comportamento da classe adaptada já que o Adapter é uma subclasse da classe adaptada.
*	Introduz apenas um objeto, e nenhum ponteiro indireto é necessário para obter a classe adaptada.

Um objeto adapter

*	Permite um único adaptador funcionar com vários objetos adaptados, isto é, o objeto adaptado em si e todas as suas subclasses (se houver). O adapter também pode adicionar funcionalidade a todas os objetos adaptados de uma vez só.
*	Torna mais difícil sobrescrever o comportamento do objeto adaptado. Isso irá requerer geração de subclasses para o objeto adaptado e fazer o adapter se referir a essa subclasse em vez do objeto adaptado em si.

## Exemplos do mundo real

* [java.util.Arrays#asList()](http://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#asList%28T...%29)
* [java.util.Collections#list()](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#list-java.util.Enumeration-)
* [java.util.Collections#enumeration()](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#enumeration-java.util.Collection-)
* [javax.xml.bind.annotation.adapters.XMLAdapter](http://docs.oracle.com/javase/8/docs/api/javax/xml/bind/annotation/adapters/XmlAdapter.html#marshal-BoundType-)


## Créditos

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
