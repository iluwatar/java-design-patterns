---
title: "Strategy Pattern in Java: Streamlining Object Behaviors with Interchangeable Algorithms"
shortTitle: Strategy
description: "Explore the Strategy design pattern in Java with a detailed guide and practical examples. Learn how to implement flexible and interchangeable algorithms effectively in your Java applications for enhanced design and maintenance."
category: Behavioral
language: en
tag:
  - Decoupling
  - Extensibility
  - Gang of Four
  - Interface
  - Polymorphism
---

## Também conhecido como

* Policy

## Intenção do Strategy Design Pattern

Defina uma família de algoritmos em Java, encapsule cada um e torne-os intercambiáveis para aprimorar o desenvolvimento de software usando o padrão de design Strategy. O Strategy permite que o algoritmo varie independentemente dos clientes que o utilizam.

## Explicação detalhada do padrão de estratégia com exemplos do mundo real

Exemplo do Mundo Real

> Um exemplo prático do padrão de projeto Strategy em Java é evidente em sistemas de navegação automotiva, onde a flexibilidade do algoritmo é fundamental. Diferentes algoritmos de navegação (como rota mais curta, rota mais rápida e rota panorâmica) podem ser usados para determinar o melhor caminho de um local para outro. Cada algoritmo encapsula uma estratégia específica para o cálculo da rota. O usuário (cliente) pode alternar entre esses algoritmos com base em suas preferências sem alterar o próprio sistema de navegação. Isso permite estratégias de navegação flexíveis e intercambiáveis dentro do mesmo sistema. 

Em outras palavras

> O padrão Strategy permite escolher o algoritmo mais adequado em tempo de execução.

De acordo com a Wikipédia

> Na programação de computadores, o padrão Strategy (também conhecido como padrão de política) é um padrão de design de software comportamental que permite selecionar um algoritmo em tempo de execução.

Flowchart

![Strategy flowchart](./etc/strategy-flowchart.png)

## Exemplo programático de padrão Strategy em Java


Matar dragões é um trabalho perigoso. Com a experiência, fica mais fácil. Matadores de dragões veteranos desenvolveram diferentes estratégias de luta contra diferentes tipos de dragões.

Vamos explorar como implementar a interface `DragonSlayingStrategy` em Java, demonstrando várias aplicações do padrão Strategy.

```java
@FunctionalInterface
public interface DragonSlayingStrategy {

  void execute();
}
```

```java
@Slf4j
public class MeleeStrategy implements DragonSlayingStrategy {

  @Override
  public void execute() {
    LOGGER.info("With your Excalibur you sever the dragon's head!");
  }
}
```

```java
@Slf4j
public class ProjectileStrategy implements DragonSlayingStrategy {

  @Override
  public void execute() {
    LOGGER.info("You shoot the dragon with the magical crossbow and it falls dead on the ground!");
  }
}
```

```java
@Slf4j
public class SpellStrategy implements DragonSlayingStrategy {

  @Override
  public void execute() {
    LOGGER.info("You cast the spell of disintegration and the dragon vaporizes in a pile of dust!");
  }
}
```

E aqui está o poderoso `DragonSlayer`, que pode escolher sua estratégia de luta com base no oponente.

```java
public class DragonSlayer {

  private DragonSlayingStrategy strategy;

  public DragonSlayer(DragonSlayingStrategy strategy) {
    this.strategy = strategy;
  }

  public void changeStrategy(DragonSlayingStrategy strategy) {
    this.strategy = strategy;
  }

  public void goToBattle() {
    strategy.execute();
  }
}
```

Finalmente, aqui está o `DragonSlayer` em ação.

```java
@Slf4j
public class App {

  private static final String RED_DRAGON_EMERGES = "Red dragon emerges.";
  private static final String GREEN_DRAGON_SPOTTED = "Green dragon spotted ahead!";
  private static final String BLACK_DRAGON_LANDS = "Black dragon lands before you.";

  public static void main(String[] args) {
    // GoF Strategy pattern
    LOGGER.info(GREEN_DRAGON_SPOTTED);
    var dragonSlayer = new DragonSlayer(new MeleeStrategy());
    dragonSlayer.goToBattle();
    LOGGER.info(RED_DRAGON_EMERGES);
    dragonSlayer.changeStrategy(new ProjectileStrategy());
    dragonSlayer.goToBattle();
    LOGGER.info(BLACK_DRAGON_LANDS);
    dragonSlayer.changeStrategy(new SpellStrategy());
    dragonSlayer.goToBattle();

    // Java 8 functional implementation Strategy pattern
    LOGGER.info(GREEN_DRAGON_SPOTTED);
    dragonSlayer = new DragonSlayer(
        () -> LOGGER.info("With your Excalibur you sever the dragon's head!"));
    dragonSlayer.goToBattle();
    LOGGER.info(RED_DRAGON_EMERGES);
    dragonSlayer.changeStrategy(() -> LOGGER.info(
        "You shoot the dragon with the magical crossbow and it falls dead on the ground!"));
    dragonSlayer.goToBattle();
    LOGGER.info(BLACK_DRAGON_LANDS);
    dragonSlayer.changeStrategy(() -> LOGGER.info(
        "You cast the spell of disintegration and the dragon vaporizes in a pile of dust!"));
    dragonSlayer.goToBattle();

    // Java 8 lambda implementation with enum Strategy pattern
    LOGGER.info(GREEN_DRAGON_SPOTTED);
    dragonSlayer.changeStrategy(LambdaStrategy.Strategy.MELEE_STRATEGY);
    dragonSlayer.goToBattle();
    LOGGER.info(RED_DRAGON_EMERGES);
    dragonSlayer.changeStrategy(LambdaStrategy.Strategy.PROJECTILE_STRATEGY);
    dragonSlayer.goToBattle();
    LOGGER.info(BLACK_DRAGON_LANDS);
    dragonSlayer.changeStrategy(LambdaStrategy.Strategy.SPELL_STRATEGY);
    dragonSlayer.goToBattle();
  }
}
```

Saída do programa:

```
13:06:36.631 [main] INFO com.iluwatar.strategy.App -- Green dragon spotted ahead!
13:06:36.634 [main] INFO com.iluwatar.strategy.MeleeStrategy -- With your Excalibur you sever the dragon's head!
13:06:36.634 [main] INFO com.iluwatar.strategy.App -- Red dragon emerges.
13:06:36.634 [main] INFO com.iluwatar.strategy.ProjectileStrategy -- You shoot the dragon with the magical crossbow and it falls dead on the ground!
13:06:36.634 [main] INFO com.iluwatar.strategy.App -- Black dragon lands before you.
13:06:36.634 [main] INFO com.iluwatar.strategy.SpellStrategy -- You cast the spell of disintegration and the dragon vaporizes in a pile of dust!
13:06:36.634 [main] INFO com.iluwatar.strategy.App -- Green dragon spotted ahead!
13:06:36.634 [main] INFO com.iluwatar.strategy.App -- With your Excalibur you sever the dragon's head!
13:06:36.634 [main] INFO com.iluwatar.strategy.App -- Red dragon emerges.
13:06:36.635 [main] INFO com.iluwatar.strategy.App -- You shoot the dragon with the magical crossbow and it falls dead on the ground!
13:06:36.635 [main] INFO com.iluwatar.strategy.App -- Black dragon lands before you.
13:06:36.635 [main] INFO com.iluwatar.strategy.App -- You cast the spell of disintegration and the dragon vaporizes in a pile of dust!
13:06:36.635 [main] INFO com.iluwatar.strategy.App -- Green dragon spotted ahead!
13:06:36.637 [main] INFO com.iluwatar.strategy.LambdaStrategy -- With your Excalibur you sever the dragon's head!
13:06:36.637 [main] INFO com.iluwatar.strategy.App -- Red dragon emerges.
13:06:36.637 [main] INFO com.iluwatar.strategy.LambdaStrategy -- You shoot the dragon with the magical crossbow and it falls dead on the ground!
13:06:36.637 [main] INFO com.iluwatar.strategy.App -- Black dragon lands before you.
13:06:36.637 [main] INFO com.iluwatar.strategy.LambdaStrategy -- You cast the spell of disintegration and the dragon vaporizes in a pile of dust!
```

## Quando usar o padrão de Strategy em Java

Use o padrão Strategy quando:

* Você precisar usar diferentes variantes de um algoritmo dentro de um objeto e quiser alternar entre algoritmos em tempo de execução.
* Existem várias classes relacionadas que diferem apenas em seu comportamento.
* Um algoritmo usa dados que os clientes não deveriam conhecer.
* Uma classe define muitos comportamentos e estes aparecem como múltiplas instruções condicionais em suas operações.

## Tutoriais de padrões de estratégia em Java

* [Strategy Pattern Tutorial (DigitalOcean)](https://www.digitalocean.com/community/tutorials/strategy-design-pattern-in-java-example-tutorial)

## Aplicações do Padrão Strategy no Mundo Real em Java

* A interface `java.util.Comparator` do Java é um exemplo comum do padrão Strategy.
* Em frameworks de GUI, gerenciadores de layout (como os do AWT e Swing do Java) são estratégias.

## Benefícios e Compensações do Padrão Strategy

Benefícios:

* Famílias de algoritmos relacionados são reutilizadas.
* Uma alternativa à subclasse para estender o comportamento.
* Evita instruções condicionais para selecionar o comportamento desejado.
* Permite que os clientes escolham a implementação do algoritmo.

Compensações:

* Os clientes devem estar cientes das diferentes estratégias.
* Aumento no número de objetos.

## Padrões de Projeto Java Relacionados

* [Decorator](https://java-design-patterns.com/patterns/decorator/): Melhora um objeto sem alterar sua interface, mas está mais preocupado com responsabilidades do que com algoritmos.
* [State](https://java-design-patterns.com/patterns/state/): Semelhante em estrutura, mas usado para representar comportamento dependente de estado em vez de algoritmos intercambiáveis.

## Referências e Créditos

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Functional Programming in Java](https://amzn.to/3JUIc5Q)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
* [Refactoring to Patterns](https://amzn.to/3VOO4F5)
