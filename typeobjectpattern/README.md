---
title: Type-Object
category: Behavioral
language: en
tags:
 - Game programming
 - Extensibility
---

## Intent
As explained in the book Game Programming Patterns by Robert Nystrom, type object pattern helps in

> Allowing flexible creation of new “classes” by creating a single class, each instance of which represents a different type of object

## Explanation

Real-world example

>Say, we are working on a game which has a hero and many monsters which are going to attack the hero. These monsters have certain attributes like attack, points etc. and come in different 'breeds' like zombie or ogres. The obvious answer is to have a base Monster class which has some fields and methods, which may be overriden by subclasses like the Zombie or Ogre class. But as we continue to build the game, there may be more and more breeds of monsters added and certain attributes may need to be changed in the existing monsters too. The OOP solution of inheriting from the base class would not be an efficient method in this case.
>Using the type-object pattern, instead of creating many classes inheriting from a base class, we have 1 class with a field which represents the 'type' of object. This makes the code cleaner and object instantiation also becomes as easy as parsing a json file with the object properties.

In plain words

>This pattern is useful anytime you need to define a variety of different “kinds” of things, but baking the kinds into your language’s type system is too rigid. In particular, it’s useful when either of these is true:

**Programmatic Example**

With that game design in mind, we fire up our text editor and start coding. According to the design, a `dragon` is a kind of `monster`, a troll is another kind, and so on with the other breeds. Thinking object-oriented, that leads us to a `Monster` base class:

```java

    class Monster
    {
    public:
  virtual ~Monster() {}
  virtual const char* getAttack() = 0;

protected:
  Monster(int startingHealth)
  : health_(startingHealth)
  {}

private:
  int health_; // Current health.
};
```
The public getAttack() function lets the combat code get the string that should be displayed when the monster attacks the hero. Each derived breed class will override this to provide a different message.

The constructor is protected and takes the starting health for the monster. We’ll have derived classes for each breed that provide their own public constructors that call this one, passing in the starting health that is appropriate for that breed.

Now let’s see a couple of breed subclasses:

```java
class Dragon : public Monster
{
public:
  Dragon() : Monster(230) {}

  virtual const char* getAttack()
  {
    return "The dragon breathes fire!";
  }
};

class Troll : public Monster
{
public:
  Troll() : Monster(48) {}

  virtual const char* getAttack()
  {
    return "The troll clubs you!";
  }
};

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
