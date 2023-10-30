---
title: Decorator
category: Structural
language: pt
tag:
 - Gang of Four
 - Extensibility
---

## Também conhecido como

Wrapper

## Propósito

Ligar dinamicamente responsabilidades adicionais a um objeto.
Decoradores dão uma alternativa fléxivel à criação de subclasses para extender a funcionalidade.

## Explicação

Exemplo do Mundo Real

> Existe um troll zangado a viver numas colinas próximas. Usualmente, costuma lutar com as mãos mas de vez em quando
> tem uma arma. Para armar o troll não é necessário criar um novo troll, podemos apenas "decorá-lo"
> dinamicamente com uma arma adequada.

Em palavras simples

> O padrão Decorator permite modificar dinamicamente o comportamento de um objeto no runtime "embalando-o" (wrapping)
> num objeto da classe do tipo decorator

A wikipedia diz

> Em programação orientada a objetos, o padrão decorator é um padrão de desenho que permite comportamento 
> ser adicionado a um objeto individual, dinamicamente ou estaticamente, sem afetar o comportamento
> de outros objetos da mesma class. O padrão decorator é frequentemente útil para aderir ao 
> "Príncipio da Responsabilidade Singular" (Single Responsibility Principle), pois permite que a funcionalidade seja dividida entre classes
> com objetivos únicos assim como adere ao "Príncipio Aberto-Fechado" (Open-Closed Principle), pois permite que a funcionalidade
> de uma classe seja extendida sem ser modificada.

**Exemplos de programação**

Vamos olhar para o exemplo do troll. Antes que tudo temos um `SimpleTroll` a implementar o `Troll`
interface:

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
    LOGGER.info("The troll tries to grab you!");
  }

  @Override
  public int getAttackPower() {
    return 10;
  }

  @Override
  public void fleeBattle() {
    LOGGER.info("The troll shrieks in horror and runs away!");
  }
}
```

A seguir, queremos adicionar o porrete ao troll. Podemos fazê-lo dinamicamente usando um decorador: 

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
    LOGGER.info("The troll swings at you with a club!");
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

Aqui está o troll em ação.

```java
// simple troll
LOGGER.info("A simple looking troll approaches.");
var troll = new SimpleTroll();
troll.attack();
troll.fleeBattle();
LOGGER.info("Simple troll power: {}.\n", troll.getAttackPower());

// change the behavior of the simple troll by adding a decorator
LOGGER.info("A troll with huge club surprises you.");
var clubbedTroll = new ClubbedTroll(troll);
clubbedTroll.attack();
clubbedTroll.fleeBattle();
LOGGER.info("Clubbed troll power: {}.\n", clubbedTroll.getAttackPower());
```

Output do programa:

```java
A simple looking troll approaches.
The troll tries to grab you!
The troll shrieks in horror and runs away!
Simple troll power: 10.

A troll with huge club surprises you.
The troll tries to grab you!
The troll swings at you with a club!
The troll shrieks in horror and runs away!
Clubbed troll power: 20.
```

## Diagrama da classe

![alt text](./etc/decorator.urm.png "Diagrama de classes do padrão Decorator")

## Aplicabilidade

Decorator é usado para:

* Adicionar responsabilidades a objetos individuais dinamicamente e de forma transparente, isto é, sem afetar outros objetos.
* Para responsabilidades que possam ser retiradas.
* Quando extender a funcionalidade através de uma subclasse não é prático. Por vezes, um grande número de extensões independentes são possíveis e produziriam uma "explosão" de subclasses para suportar todas as combinações possíveis. Ou então a definição da classe pode estar escondida ou indisponível para criação de subclasses.

## Tutoriais

* [Decorator Pattern Tutorial](https://www.journaldev.com/1540/decorator-design-pattern-in-java-example)

## Usos comuns

 * [java.io.InputStream](http://docs.oracle.com/javase/8/docs/api/java/io/InputStream.html), [java.io.OutputStream](http://docs.oracle.com/javase/8/docs/api/java/io/OutputStream.html),
 [java.io.Reader](http://docs.oracle.com/javase/8/docs/api/java/io/Reader.html) e [java.io.Writer](http://docs.oracle.com/javase/8/docs/api/java/io/Writer.html)
 * [java.util.Collections#synchronizedXXX()](http://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#synchronizedCollection-java.util.Collection-)
 * [java.util.Collections#unmodifiableXXX()](http://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#unmodifiableCollection-java.util.Collection-)
 * [java.util.Collections#checkedXXX()](http://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#checkedCollection-java.util.Collection-java.lang.Class-)


## Créditos

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Functional Programming in Java: Harnessing the Power of Java 8 Lambda Expressions](https://www.amazon.com/gp/product/1937785467/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=1937785467&linkCode=as2&tag=javadesignpat-20&linkId=7e4e2fb7a141631491534255252fd08b)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=f27d2644fbe5026ea448791a8ad09c94)
