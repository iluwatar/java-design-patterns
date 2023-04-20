---
title: Abstract Factory
categories: Creational
language: pt
tag:
- Gang of Four
---

## Também conhecido como

Kit

## Propósito

Fornece uma interface para criar famílias de objetos relacionados dependentes sem especificar sua classe concreta.

## Explicação

Exemplo do Mundo Real

> Para criar um reino precisamos de objetos com um tema comum. O reino élfico precisa de um rei élfico, um castelo élfico e um exército élfico, enquanto o reino orc precisa de um rei orc, um castelo orc e um exército orc. Existe uma dependência entre os objetos do reino.

Em outras palavras

> Uma fábrica de fábricas; uma fábrica que agrupa outras fábricas individuais, mas relacionadas/dependentes, sem especificar sua classe concreta.

De acordo com a Wikipédia

> A essência do padrão Abstract Factory é fornecer uma interface para criar famílias de objetos relacionados ou dependentes sem especificar suas classes concretas.

**Exemplo Programático**

Traduzindo o exemplo do reino acima. Em primeiro lugar, temos algumas interfaces e implementação para os objetos no reino.

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

// Elven implementations ->
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

// Orcish implementations similarly -> ...

```

Em seguida, temos a abstração e as implementações para a fábrica do reino.

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

Agora temos a fábrica abstrata que nos permite fazer uma família de objetos relacionados, ou seja, a fábrica do reino élfico cria castelo élfico, rei e exército, etc.


```java
var factory = new ElfKingdomFactory();
var castle = factory.createCastle();
var king = factory.createKing();
var army = factory.createArmy();

castle.getDescription();
king.getDescription();
army.getDescription();
```

Output do programa:

```java
This is the elven castle!
This is the elven king!
This is the elven Army!
```


Agora, podemos projetar uma fábrica para nossas diferentes fábricas do reino. Neste exemplo, criamos o `FactoryMaker`, responsável por devolver uma instância de `ElfKingdomFactory` ou `OrcKingdomFactory`.
O cliente pode usar o `FactoryMaker` para criar uma fatoração concreta, que uma vez, produzirá diferentes objetos concretos (derivados de `Army`, `King`, `Castle`).
Neste exemplo, também usamos um enum para parametrizar qual tipo de fábrica do reino o cliente solicitará.

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
        --similar use of the orc factory
    }
```

## Diagrama de classes

![alt text](../../../abstract-factory/etc/abstract-factory.urm.png "Diagrama de Classes de Abstract Factory")


## Aplicabilidade

Use o padrão Abstract Factory quando

* O sistema deve ser independente de como seus produtos são criados, compostos e representados
* O sistema deve ser configurado com uma das várias famílias de produtos
* A família de objetos de produtos relacionados foi projetada para ser usada em conjunto e você precisa aplicar essa restrição
* Você deseja fornecer uma biblioteca de classes de produtos e deseja revelar apenas suas interfaces, não suas implementações
* O tempo de vida da dependência é conceitualmente menor que o tempo de vida do consumidor.
* Você precisa de um valor em tempo de execução para construir uma dependência específica
* Você deseja decidir qual produto chamar de uma família em tempo de execução.
* Você precisa fornecer um ou mais parâmetros conhecidos apenas em tempo de execução antes de resolver uma dependência.
* Quando você precisa de consistência entre os produtos
* Você não deseja alterar o código existente ao adicionar novos produtos ou famílias de produtos ao programa.

Exemplos de casos de uso

* Selecionando para chamar a implementação apropriada de FileSystemAcmeService ou DatabaseAcmeService ou NetworkAcmeService em tempo de execução.
* A escrita de casos de teste de unidade torna-se muito mais fácil
* Ferramentas de interface do usuário para diferentes sistemas operacionais

## Consequencias

* A injeção de dependência em java oculta as dependências da classe de serviço que podem levar a erros de tempo de execução que seriam detectados em tempo de compilação.
* Embora o padrão seja ótimo ao criar objetos predefinidos, adicionar novos pode ser um desafio.
* O código se torna mais complicado do que deveria, pois muitas novas interfaces e classes são introduzidas junto com o padrão.

## Tutoriais

* [Abstract Factory Pattern Tutorial](https://www.journaldev.com/1418/abstract-factory-design-pattern-in-java) 

## Usos conhecidos

* [javax.xml.parsers.DocumentBuilderFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/parsers/DocumentBuilderFactory.html)
* [javax.xml.transform.TransformerFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/transform/TransformerFactory.html#newInstance--)
* [javax.xml.xpath.XPathFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/xpath/XPathFactory.html#newInstance--)

## Padrões relacionados

* [Factory Method](https://java-design-patterns.com/patterns/factory-method/)
* [Factory Kit](https://java-design-patterns.com/patterns/factory-kit/)

## Créditos

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
