---
title: Type-Object
category: Behavioral
language: en
tag:
 - Game programming
 - Extensibility
---

## Intent
As explained in the book Game Programming Patterns by Robert Nystrom, type object pattern helps in

> Allowing flexible creation of new “classes” by creating a single class, each instance of which represents a different type of object

## Explanation
Real-world example
> You are working on a game with many different breeds of monsters. Each monster breed has different values for the attributes, such as attack, health, intelligence, etc. You want to create new monster breeds, or modify the attributes of an existing breed, without needing to modify the code and recompiling the game.

In plain words
> Define a type object class, and a typed object class. We give each type object instance a reference to a typed object, containing the information for that type.

**Programmatic example**

Suppose we are developing a game of Candy Crush. There are many different candy types, and we may want to edit or create new ones over time as we develop the game.

First, we have a type for the candies, with a field name, parent, points and Type.

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

The field data for candy types are stored in the JSON file ```candy.json```. New candies can be added just by appending it to this file.

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

The JSON file is parsed, instanciating each Candy type, and storing it in a hashtable. The ```type``` field is matched with the ```Type``` enum defined in the Candy class.
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

## Class diagram
![alt text](./etc/typeobjectpattern.urm.png "Type-Object pattern class diagram")

## Applicability
This pattern can be used when:

* We don’t know what types we will need up front.
* We want to be able to modify or add new types without having to recompile or change code.
* Only difference between the different 'types' of objects is the data, not the behaviour.
 
## Credits

* [Game Programming Patterns - Type Object](http://gameprogrammingpatterns.com/type-object.html)
* [Types as Objects Pattern](http://www.cs.sjsu.edu/~pearce/modules/patterns/analysis/top.htm)
