---
title: "Singleton Pattern in Java: Implementing Global Access Points in Java Applications"
shortTitle: Singleton
description: "Explore the Singleton Pattern in Java with our comprehensive guide. Learn how to implement efficient object management for your Java applications, ensuring optimal use of resources and easy access with examples and detailed explanations."
category: Creational
language: pt
tag:
  - Gang of Four
  - Instantiation
  - Lazy initialization
  - Resource management
---

## Também conhecido como

* Single Instance

## Propósito

Assegurar que a classe Java possua somente uma instância e forneça um ponto global de acesso a essa instância Singleton.

## Explicação

Exemplo do Mundo Real

> Uma analogia do mundo real para o padrão Singleton é um governo emitindo um passaporte. Em um país, cada cidadão pode receber apenas um passaporte válido por vez. O escritório de passaportes garante que nenhum passaporte duplicado seja emitido para a mesma pessoa. Sempre que um cidadão precisa viajar, ele deve usar este passaporte único, que serve como o identificador único e globalmente reconhecido para suas credenciais de viagem. Este acesso controlado e gerenciamento de instância exclusivo espelham como o padrão Singleton garante o gerenciamento eficiente de objetos em aplicativos Java.

Em outras palavras

> Assegura que somente um objeto de classe em particular seja criado.

De acordo com a Wikipédia

> Na engenharia de software, o padrão Singleton é um padrão de design de software que restringe a instanciação de uma classe a um objeto. Isso é útil quando exatamente um objeto é necessário para coordenar ações no sistema.

## Exemplo Programático

Joshua Bloch, Effective Java 2nd Edition pg.18

> Um tipo Enum de elemento único é a melhor maneira de implementar um Singleton

```java
public enum EnumIvoryTower {
  INSTANCE
}
```

Então para usar:

```java
    var enumIvoryTower1 = EnumIvoryTower.INSTANCE;
    var enumIvoryTower2 = EnumIvoryTower.INSTANCE;
    LOGGER.info("enumIvoryTower1={}", enumIvoryTower1);
    LOGGER.info("enumIvoryTower2={}", enumIvoryTower2);
```

A saída do console

```
enumIvoryTower1=com.iluwatar.singleton.EnumIvoryTower@1221555852
enumIvoryTower2=com.iluwatar.singleton.EnumIvoryTower@1221555852
```

## Quando usar

Use o padrão Singleton quando

* Deve haver exatamente uma instância de uma classe, e ela deve ser acessível aos clientes a partir de um ponto de acesso bem conhecido
* Quando a única instância deve ser extensível por herança, e os clientes devem ser capazes de usar uma instância estendida sem modificar seu código

## Aplicações do mundo real

* A classe de Log
* Classes de configuração em muitos aplicativos
* Pools de conexão
* Gerenciador de arquivos
* [java.lang.Runtime#getRuntime()](http://docs.oracle.com/javase/8/docs/api/java/lang/Runtime.html#getRuntime%28%29)
* [java.awt.Desktop#getDesktop()](http://docs.oracle.com/javase/8/docs/api/java/awt/Desktop.html#getDesktop--)
* [java.lang.System#getSecurityManager()](http://docs.oracle.com/javase/8/docs/api/java/lang/System.html#getSecurityManager--)

## Benefícios e desafios do padrão Singleton

Benefícios:

* Acesso controlado à instância única.
* Poluição reduzida do namespace.
* Permite refinamento de operações e representação.
* Permite um número variável de instâncias (se desejado mais de uma).
* Mais flexível do que operações de classe.

Desafios:

* Difícil de testar devido ao estado global.
* Gerenciamento de ciclo de vida potencialmente mais complexo.
* Pode introduzir gargalos se usado em um contexto concorrente sem sincronização cuidadosa.

## Outros Padrōes de Projeto Relacionados

* [Abstract Factory](https://java-design-patterns.com/patterns/abstract-factory/): Geralmente usado para garantir que uma classe tenha apenas uma instância.
* [Factory Method](https://java-design-patterns.com/patterns/factory-method/): O padrão Singleton pode ser implementado usando o padrāo Factory para encapsular a lógica de criação.
* [Prototype](https://java-design-patterns.com/patterns/prototype/): Evita a necessidade de criar instâncias, pode ser usado em conjunto com o Singleton para gerenciar instâncias únicas.

## Referências e Créditos

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [Java Design Patterns: A Hands-On Experience with Real-World Examples](https://amzn.to/3yhh525)
* [Refactoring to Patterns](https://amzn.to/3VOO4F5)
