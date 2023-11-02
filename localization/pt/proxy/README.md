---
title: Proxy
category: Structural
language: pt
tag:
 - Gang Of Four
 - Decoupling
---

## Também conhecido como

Surrogate

## Propósito

Fornecer um substituto para controlar o acesso ao objeto original

## Explicação

Exemplo de mundo real

> Imagine uma torre onde magos locais vão estudar os seus feitiços. A torre de marfim consegue ser
> acedida através de uma proxy que garante que apenas os três primeiros magos podem entrar. Aqui a proxy 
> representa a funcionalidade da torre e adiciona controlo de acesso a ela.

Em palavras simples

> Usando um padrão proxy, a classe representa a funcionalidade de uma outra classe.

Wikipedia diz

> Uma proxy, na sua forma mais genérica, é uma classe a funcionar como interface para algo.
> Uma proxy é um wrapper ou objeto agente que está a ser chamado pelo cliente para aceder ao verdadeiro
> objeto por detrás das cortinas. O uso de uma proxy pode ser apenas redirecionar para o objeto real, ou 
> pode fornecer lógica adicional. Numa proxy, funcionalidade extra também pode ser fornecida, por exemplo apanhar 
> operações no objeto real que gastam muitos recursos, ou verificar precondições antes
> das operações no objeto real serem invocadas.

**Exemplos de programação**

Usando a nossa torre de magos do exemplo acima. Primeiro temos a interface `WizardTower` e a classe
`IvoryTower`.

```java
public interface WizardTower {

  void enter(Wizard wizard);
}

@Slf4j
public class IvoryTower implements WizardTower {

  public void enter(Wizard wizard) {
    LOGGER.info("{} enters the tower.", wizard);
  }

}
```

Depois uma simples classe `Wizard`.

```java
public class Wizard {

  private final String name;

  public Wizard(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
```

Depois temos a `WizardTowerProxy` para adicionar controlo à `WizardTower`.

```java
@Slf4j
public class WizardTowerProxy implements WizardTower {

  private static final int NUM_WIZARDS_ALLOWED = 3;

  private int numWizards;

  private final WizardTower tower;

  public WizardTowerProxy(WizardTower tower) {
    this.tower = tower;
  }

  @Override
  public void enter(Wizard wizard) {
    if (numWizards < NUM_WIZARDS_ALLOWED) {
      tower.enter(wizard);
      numWizards++;
    } else {
      LOGGER.info("{} is not allowed to enter!", wizard);
    }
  }
}
```

E aqui está a torre a entrar em cenário.

```java
var proxy = new WizardTowerProxy(new IvoryTower());
proxy.enter(new Wizard("Red wizard"));
proxy.enter(new Wizard("White wizard"));
proxy.enter(new Wizard("Black wizard"));
proxy.enter(new Wizard("Green wizard"));
proxy.enter(new Wizard("Brown wizard"));
```

Saída do programa:

```
Red wizard enters the tower.
White wizard enters the tower.
Black wizard enters the tower.
Green wizard is not allowed to enter!
Brown wizard is not allowed to enter!
```

## Diagrama de classes

![alt text](./etc/proxy.urm.png "Diagrama de classes do padrão proxy")

## Aplicabilidade

O proxy é aplicável quando existe a necessidade de uma referência a um objeto mais versátil ou sofisticada em vez de um simples ponteiro. Aqui estão várias situações comuns em que o padrão Proxy é aplicável.

* Proxy remoto dá uma representação local de um objeto num espaço de endereçamento diferente.
* Proxy virtual cria objetos dispendiosos quando requisitado.
* Proxy de proteção controla o acesso ao objeto original. Proxies de proteção são úteis quando objetos deviam ter direitos de acesso diferentes.

Tipicamente, o padrão proxy é usado para

* Controlar acesso a um outro objeto
* Inicialização preguiçosa
* Implementar um registro de eventos (logging)
* Facilitar a conexão com a rede
* Contar o número de referências para um objeto

## Tutoriais

* [Controlar acesso com um padrão Proxy](http://java-design-patterns.com/blog/controlling-access-with-proxy-pattern/)

## Usos conhecidos

* [java.lang.reflect.Proxy](http://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Proxy.html)
* [Apache Commons Proxy](https://commons.apache.org/proper/commons-proxy/)
* Frameworks para Mocking [Mockito](https://site.mockito.org/), 
[Powermock](https://powermock.github.io/), [EasyMock](https://easymock.org/)
* UIKit's da Apple [UIAppearance](https://developer.apple.com/documentation/uikit/uiappearance)

## Padrões relacionados

* [Ambassador](https://java-design-patterns.com/patterns/ambassador/)

## Créditos

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
