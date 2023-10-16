---
title: Factory
category: Creational
language: pt
tag:
 - Gang of Four
---

## Também conhecido como

* Simple Factory
* Static Factory Method

## Propósito

Fornecer um método estático encapsulado em uma classe conhecida como factory, para ocultar a lógica
de implementação e fazer com que o código do cliente se preocupe apenas com sua utilização em vez de
inicializar novos objetos.

## Explicação

Exemplo de mundo real

> Imagine um alquimista que está prestes a fabricar moedas. O alquimista deve ser capaz de produzir
> moedas de ouro e cobre, e a alternância entre a fabricação destas deve ser possível sem a necessidade
> de modificar o código fonte. O padrão factory torna isso possível fornecendo um método construtor estático
> que pode ser chamado com parâmetros relevantes.

Wikipedia diz

> Factory é um objeto para a criação de outros objetos - formalmente uma factory é uma função ou método
> que retorna objetos de protótipos ou classes variadas.

**Exemplo de programação**

Temos uma interface `Coin` e duas implementações `GoldCoin` e `CopperCoin`.

```java
public interface Coin {
  String getDescription();
}

public class GoldCoin implements Coin {

  static final String DESCRIPTION = "This is a gold coin.";

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}

public class CopperCoin implements Coin {
   
  static final String DESCRIPTION = "This is a copper coin.";

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
```

O enum abaixo representa os tipos de moedas que suportamos (`GoldCoin` e `CopperCoin`).

```java
@RequiredArgsConstructor
@Getter
public enum CoinType {

  COPPER(CopperCoin::new),
  GOLD(GoldCoin::new);

  private final Supplier<Coin> constructor;
}
```

Então temos o método estático `getCoin` para criar objetos de moeda encapsulados na classe factory
`CoinFactory`.

```java
public class CoinFactory {

  public static Coin getCoin(CoinType type) {
    return type.getConstructor().get();
  }
}
```

Agora no código cliente podemos criar diferentes tipos de moedas usando a classe factory.

```java
LOGGER.info("The alchemist begins his work.");
var coin1 = CoinFactory.getCoin(CoinType.COPPER);
var coin2 = CoinFactory.getCoin(CoinType.GOLD);
LOGGER.info(coin1.getDescription());
LOGGER.info(coin2.getDescription());
```

Saída do programa:

```java
The alchemist begins his work.
This is a copper coin.
This is a gold coin.
```

## Diagrama de classes

![alt text](./etc/factory.urm.png "Diagrama de classes do padrão Factory")

## Aplicabilidade

Utilize o padrão factory quando a preocupação é apenas em criar o objeto, e não como criar
e gerenciá-lo.

Prós

* Permite manter a criação de todos os objetos em um só lugar e evita espalhar a palavra-chave 'new' pela base de código.
* Permite escrever código desacoplado. Algumas de suas principais vantagens incluem uma melhor testabilidade, código fácil de entender, componentes substituíveis, escalabilidade e funcionalidades isoladas.

Contras

* O código se torna mais complicado do que deveria.

## Usos conhecidos

* [java.util.Calendar#getInstance()](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html#getInstance--)
* [java.util.ResourceBundle#getBundle()](https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html#getBundle-java.lang.String-)
* [java.text.NumberFormat#getInstance()](https://docs.oracle.com/javase/8/docs/api/java/text/NumberFormat.html#getInstance--)
* [java.nio.charset.Charset#forName()](https://docs.oracle.com/javase/8/docs/api/java/nio/charset/Charset.html#forName-java.lang.String-)
* [java.net.URLStreamHandlerFactory#createURLStreamHandler(String)](https://docs.oracle.com/javase/8/docs/api/java/net/URLStreamHandlerFactory.html) (retorna objetos singleton diferentes, dependendo do protocolo)
* [java.util.EnumSet#of()](https://docs.oracle.com/javase/8/docs/api/java/util/EnumSet.html#of(E))
* [javax.xml.bind.JAXBContext#createMarshaller()](https://docs.oracle.com/javase/8/docs/api/javax/xml/bind/JAXBContext.html#createMarshaller--) e outros métodos parecidos.

## Padrões relacionados

* [Factory Method](https://java-design-patterns.com/patterns/factory-method/)
* [Factory Kit](https://java-design-patterns.com/patterns/factory-kit/)
* [Abstract Factory](https://java-design-patterns.com/patterns/abstract-factory/)

