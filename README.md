
# Design pattern samples in Java.

## Build status:

![Build status](https://travis-ci.org/iluwatar/java-design-patterns.svg?branch=master)

## <a name="list-of-design-patterns">List of Design Patterns</a>

* Creational Patterns
	* [Abstract Factory](#abstract-factory)
	* [Builder](#builder)
	* [Factory Method](#factory-method)
	* [Prototype](#prototype)
	* [Singleton](#singleton)
* Structural Patterns
	* [Adapter](#adapter)
	* [Bridge](#bridge)
	* [Composite](#composite)
	* [Decorator](#decorator)
	* [Facade](#facade)
	* [Flyweight](#flyweight)
	* [Proxy](#proxy)
	* [Service Locator](#service-locator)
* Behavioral Patterns
	* [Chain of responsibility](#chain-of-responsibility)
	* [Command](#command)
	* [Interpreter](#interpreter)
	* [Iterator](#iterator)
	* [Mediator](#mediator)
	* [Memento](#memento)
	* [Observer](#observer)
	* [State](#state)
	* [Strategy](#strategy)
	* [Template method](#template-method)
	* [Visitor](#visitor)
* [Model-View-Presenter](#model-view-presenter)
* [Double Checked Locking](#double-checked-locking)
* [Servant](#servant)
* [Null Object](#null-object)

## <a name="abstract-factory">Abstract Factory</a> [&#8593;](#list-of-design-patterns)
**Intent:** Provide an interface for creating families of related or dependent objects without specifying their concrete classes.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/abstract-factory/etc/abstract-factory_1.png "Abstract Factory")

**Applicability:** Use the Abstract Factory pattern when
* a system should be independent of how its products are created, composed and represented
* a system should be configured with one of multiple families of products
* a family of related product objects is designed to be used together, and you need to enforce this constraint
* you want to provide a class library of products, and you want to reveal just their interfaces, not their implementations

**Real world examples:**
* [javax.xml.parsers.DocumentBuilderFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/parsers/DocumentBuilderFactory.html)

## <a name="builder">Builder</a> [&#8593;](#list-of-design-patterns)
**Intent:** Separate the construction of a complex object from its representation so that the same construction process can create different representations.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/builder/etc/builder_1.png "Builder")

**Applicability:** Use the Builder pattern when
* the algorithm for creating a complex object should be independent of the parts that make up the object and how they're assembled
* the construction process must allow different representations for the object that's constructed

**Real world examples:**
* [java.lang.StringBuilder](http://docs.oracle.com/javase/8/docs/api/java/lang/StringBuilder.html)

## <a name="factory-method">Factory Method</a> [&#8593;](#list-of-design-patterns)
**Intent:** Define an interface for creating an object, but let subclasses decide which class to instantiate. Factory Method lets a class defer instantiation to subclasses.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/factory-method/etc/factory-method_1.png "Factory Method")

**Applicability:** Use the Factory Method pattern when
* a class can't anticipate the class of objects it must create
* a class wants its subclasses to specify the objects it creates
* classes delegate responsibility to one of several helper subclasses, and you want to localize the knowledge of which helper subclass is the delegate

**Real world examples:**
* [java.util.Calendar#getInstance()](http://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html#getInstance%28%29)

## <a name="prototype">Prototype</a> [&#8593;](#list-of-design-patterns)
**Intent:** Specify the kinds of objects to create using a prototypical instance, and create new objects by copying this prototype.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/prototype/etc/prototype_1.png "Prototype")

**Applicability:** Use the Prototype pattern when a system should be independent of how its products are created, composed and represented; and
* when the classes to instantiate are specified at run-time, for example, by dynamic loading; or
* to avoid building a class hierarchy of factories that parallels the class hierarchy of products; or
* when instances of a class can have one of only a few different combinations of state. It may be more convenient to install a corresponding number of prototypes and clone them rather than instantiating the class manually, each time with the appropriate state

**Real world examples:**
* [java.lang.Object#clone()](http://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#clone%28%29)

## <a name="singleton">Singleton</a> [&#8593;](#list-of-design-patterns)
**Intent:** Ensure a class only has one instance, and provide a global point of access to it.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/singleton/etc/singleton_1.png "Singleton")

**Applicability:** Use the Singleton pattern when
* there must be exactly one instance of a class, and it must be accessible to clients from a well-known access point
* when the sole instance should be extensible by subclassing, and clients should be able to use an extended instance without modifying their code

**Typical Use Case:**
* the logging class
* managing a connection to a database
* file manager

**Real world examples:**
* [java.lang.Runtime#getRuntime()](http://docs.oracle.com/javase/8/docs/api/java/lang/Runtime.html#getRuntime%28%29)

## <a name="adapter">Adapter</a> [&#8593;](#list-of-design-patterns)
**Intent:** Convert the interface of a class into another interface the clients expect. Adapter lets classes work together that couldn't otherwise because of incompatible interfaces.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/adapter/etc/adapter_1.png "Adapter")

**Applicability:** Use the Adapter pattern when
* you want to use an existing class, and its interface does not match the one you need
* you want to create a reusable class that cooperates with unrelated or unforeseen classes, that is, classes that don't necessarily have compatible interfaces
* you need to use several existing subclasses, but it's impractical to adapt their interface by subclassing every one. An object adapter can adapt the interface of its parent class.

**Real world examples:**
* [java.util.Arrays#asList()](http://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#asList%28T...%29)

## <a name="bridge">Bridge</a> [&#8593;](#list-of-design-patterns)
**Intent:** Decouple an abstraction from its implementation so that the two can vary independently.


![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/bridge/etc/bridge_1.png "Bridge")

**Applicability:** Use the Bridge pattern when
* you want to avoid a permanent binding between an abstraction and its implementation. This might be the case, for example, when the implementation must be selected or switched at run-time.
* both the abstractions and their implementations should be extensible by subclassing. In this case, the Bridge pattern lets you combine the different abstractions and implementations and extend them independently
* changes in the implementation of an abstraction should have no impact on clients; that is, their code should not have to be recompiled.
* you have a proliferation of classes. Such a class hierarchy indicates the need for splitting an object into two parts. Rumbaugh uses the term "nested generalizations" to refer to such class hierarchies
* you want to share an implementation among multiple objects (perhaps using reference counting), and this fact should be hidden from the client. A simple example is Coplien's String class, in which multiple objects can share the same string representation.

## <a name="composite">Composite</a> [&#8593;](#list-of-design-patterns)
**Intent:** Compose objects into tree structures to represent part-whole hierarchies. Composite lets clients treat individual objects and compositions of objects uniformly.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/composite/etc/composite_1.png "Composite")

**Applicability:** Use the Composite pattern when
* you want to represent part-whole hierarchies of objects
* you want clients to be able to ignore the difference between compositions of objects and individual objects. Clients will treat all objects in the composite structure uniformly

**Real world examples:**
* [java.awt.Container](http://docs.oracle.com/javase/8/docs/api/java/awt/Container.html) and [java.awt.Component](http://docs.oracle.com/javase/8/docs/api/java/awt/Component.html)
* [Apache Wicket](https://github.com/apache/wicket) component tree, see [Component](https://github.com/apache/wicket/blob/91e154702ab1ff3481ef6cbb04c6044814b7e130/wicket-core/src/main/java/org/apache/wicket/Component.java) and [MarkupContainer](https://github.com/apache/wicket/blob/b60ec64d0b50a611a9549809c9ab216f0ffa3ae3/wicket-core/src/main/java/org/apache/wicket/MarkupContainer.java)

## <a name="decorator">Decorator</a> [&#8593;](#list-of-design-patterns)
**Intent:** Attach additional responsibilities to an object dynamically. Decorators provide a flexible alternative to subclassing for extending functionality.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/decorator/etc/decorator_1.png "Decorator")

**Applicability:** Use Decorator
* to add responsibilities to individual objects dynamically and transparently, that is, without affecting other objects
* for responsibilities that can be withdrawn
* when extension by subclassing is impractical. Sometimes a large number of independent extensions are possible and would produce an explosion of sublasses to support every combination. Or a class definition may be hidden or otherwise unavailable for subclassing

## <a name="facade">Facade</a> [&#8593;](#list-of-design-patterns)
**Intent:** Provide a unified interface to a set of interfaces in a subsystem. Facade defines a higher-level interface that makes the subsystem easier to use.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/facade/etc/facade_1.png "Facade")

**Applicability:** Use the Facade pattern when
* you want to provide a simple interface to a complex subsystem. Subsystems often get more complex  as they evolve. Most patterns, when applied, result in more and smaller classes. This makes the subsystem more reusable and easier to customize, but is also becomes harder to use for clients that don't need to customize it. A facade can provide a simple default view of the subsystem that is good enough for most clients. Only clients needing more customizability will need to look beyond the facade.
* there are many dependencies between clients and the implementation classes of an abstraction. Introduce a facade to decouple the subsystem from clients and other subsystems, thereby promoting subsystem independence and portability.
* you want to layer your subsystems. Use a facade to define an entry point to each subsystem level. If subsystems are dependent, the you can simplify the dependencies between them by making them communicate with each other solely through their facades

## <a name="flyweight">Flyweight</a> [&#8593;](#list-of-design-patterns)
**Intent:** Use sharing to support large numbers of fine-grained objects efficiently.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/flyweight/etc/flyweight_1.png "Flyweight")

**Applicability:** The Flyweight pattern's effectiveness depends heavily on how and where it's used. Apply the Flyweight pattern when all of the following are true
* an application uses a large number of objects
* storage costs are high because of the sheer quantity of objects
* most object state can be made extrinsic
* many groups of objects may be replaced by relatively few shared objects once extrinsic state is removed
* the application doesn't depend on object identity. Since flyweight objects may be shared, identity tests will return true for conceptually distinct objects.

**Real world examples:**
* [java.lang.Integer#valueOf(int)](http://docs.oracle.com/javase/8/docs/api/java/lang/Integer.html#valueOf%28int%29)

## <a name="proxy">Proxy</a> [&#8593;](#list-of-design-patterns)
**Intent:** Provide a surrogate or placeholder for another object to control access to it.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/proxy/etc/proxy_1.png "Proxy")

**Applicability:** Proxy is applicable whenever there is a need for a more versatile or sophisticated reference to an object than a simple pointer. here are several common situations in which the Proxy pattern is applicable

* a remote proxy provides a local representative for an object in a different address space.
* a virtual proxy creates expensive objects on demand.
* a protection proxy controls access to the original object. Protection proxies are useful when objects should have different access rights.

**Typical Use Case:**

* Control access to another object
* Lazy initialization
* implement logging
* facilitate network connection
* to count references to an object

**Real world examples:**
* [java.lang.reflect.Proxy](http://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Proxy.html)

## <a name="service-locator">Service Locator</a> [&#8593;](#list-of-design-patterns)
**Intent:** Encapsulate the processes involved in obtaining a service with a strong abstraction layer.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/service-locator/etc/service-locator.png "Proxy")

**Applicability:** The service locator pattern is applicable whenever we want to locate/fetch various services using JNDI which, typically, is a redundant and expensive lookup. The service Locator pattern addresses this expensive lookup by making use of caching techniques ie. for the very first time a particular service is requested, the service Locator looks up in JNDI, fetched the relavant service and then finally caches this service object. Now, further lookups of the same service via Service Locator is done in its cache which improves the performance of application to great extent.

**Typical Use Case:**

* When network hits are expensive and time consuming
* lookups of services are done quite frequently
* large number of services are being used

## <a name="chain-of-responsibility">Chain of responsibility</a> [&#8593;](#list-of-design-patterns)
**Intent:** Avoid coupling the sender of a request to its receiver by giving more than one object a chance to handle the request. Chain the receiving objects and pass the request along the chain until an object handles it.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/chain/etc/chain_1.png "Chain of Responsibility")

**Applicability:** Use Chain of Responsibility when
* more than one object may handle a request, and the handler isn't known a priori. The handler should be ascertained automatically
* you want to issue a request to one of several objects without specifying the receiver explicitly
* the set of objects that can handle a request should be specified dynamically

**Real world examples:**
* [java.util.logging.Logger#log()](http://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html#log%28java.util.logging.Level,%20java.lang.String%29)

## <a name="command">Command</a> [&#8593;](#list-of-design-patterns)
**Intent:** Encapsulate a request as an object, thereby letting you parameterize clients with different requests, queue or log requests, and support undoable operations.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/command/etc/command_1.png "Command")

**Applicability:** Use the Command pattern when you want to

* parameterize objects by an action to perform. You can express such parameterization in a procedural language with a callback function, that is, a function that's registered somewhere to be called at a later point. Commands are an object-oriented replacement for callbacks.
* specify, queue, and execute requests at different times. A Command object can have a lifetime independent of the original request. If the receiver of a request can be represented in an address space-independent way, then you can transfer a command object for the request to a different process and fulfill the request there
* support undo. The Command's execute operation can store state for reversing its effects in the command itself. The Command interface must have an added Unexecute operation that reverses the effects of a previous call to execute. Executed commands are stored in a history list. Unlimited-level undo and redo is achieved by traversing this list backwards and forwards calling unexecute and execute, respectively
* support logging changes so that they can be reapplied in case of a system crash. By augmenting the Command interface with load and store operations, you can keep a persistent log of changes. Recovering from a crash involves reloading logged commands from disk and re-executing them with the execute operation
* structure a system around high-level operations build on primitive operations. Such a structure is common in information systems that support transactions. A transaction encapsulates a set of changes to data. The Command pattern offers a way to model transactions. Commands have a common interface, letting you invoke all transactions the same way. The pattern also makes it easy to extend the system with new transactions

**Typical Use Case:**

* to keep a history of requests
* implement callback functionality
* implement the undo functionality

**Real world examples:**
* [java.lang.Runnable](http://docs.oracle.com/javase/8/docs/api/java/lang/Runnable.html)

## <a name="interpreter">Interpreter</a> [&#8593;](#list-of-design-patterns)
**Intent:** Given a language, define a representation for its grammar along with an interpreter that uses the representation to interpret sentences in the language.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/interpreter/etc/interpreter_1.png "Interpreter")

**Applicability:** Use the Interpreter pattern when there is a language to interpret, and you can represent statements in the language as abstract syntax trees. The Interpreter pattern works best when
* the grammar is simple. For complex grammars, the class hierarchy for the grammar becomes large and unmanageable. Tools such as parser generators are a better alternative in such cases. They can interpret expressions without building abstract syntax trees, which can save space and possibly time
* efficiency is not a critical concern. The most efficient interpreters are usually not implemented by interpreting parse trees directly but by first translating them into another form. For example, regular expressions are often transformed into state machines. But even then, the translator can be implemented by the Interpreter pattern, so the pattern is still applicable

## <a name="iterator">Iterator</a> [&#8593;](#list-of-design-patterns)
**Intent:** Provide a way to access the elements of an aggregate object sequentially without exposing its underlying representation.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/iterator/etc/iterator_1.png "Iterator")

**Applicability:** Use the Iterator pattern
* to access an aggregate object's contents without exposing its internal representation
* to support multiple traversals of aggregate objects
* to provide a uniform interface for traversing different aggregate structures

**Real world examples:**
* [java.util.Iterator](http://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html)

## <a name="mediator">Mediator</a> [&#8593;](#list-of-design-patterns)
**Intent:** Define an object that encapsulates how a set of objects interact. Mediator promotes loose coupling by keeping objects from referring to each other explicitly, and it lets you vary their interaction independently.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/mediator/etc/mediator_1.png "Mediator")

**Applicability:** Use the Mediator pattern when
* a set of objects communicate in well-defined but complex ways. The resulting interdependencies are unstructured and difficult to understand
* reusing an object is difficult because it refers to and communicates with many other objects
* a behavior that's distributed between several classes should be customizable without a lot of subclassing

## <a name="memento">Memento</a> [&#8593;](#list-of-design-patterns)
**Intent:** Without violating encapsulation, capture and externalize an object's internal state so that the object can be restored to this state later.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/memento/etc/memento_1.png "Memento")

**Applicability:** Use the Memento pattern when
* a snapshot of an object's state must be saved so that it can be restored to that state later, and
* a direct interface to obtaining the state would expose implementation details and break the object's encapsulation

**Real world examples:**
* [java.util.Date](http://docs.oracle.com/javase/8/docs/api/java/util/Date.html)

## <a name="observer">Observer</a> [&#8593;](#list-of-design-patterns)
**Intent:** Define a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/observer/etc/observer_1.png "Observer")

**Applicability:** Use the Observer pattern in any of the following situations

* when an abstraction has two aspects, one dependent on the other. Encapsulating these aspects in separate objects lets you vary and reuse them independently
* when a change to one object requires changing others, and you don't know how many objects need to be changed
* when an object should be able to notify other objects without making assumptions about who these objects are. In other words, you don't want these objects tightly coupled

**Typical Use Case:**

* changing in one object leads to a change in other objects

**Real world examples:**
* [java.util.Observer](http://docs.oracle.com/javase/8/docs/api/java/util/Observer.html)

## <a name="state">State</a> [&#8593;](#list-of-design-patterns)
**Intent:** Allow an object to alter its behavior when its internal state changes. The object will appear to change its class.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/state/etc/state_1.png "State")

**Applicability:** Use the State pattern in either of the following cases
* an object's behavior depends on its state, and it must change its behavior at run-time depending on that state
* operations have large, multipart conditional statements that depend on the object's state. This state is usually represented by one or more enumerated constants. Often, several operations will contain this same conditional structure. The State pattern puts each branch of the conditional in a separate class. This lets you treat the object's state as an object in its own right that can vary independently from other objects.

## <a name="strategy">Strategy</a> [&#8593;](#list-of-design-patterns)
**Intent:** Define a family of algorithms, encapsulate each one, and make them interchangeable. Strategy lets the algorithm vary independently from clients that use it.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/strategy/etc/strategy_1.png "Strategy")

**Applicability:** Use the Strategy pattern when
* many related classes differ only in their behavior. Stratefies provide a way to configure a class eith one of many behaviors
* you need different variants of an algorithm. for example, you migh define algorithms reflecting different space/time trade-offs. Strategies can be used when these variants are implemented as a class hierarchy of algorithms
* an algorithm uses data that clients shouldn't know about. Use the Strategy pattern to avoid exposing complex, algorithm-specific data structures
* a class defines many behaviors, and these appear as multiple conditional statements in its operations. Instead of many conditionals, move related conditional branches into their own Strategy class

## <a name="template-method">Template method</a> [&#8593;](#list-of-design-patterns)
**Intent:** Define the skeleton of an algorithm in an operation, deferring some steps to subclasses. Template method lets subclasses redefine certain steps of an algorithm without changing the algorithm's structure.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/template-method/etc/template-method_1.png "Template Method")

**Applicability:** The Template Method pattern should be used
* to implement the invariant parts of an algorithm once and leave it up to subclasses to implement the behavior that can vary
* when common behavior among subclasses should be factored and localized in a common class to avoid code duplication. This is good example of "refactoring to generalize" as described by Opdyke and Johnson. You first identify the differences in the existing code and then separate the differences into new operations. Finally, you replace the differing code with a template method that calls one of these new operations
* to control subclasses extensions. You can define a template method that calls "hook" operations at specific points, thereby permitting extensions only at those points

## <a name="visitor">Visitor</a> [&#8593;](#list-of-design-patterns)
**Intent:** Represent an operation to be performed on the elements of an object structure. Visitor lets you define a new operation without changing the classes of the elements on which it operates.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/visitor/etc/visitor_1.png "Visitor")

**Applicability:** Use the Visitor pattern when
* an object structure contains many classes of objects with differing interfaces, and you want to perform operations on these objects that depend on their concrete classes
* many distinct and unrelated operations need to be performed on objects in an object structure, and you want to avoid "polluting" their classes with these operations. Visitor lets you keep related operations together by defining them in one class. When the object structure is shared by many applications, use Visitor to put operations in just those applications that need them
* the classes defining the object structure rarely change, but you often want to define new operations over the structure. Changing the object structure classes requires redefining the interface to all visitors, which is potentially costly. If the object structure classes change often, then it's probably better to define the operations in those classes

**Real world examples:**
* [Apache Wicket](https://github.com/apache/wicket) component tree, see [MarkupContainer](https://github.com/apache/wicket/blob/b60ec64d0b50a611a9549809c9ab216f0ffa3ae3/wicket-core/src/main/java/org/apache/wicket/MarkupContainer.java)

## <a name="model-view-presenter">Model-View-Presenter</a> [&#8593;](#list-of-design-patterns)
**Intent:** Apply a "Separation of Concerns" principle in a way that allows developers to build and test user interfaces.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/model-view-presenter/etc/model-view-presenter_1.png "Model-View-Presenter")

**Applicability:** Use the Model-View-Presenter in any of the following situations
* when you want to improve the "Separation of Concerns" principle in presentation logic
* when a user interface development and testing is necessary.

## <a name="double-checked-locking">Double Checked Locking</a> [&#8593;](#list-of-design-patterns)
**Intent:** Reduce the overhead of acquiring a lock by first testing the locking criterion (the "lock hint") without actually acquiring the lock. Only if the locking criterion check indicates that locking is required does the actual locking logic proceed.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/double-checked-locking/etc/double_checked_locking_1.png "Double Checked Locking")

**Applicability:** Use the Double Checked Locking pattern when
* there is a concurrent access in object creation, e.g. singleton, where you want to create single instance of the same class and checking if it's null or not maybe not be enough when there are two or more threads that checks if instance is null or not.
* there is a concurrent access on a method where method's behaviour changes according to the some constraints and these constraint change within this method.

## <a name="servant">Servant</a> [&#8593;](#list-of-design-patterns)
**Intent:** Servant is used for providing some behavior to a group of classes. Instead of defining that behavior in each class - or when we cannot factor out this behavior in the common parent class - it is defined once in the Servant.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/servant/etc/servant-pattern.png "Servant")

**Applicability:** Use the Servant pattern when
* When we want some objects to perform a common action and don't want to define this action as a method in every class.

## <a name="null-object">Null Object</a> [&#8593;](#list-of-design-patterns)
**Intent:** In most object-oriented languages, such as Java or C#, references may be null. These references need to be checked to ensure they are not null before invoking any methods, because methods typically cannot be invoked on null references. Instead of using a null reference to convey absence of an object (for instance, a non-existent customer), one uses an object which implements the expected interface, but whose method body is empty. The advantage of this approach over a working default implementation is that a Null Object is very predictable and has no side effects: it does nothing.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/null-object/etc/test.png "Null Object")

**Applicability:** Use the Null Object pattern when
* You want to avoid explicit null checks and keep the algorithm elegant and easy to read.


# Frequently asked questions

**Q: What is the difference between State and Strategy patterns?**

While the implementation is similar they solve different problems. The State pattern deals with what state an object is in - it encapsulates state-dependent behavior. The Strategy pattern deals with how an object performs a certain task - it encapsulates an algorithm.

**Q: What is the difference between Strategy and Template Method patterns?**

In Template Method the algorithm is chosen at compile time via inheritance. With Strategy pattern the algorithm is chosen at runtime via composition.

**Q: What is the difference between Proxy and Decorator patterns?**

The difference is the intent of the patterns. While Proxy controls access to the object Decorator is used to add responsibilities to the object.



# How to contribute

**To add a new pattern** you need to do the following steps:

1. Fork the repository.
2. Implement the code changes in your fork. Remember to add sufficient comments documenting the implementation.
3. Create a simple class diagram from your example code.
4. Add description of the pattern in README.md and link to the class diagram.
5. Create a pull request.

**To edit the existing UML diagrams** you need one of the following:
- [GenMyModel](http://genmymodel.com/)
- [ObjectAid UML Explorer for Eclipse](http://www.objectaid.com/home)

**For inspiration** there is a good list of design patterns at [Wikipedia](http://en.wikipedia.org/wiki/Software_design_pattern).

**Links to patterns applied in real world applications** are welcome. The links should be added to the corresponding section of the `README.md`.



# Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](http://www.amazon.com/Design-Patterns-Elements-Reusable-Object-Oriented/dp/0201633612)
* [Effective Java (2nd Edition)](http://www.amazon.com/Effective-Java-Edition-Joshua-Bloch/dp/0321356683)
* [Java Generics and Collections](http://www.amazon.com/Java-Generics-Collections-Maurice-Naftalin/dp/0596527756/)
* [Let’s Modify the Objects-First Approach into Design-Patterns-First](http://edu.pecinovsky.cz/papers/2006_ITiCSE_Design_Patterns_First.pdf)
* [Pattern Languages of Program Design](http://www.amazon.com/Pattern-Languages-Program-Design-Coplien/dp/0201607344/ref=sr_1_1)



# License

This project is licensed under the terms of the MIT license.
