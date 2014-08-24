
#Design pattern samples in Java.


##Abstract Factory
**Intent:** Provide an interface for creating families of related or dependent objects without specifying their concrete classes.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/abstract-factory/etc/abstract-factory.jpg "Abstract Factory")

**Applicability:** Use the Abstract Factory pattern when
* a system should be independent of how its products are created, composed and represented
* a system should be configured with one of multiple families of products
* a family of related product objects is designed to be used together, and you need to enforce this constraint
* you want to provide a class library of products, and you want to reveal just their interfaces, not their implementations

##Builder
**Intent:** Separate the construction of a complex object from its representation so that the same construction process can create different representations.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/builder/etc/builder.jpg "Builder")

**Applicability:** Use the Builder pattern when
* the algorithm for creating a complex object should be independent of the parts that make up the object and how they're assembled
* the construction process must allow different representations for the object that's constructed

##Factory Method
**Intent:** Define an interface for creating an object, but let subclasses decide which class to instantiate. Factory Method lets a class defer instantiation to subclasses.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/factory-method/etc/factory-method.jpg "Factory Method")

**Applicability:** Use the Factory Method pattern when
* a class can't anticipate the class of objects it must create
* a class wants its subclasses to specify the objects it creates
* classes delegate responsibility to one of several helper subclasses, and you want to localize the knowledge of which helper subclass is the delegate

##Prototype
**Intent:** Specify the kinds of objects to create using a prototypical instance, and create new objects by copying this prototype.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/prototype/etc/prototype.jpg "Prototype")

**Applicability:** Use the Prototype pattern when a system should be independent of how its products are created, composed and represented; and
* when the classes to instantiate are specified at run-time, for example, by dynamic loading; or
* to avoid building a class hierarchy of factories that parallels the class hierarchy of products; or
* when instances of a class can have one of only a few different combinations of state. It may be more convenient to install a corresponding number of prototypes and clone them rather than instantiating the class manually, each time with the appropriate state

##Singleton
**Intent:** Ensure a class only has one instance, and provide a global point of access to it.

##Adapter
**Intent:** Convert the interface of a class into another interface clients expect. Adapter lets classes work together that couldn't otherwise because of incompatible interfaces.

##Bridge
**Intent:** Decouple an abstraction from its implementationso that the two can vary independently.

##Composite
**Intent:** Compose objects into tree structures to represent part-whole hierarchies. Composite lets clients treat individual objects and compositions of objects uniformly.

##Decorator
**Intent:** Attach additional responsibilities to an object dynamically. Decorators provide a flexible alternative to subclassing for extending functionality.

##Facade
**Intent:** Provide a unified interface to a set of interfaces in a subsystem. Facade defines a higher-level interface that makes the subsystem easier to use.

##Flyweight
**Intent:** Use sharing to support large numbers of fine-grained objects efficiently.

##Proxy
**Intent:** Provide a surrogate or placeholder for another object to control access to it.

##Chain of responsibility
**Intent:** Avoid coupling the sender of a request to its receiver by giving more than one object a chance to handle the request. Chain the receiving objects and pass the request along the chain until an object handles it.

##Command
**Intent:** Encapsulate a request as an object, thereby letting you parameterize clients with different requests, queue or log requests, and support undoable operations.

##Interpreter
**Intent:** Given a language, define a representation for its grammar along with an interpreter that uses the representation to interpret sentences in the language.

##Iterator
**Intent:** Provide a way to access the elements of an aggregate object sequentially without exposing its underlying representation.

##Mediator
**Intent:** Define an object that encapsulates how a set of objects interact. Mediator promotes loose coupling by keeping objects from referring to each other explicitly, and it lets you vary their interaction independently.

##Memento
**Intent:** Without violating encapsulation, capture and externalize an object's internal state so that the object can be restored to this state later.

##Observer
**Intent:** Define a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.

##State
**Intent:** Allow an object to alter its behavior when its internal state changes. The object will appear to change its class.

##Strategy
**Intent:** Define a family of algorithms, encapsulate each one, and make them interchangeable. Strategy lets the algorithm vary independently from clients that use it.

##Template method
**Intent:** Define the skeleton of an algorithm in an operation, deferring some steps to subclasses. Template method lets subclasses redefine certain steps of an algorithm without changing the algorithm's structure.

##Visitor
**Intent:** Represent an operation to be performed on the elements of an object structure. Visitor lets you define a new operation without changing the classes of the elements on which it operates.
