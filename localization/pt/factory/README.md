---
title: Factory
categories: Creational
language: pt
tag:
- Gang of Four
---

## Também conhecido como

- Fábrica Simples
- Método de fábrica estático

## Propósito

Fornecendo um método estático encapsulado em uma classe chamada fábrica, para ocultar a implementação
lógica e fazer com que o código do cliente se concentre no uso em vez de inicializar novos objetos.

## Explicação

Exemplo do Mundo Real

>Imagine um alquimista que está prestes a fabricar moedas. O alquimista deve ser capaz de criar ambos moedas de ouro e cobre e a troca entre elas deve ser possível sem modificar o existente
código fonte. O padrão de fábrica torna isso possível, fornecendo um método de construção estático que pode ser chamado com parâmetros relevantes.

De acordo com a Wikipédia

>Fábrica é um objeto para criar outros objetos – formalmente uma fábrica é uma função ou método que retorna objetos de um protótipo ou classe variável.

**Exemplo Programático**

Temos uma interface Coin e duas implementações GoldCoin CopperCoin.

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

A enumeração acima representa os tipos de moedas que oferecemos suporte (GoldCoin e CopperCoin).

```java
@RequiredArgsConstructor
@Getter
public enum CoinType {

  COPPER(CopperCoin::new),
  GOLD(GoldCoin::new);

  private final Supplier<Coin> constructor;
}
```

Então temos o método estático getCoin para criar objetos moeda encapsulados na classe de fábrica Fábrica de moedas.

```java
public class CoinFactory {

  public static Coin getCoin(CoinType type) {
    return type.getConstructor().get();
  }
}
```

Agora no código do cliente podemos criar diferentes tipos de moedas usando a classe factory.

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

(yet to be added)

## Aplicabilidade

Use o padrão de fábrica quando você se preocupa apenas com a criação de um objeto, e não com como criá-lo e gerenciá-lo.

Prós

* Permite manter a criação de todos os objetos em um só lugar e evitar a propagação de 'novas' palavras-chave pela base de código.
* Permite escrever código fracamente acoplado. Algumas de suas principais vantagens incluem melhor testabilidade, código fácil de entender, componentes trocáveis, escalabilidade e recursos isolados.

Contras

* O código se torna mais complicado do que deveria.

  ## Usos conhecidos

* [java.util.Calendar#getInstance()](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html#getInstance--)
* [java.util.ResourceBundle#getBundle()](https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html#getBundle-java.lang.String-)
* [java.text.NumberFormat#getInstance()](https://docs.oracle.com/javase/8/docs/api/java/text/NumberFormat.html#getInstance--)
* [java.nio.charset.Charset#forName()](https://docs.oracle.com/javase/8/docs/api/java/nio/charset/Charset.html#forName-java.lang.String-)
* [java.net.URLStreamHandlerFactory#createURLStreamHandler(String)](https://docs.oracle.com/javase/8/docs/api/java/net/URLStreamHandlerFactory.html) (retorna diferentes objetos singleton, dependendo do protocolo)
* [java.util.EnumSet#of()](https://docs.oracle.com/javase/8/docs/api/java/util/EnumSet.html#of(E))
* [javax.xml.bind.JAXBContext#createMarshaller()](https://docs.oracle.com/javase/8/docs/api/javax/xml/bind/JAXBContext.html#createMarshaller--) e outros métodos semelhantes.


## Padrões relacionados

* [Factory Method](https://java-design-patterns.com/patterns/factory-method/)
* [Factory Kit](https://java-design-patterns.com/patterns/factory-kit/)
* [Abstract Factory](https://java-design-patterns.com/patterns/abstract-factory/)
