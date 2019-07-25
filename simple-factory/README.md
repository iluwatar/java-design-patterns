


Simple Factory Pattern
========================================
|layout|title|folder|permalink|categories|tags|
|---|---|---|---|---|---|
|pattern|Simple Factory|simple-factory|/patterns/simple-factory/|Structural|java|

## Intent
[alt text] (simple-factory/etc/SimpleFactory.jpg "Simple-Factory")
[alt text] (simple-factory/etc/seq_SimpleFactory.jpg "Sequence Simple-Factory")


Model Motivation
--------------------

Considering a simple software application scenario, a software system can provide multiple buttons with different appearance (such as circular button, rectangular button, diamond button, etc.).

These buttons all originate from the same base class, but after inheriting the base class, different subclasses modify some attributes so that they can present different appearance. If we want to use these buttons, we don't need to know the name of these specific button classes, just need to know a parameter representing the button class, and mention it. For a convenient method to call, the parameter can be passed into the method to return a corresponding button object, at this time, you can use the simple factory mode.



Schema Definition
--------------------

Simple Factory Pattern: Also known as Static Factory Method, it belongs to the class creation pattern. In simple factory mode, instances of different classes can be returned according to different parameters. The Simple Factory pattern specifically defines a class to create instances of other classes, which usually have a common parent class.




Model structure
--------------------

The simple factory model includes the following roles:



- Factory: Factory role

The factory role is responsible for implementing the internal logic for creating all instances

- Product: abstract product roles

The abstract product role is the parent class of all objects created and is responsible for describing the common interfaces shared by all instances.

- ConcreteProduct: Specific Product Roles

A specific product role is to create a goal, and all created objects act as instances of a specific class of that role.




Code analysing
--------------------
interface Fruit

```java
public interface Fruit {
    void whatIm();
}
```

class Apple 

```java
public class Apple implements Fruit {
    @Override
    public void whatIm() {
        //apple
    }
}
```

class Pear

```java
public class Pear implements Fruit {
    @Override
    public void whatIm() {
        //梨
    }
}
```
class FruitFactory

```java
public class FruitFactory {

    public Fruit createFruit(String type) {

        if (type.equals("apple")) {//produce apple
            return new Apple();
        } else if (type.equals("pear")) {//produce pear
            return new Pear();
        }

        return null;
    }
}
```

product use

```java
 FruitFactory mFactory = new FruitFactory();
 Apple apple = (Apple) mFactory.createFruit("apple");//get apple
 Pear pear = (Pear) mFactory.createFruit("pear");//get pear

```



Linenos:




pattern analysis
--------------------



- Separating the creation of objects from the business processing of the objects themselves can reduce the coupling degree of the system and make it relatively easy to modify both.

- When calling the factory method of the factory class, because the factory method is static and convenient to use, it can be called directly by the class name, and only need to pass in a simple parameter. In practical development, the parameters can be saved in the configuration file of XML and other formats when calling, and the parameters can be modified. There is no need to modify any source code.

- The biggest problem of the simple factory model is that the responsibility of the factory class is relatively heavy. Adding new products requires modifying the judgment logic of the factory class, which is contrary to the principle of opening and closing.

- The point of the simple factory pattern is that when you need something, you just need to pass in a correct parameter to get the object you need without knowing the details of its creation.



Example
--------------------
(Brief)




Advantages of Simple Factory Model
--------------------



- The factory class contains the necessary judgment logic, which can decide when to create an instance of which product class. The client can exempt the responsibility of directly creating the product object, but only "consume" the product. Simple factory mode achieves the division of responsibility by this way. It provides a special factory class for creating. Object.

- The client does not need to know the class name of the specific product class, but only the corresponding parameters of the specific product class. For some complex class names, simple factory mode can reduce the user's memory.

- By introducing configuration files, new product classes can be replaced and added without modifying any client code, which improves the flexibility of the system to a certain extent.



Disadvantages of Simple Factory Model
--------------------



- Because the factory class centralizes all the product creation logic, once it does not work properly, the whole system will be affected.

- Using simple factory mode will increase the number of classes in the system, and increase the complexity and difficulty of understanding the system in a certain program.

- System expansion is difficult, once new products are added, factory logic has to be modified. When there are many types of products, factory logic may be too complex, which is not conducive to system expansion and maintenance.

- Simple factory model uses static factory method, which makes it impossible for factory roles to form hierarchical structure based on inheritance.



Applicable environment

-----------------------------------------

Simple factory mode can be used in the following cases:



- The factory class is responsible for creating fewer objects: because fewer objects are created, the business logic in the factory method is not too complex.

- The client only knows the parameters passed into the factory class, and does not care about how to create the object: the client does not need to care about the creation details, or even the class name, but only knows the parameters corresponding to the type.



Model application
--------------------

1. Simple factory patterns are widely used in JDK class libraries, such as the tool class java. text. DateFormat, which is used to format a local date or time.



:



Public final static DateFormat getDateInstance ();

Public final static DateFormat getDateInstance (int style);

Public final static DateFormat getDateInstance (int style, Locale)

Locale;



2. Java Encryption Technology



Get the key generator of different encryption algorithms::



KeyGenerator keyGen = KeyGenerator. getInstance ("DESede");



Create a password::



Cipher CP = Cipher. getInstance ("DESede");



summary
--------------------



- Creative schemas abstract the instantiation process of classes and separate the creation of objects from the use process of objects.

- Simple factory pattern, also known as static factory method pattern, belongs to class creation pattern. In simple factory mode, instances of different classes can be returned according to different parameters. The Simple Factory pattern specifically defines a class to create instances of other classes, which usually have a common parent class.

- The simple factory pattern consists of three roles: the factory role is responsible for implementing the internal logic of creating all instances; the abstract product role is the parent class of all objects created and is responsible for describing the common interfaces shared by all instances; the specific product role is to create the goal, and all created objects act as one of the roles. Instances of body classes.

- The point of the simple factory pattern is that when you need something, you just need to pass in a correct parameter to get the object you need without knowing the details of its creation.

- The greatest advantage of simple factory mode is to realize the separation of object creation and object usage. The creation of object is entrusted to the special factory class. But its greatest disadvantage is that the factory class is not flexible enough. Adding new specific products needs to modify the judgment logic code of the factory class. Moreover, when there are more products, the factory method. The code will be very complex.

- The application of simple factory mode includes: the factory class is responsible for creating fewer objects; the client only knows the parameters passed into the factory class, and does not care about how to create objects.
