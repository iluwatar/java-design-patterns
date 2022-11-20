---
title: Builder
categories: Creational
language: pt
tag:
- Gang of Four
---

## Propósito

Separe a construção de um objeto complexo de sua representação para que a mesma construção do
processo possa criar diferentes representações.

## Explicação

Exemplo do Mundo Real

> Imagine um gerador de personagens para um jogo de RPG. A opção mais fácil é deixar que o computador
> crie o personagem para você. Se você quiser selecionar manualmente os detalhes do personagem como
> profissão, gênero, cor de cabelo, etc. a geração do personagem se torna um processo passo a passo que
> termina quando todas as seleções estiverem prontas.

Em outras palavras

> Permite criar diferentes formas de um objeto, evitando a poluição do construtor. Útil
> quando pode haver várias formas de um objeto. Ou quando há muitos passos envolvidos na
> criação de um objeto.

De acordo com a Wikipédia

> o Builder constrói objetos complexos passo a passo e procura evitar ser um anti-padrão.

Dito isso, deixe-me acrescentar um pouco sobre o que é o antipadrão do construtor. Em um ponto
ou o outro, todos nós vimos um construtor como abaixo:

```java
public Hero(Profession profession, String name, HairType hairType, HairColor hairColor, Armor armor, Weapon weapon) {
}
```

Como você pode ver, o número de parâmetros do construtor pode ficar rapidamente fora de controle e pode se tornar
difícil entender a disposição dos parâmetros. Além disso, esta lista de parâmetros pode continuar
crescendo se você quiser adicionar mais opções no futuro. Isso é chamado de construtor telescópico
anti-padrão.

**Exemplo Programático**

A alternativa sensata é usar o padrão Builder. Em primeiro lugar, temos o nosso herói que queremos criar:

```java
public final class Hero {
  private final Profession profession;
  private final String name;
  private final HairType hairType;
  private final HairColor hairColor;
  private final Armor armor;
  private final Weapon weapon;

  private Hero(Builder builder) {
    this.profession = builder.profession;
    this.name = builder.name;
    this.hairColor = builder.hairColor;
    this.hairType = builder.hairType;
    this.weapon = builder.weapon;
    this.armor = builder.armor;
  }
}
```

Então temos o builder:

```java
  public static class Builder {
    private final Profession profession;
    private final String name;
    private HairType hairType;
    private HairColor hairColor;
    private Armor armor;
    private Weapon weapon;

    public Builder(Profession profession, String name) {
      if (profession == null || name == null) {
        throw new IllegalArgumentException("profession and name can not be null");
      }
      this.profession = profession;
      this.name = name;
    }

    public Builder withHairType(HairType hairType) {
      this.hairType = hairType;
      return this;
    }

    public Builder withHairColor(HairColor hairColor) {
      this.hairColor = hairColor;
      return this;
    }

    public Builder withArmor(Armor armor) {
      this.armor = armor;
      return this;
    }

    public Builder withWeapon(Weapon weapon) {
      this.weapon = weapon;
      return this;
    }

    public Hero build() {
      return new Hero(this);
    }
  }
```

Então pode ser usado como:

```java
var mage = new Hero.Builder(Profession.MAGE, "Riobard").withHairColor(HairColor.BLACK).withWeapon(Weapon.DAGGER).build();
```

## Diagrama de classes

![alt text](../../../builder/etc/builder.urm.png "Diagrama de classes Builder")

## Aplicabilidade

Use o padrão Builder quando

* O algoritmo para criar um objeto complexo deve ser independente das partes que compõem o objeto e de como elas são montadas
* O processo de construção deve permitir diferentes representações para o objeto construído

## Tutoriais

* [Refactoring Guru](https://refactoring.guru/design-patterns/builder)
* [Oracle Blog](https://blogs.oracle.com/javamagazine/post/exploring-joshua-blochs-builder-design-pattern-in-java)
* [Journal Dev](https://www.journaldev.com/1425/builder-design-pattern-in-java)

## Usos conhecidos

* [java.lang.StringBuilder](http://docs.oracle.com/javase/8/docs/api/java/lang/StringBuilder.html)
* [java.nio.ByteBuffer](http://docs.oracle.com/javase/8/docs/api/java/nio/ByteBuffer.html#put-byte-) bem como buffers semelhantes, como FloatBuffer, IntBuffer e assim por diante.
* [java.lang.StringBuffer](http://docs.oracle.com/javase/8/docs/api/java/lang/StringBuffer.html#append-boolean-)
* Todas as implementações de [java.lang.Appendable](http://docs.oracle.com/javase/8/docs/api/java/lang/Appendable.html)
* [Apache Camel builders](https://github.com/apache/camel/tree/0e195428ee04531be27a0b659005e3aa8d159d23/camel-core/src/main/java/org/apache/camel/builder)
* [Apache Commons Option.Builder](https://commons.apache.org/proper/commons-cli/apidocs/org/apache/commons/cli/Option.Builder.html)

## Créditos

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Effective Java](https://www.amazon.com/gp/product/0134685997/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0134685997&linkCode=as2&tag=javadesignpat-20&linkId=4e349f4b3ff8c50123f8147c828e53eb)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
