---
title: Type-Object
shortTitle: Type-Object
category: Behavioral
language: es
tag:
 - Game programming
 - Extensibility
---

## Propósito
Como se explica en el libro Game Programming Patterns de Robert Nystrom, el patrón objeto tipo ayuda a

> Permitir la creación flexible de nuevas "clases" mediante la creación de una única clase, cada instancia de la cual representa un tipo diferente de objeto

## Explicación
Ejemplo del mundo real
> Estás trabajando en un juego con muchas razas diferentes de monstruos. Cada raza de monstruo tiene diferentes valores para los atributos, como ataque, salud, inteligencia, etc. Quieres crear nuevas razas de monstruos, o modificar los atributos de una raza existente, sin necesidad de modificar el código y recompilar el juego.

En palabras sencillas
> Definimos una clase de objeto de tipo y una clase de objeto tipado. Damos a cada instancia de objeto de tipo una referencia a un objeto tipado, que contiene la información para ese tipo.

**Ejemplo programático**

Supongamos que estamos desarrollando un juego de Candy Crush. Hay muchos tipos de caramelos diferentes, y es posible que queramos editar o crear nuevos con el tiempo a medida que desarrollamos el juego.

En primer lugar, tenemos un tipo para los caramelos, con un nombre de campo, padre, puntos y Tipo.

```java
@Getter(AccessLevel.PACKAGE)
public class Candy {

  enum Type {
    CRUSHABLE_CANDY,
    REWARD_FRUIT
  }

  String name;
  Candy parent;
  String parentName;

  @Setter
  private int points;
  private final Type type;

  Candy(String name, String parentName, Type type, int points) {
    this.name = name;
    this.parent = null;
    this.type = type;
    this.points = points;
    this.parentName = parentName;
  }

}
```

Los datos de campo de los tipos de caramelos se almacenan en el archivo JSON ``candy.json``. Se pueden añadir nuevos caramelos simplemente añadiéndolos a este archivo.

```json
{"candies" : [
  {
    "name" : "fruit",
    "parent" : "null",
    "type" : "rewardFruit",
    "points" : 20
  },
  {
    "name" : "candy",
    "parent" : "null",
    "type" : "crushableCandy",
    "points" : 10
  },
  {
    "name" : "cherry",
    "parent" : "fruit",
    "type" : "rewardFruit",
    "points" : 0
  },
  {
    "name" : "mango",
    "parent" : "fruit",
    "type" : "rewardFruit",
    "points" : 0
  },
  {
    "name" : "purple popsicle",
    "parent" : "candy",
    "type" : "crushableCandy",
    "points" : 0
  },
  {
    "name" : "green jellybean",
    "parent" : "candy",
    "type" : "crushableCandy",
    "points" : 0
  },
  {
    "name" : "orange gum",
    "parent" : "candy",
    "type" : "crushableCandy",
    "points" : 0
  }
  ]
}
```

El archivo JSON se analiza, instanciando cada tipo de caramelo y almacenándolo en una tabla hash. El campo ``type`` se compara con el enum ``Type`` definido en la clase Candy.

```java
public class JsonParser {
  Hashtable<String, Candy> candies;

  JsonParser() {
    this.candies = new Hashtable<>();
  }

  void parse() throws JsonParseException {
    var is = this.getClass().getClassLoader().getResourceAsStream("candy.json");
    var reader = new InputStreamReader(is);
    var json = (JsonObject) com.google.gson.JsonParser.parseReader(reader);
    var array = (JsonArray) json.get("candies");
    for (var item : array) {
      var candy = (JsonObject) item;
      var name = candy.get("name").getAsString();
      var parentName = candy.get("parent").getAsString();
      var t = candy.get("type").getAsString();
      var type = Type.CRUSHABLE_CANDY;
      if (t.equals("rewardFruit")) {
        type = Type.REWARD_FRUIT;
      }
      var points = candy.get("points").getAsInt();
      var c = new Candy(name, parentName, type, points);
      this.candies.put(name, c);
    }
    setParentAndPoints();
  }

  void setParentAndPoints() {
    for (var e = this.candies.keys(); e.hasMoreElements(); ) {
      var c = this.candies.get(e.nextElement());
      if (c.parentName == null) {
        c.parent = null;
      } else {
        c.parent = this.candies.get(c.parentName);
      }
      if (c.getPoints() == 0 && c.parent != null) {
        c.setPoints(c.parent.getPoints());
      }
    }
  }
}
```

## En palabras sencillas

El patrón Tipo-Objeto en Java es un método para encapsular propiedades y comportamientos específicos de un tipo dentro de un objeto. Este patrón de diseño facilita la adición de nuevos tipos sin necesidad de realizar cambios en el código existente, mejorando así la expansión y el mantenimiento de la base de código.

## Wikipedia dice

Aunque no existe una entrada específica en Wikipedia para el patrón Tipo-Objeto, se trata de una técnica de uso común en la programación orientada a objetos. Este patrón ayuda a gestionar objetos que comparten características similares pero tienen valores diferentes para esas características. Su uso está muy extendido en el desarrollo de juegos, donde numerosos tipos de objetos (como los enemigos) comparten un comportamiento común pero tienen propiedades diferentes.

## Ejemplo programático

Consideremos un ejemplo en el que intervienen distintos tipos de enemigos en un juego. Cada tipo de enemigo tiene propiedades distintas, como velocidad, salud y daño.

```java
public class EnemyType {
    private String name;
    private int speed;
    private int health;
    private int damage;
    
    public EnemyType(String name, int speed, int health, int damage) {
        this.name = name;
        this.speed = speed;
        this.health = health;
        this.damage = damage;
    }

    // getters and setters
}

public class Enemy {
    private EnemyType type;

    // Encapsulating type information in an object
    public Enemy(EnemyType type) {
        this.type = type;
    }

    // other methods
}
```

En el ejemplo anterior, `EnemyType` encapsula propiedades específicas del tipo (nombre, velocidad, salud, daño), y `Enemy` utiliza una instancia de `EnemyType` para definir su tipo. De esta forma, puedes añadir tantos tipos de enemigos como quieras sin modificar la clase "Enemigo".

## Aplicabilidad
Este patrón puede utilizarse cuando:

* No sabemos de antemano qué tipos vamos a necesitar.
* Queremos ser capaces de modificar o añadir nuevos tipos sin tener que recompilar o cambiar el código.
* La única diferencia entre los diferentes "tipos" de objetos son los datos, no el comportamiento.

## Otro ejemplo con diagrama de clases
![alt text](./etc/typeobjectpattern.urm.png "Type-Object pattern class diagram")

## Créditos

* [Game Programming Patterns - Type Object](http://gameprogrammingpatterns.com/type-object.html)
* [Types as Objects Pattern](http://www.cs.sjsu.edu/~pearce/modules/patterns/analysis/top.htm)
