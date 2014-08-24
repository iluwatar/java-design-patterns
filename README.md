
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

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/singleton/etc/singleton.jpg "Singleton")

**Applicability:** Use the Singleton pattern when
* the must be exactly one instance of a class, and it must be accessible to clients from a well-known access point
* when the sole instance should be extensible by subclassing, and clients should be able to use an extended instance without modifying their code

##Adapter
**Intent:** Convert the interface of a class into another interface clients expect. Adapter lets classes work together that couldn't otherwise because of incompatible interfaces.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/adapter/etc/adapter.jpg "Adapter")

**Applicability:** Use the Adapter pattern when
* you want to use an existing class, and its interface does not match the one you need
* you want to create a reusable class that cooperates with unrelated or unforeseen classes, that is, classes that don't necessarily have compatible interfaces
* you need to use several existing subclasses, but it's impractical to adapt their interface by subclassing every one. An object adapter can adapt the interface of its parent class.

##Bridge
**Intent:** Decouple an abstraction from its implementationso that the two can vary independently.


![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/bridge/etc/bridge.jpg "Bridge")

**Applicability:** Use the Bridge pattern when
* you want to avoid a permanent binding between an abstraction and its implementation. This might be the case, for example, when the implementation must be selected or switched at run-time.
* both the abstractions and their implementations should be extensible by subclassing. In this case, the Bridge pattern lets you combine the different abstractions and implementations and extend them independently
* changes in the implementation of an abstraction should have no impact on clients; that is, their code should not have to be recompiled.
* you have a proliferation of classes. Such a class hierarchy indicates the need for splitting an object into two parts. Rumbaugh uses the term "nested generalizations" to refer to such class hierarchies
* you want to share an implementation among multiple objects (perhaps using reference counting), and this fact should be hidden from the client. A simple example is Coplien's String class, in which multiple objects can share the same string representation.

##Composite
**Intent:** Compose objects into tree structures to represent part-whole hierarchies. Composite lets clients treat individual objects and compositions of objects uniformly.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/composite/etc/composite.jpg "Composite")

**Applicability:** Use the Composite pattern when
* you want to represent part-whole hierarchies of objects
* you want clients to be able to ignore the difference between compositions of objects and individual objects. Clients will treat all objects in the composite structure uniformly

##Decorator
**Intent:** Attach additional responsibilities to an object dynamically. Decorators provide a flexible alternative to subclassing for extending functionality.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/decorator/etc/decorator.jpg "Decorator")

**Applicability:** Use Decorator
* to add responsibilities to individual objects dynamically and transparently, that is, without affecting other objects
* for responsibilities that can be withdrawn
* when extension by subclassing is impractical. Sometimes a large number of independent extensions are possible and would produce an explosion of sublasses to support every combination. Or a class definition may be hidden or otherwise unavailable for subclassing

##Facade
**Intent:** Provide a unified interface to a set of interfaces in a subsystem. Facade defines a higher-level interface that makes the subsystem easier to use.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/facade/etc/facade.jpg "Facade")

**Applicability:** Use the Facade pattern when
* you want to provide a simple interface to a complex subsystem. Subsystems often get more complex  as they evolve. Most patterns, when applied, result in more and smaller classes. This makes the subsystem more reusable and easier to customize, but is also becomes harder to use for clients that don't need to customize it. A facade can provide a simple default view of the subsystem that is good enough for most clients. Only clients needing more customizability will need to look beyond the facade.
* there are many dependencies between clients and the implementation classes of an abstraction. Introduce a facade to decouple the subsystem from clients and other subsystems, thereby promoting subsystem independence and portability.
* you want to layer your subsystems. Use a facade to define an entry point to each subsystem level. If subsystems are dependent, the you can simplify the dependencies between them by making them communicate with each other solely through their facades

##Flyweight
**Intent:** Use sharing to support large numbers of fine-grained objects efficiently.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/flyweight/etc/flyweight.jpg "Flyweight")

**Applicability:** The Flyweight pattern's effectiveness depends heavily on how and where it's used. Apply the Flyweight pattern when all of the following are true
* an application uses a large number of objects
* storage costs are high because of the sheer quantity of objects
* most object state can be made extrinsic
* many groups of objects may be replaced by relatively few shared objects once extrinsic state is removed
* the application doesn't depend on object identity. Since flyweight objects may be shared, identity tests will return true for conceptually distinct objects.

##Proxy
**Intent:** Provide a surrogate or placeholder for another object to control access to it.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/proxy/etc/proxy.jpg "Proxy")

**Applicability:** Proxy is applicable whenever there is a need for a more versatile or sophisticated reference to an object than a simple pointer. here are several common situations in which the Proxy pattern is applicable
* a remote proxy provides a local representative for an object in a different address space.
* a virtual proxy creates expensive objects on demand.
* a protection proxy controls access to the original object. Protection proxies are useful when objects should have different access rights.

##Chain of responsibility
**Intent:** Avoid coupling the sender of a request to its receiver by giving more than one object a chance to handle the request. Chain the receiving objects and pass the request along the chain until an object handles it.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/chain/etc/chain.jpg "Chain of Responsibility")

**Applicability:** Use Chain of Responsibility when
* more than one object may handle a request, and the handler isn't known a priori. The handler should be ascertained automatically
* you want to issue a request to one of several objects without specifying the receiver explicitly
* the set of objects that can handle a request should be specified dynamically

##Command
**Intent:** Encapsulate a request as an object, thereby letting you parameterize clients with different requests, queue or log requests, and support undoable operations.

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/command/etc/command.jpg "Command")

**Applicability:** Use the Command pattern when you want to
* parameterize objects by an action to perform. You can express such parameterization in a procedural language with a callback function, that is, a function that's registered somewhere to be called at a later point. Commands are an object-oriented replacement for callbacks.
* specify, queue, and execute requests at different times. A Command object can have a lifetime independent of the original request. If the receiver of a request can be represented in an address space-independent way, then you can transfer a command object for the request to a different process and fulfill the request there
* support undo. The Command's execute operation can store state for reversing its effects in the command itself. The Command interface must have an added Unexecute operation that reverses the effects of a previous call to execute. Executed commands are stored in a history list. Unlimited-level undo and redo is achieved by traversing this list backwards and forwards calling unexecute and execute, respectively
* support logging changes so that they can be reapplied in case of a system crash. By augmenting the Command interface with load and store operations, you can keep a persistent log of changes. Recovering from a crash involves reloading logged commands from disk and re-executing them with the execute operation
* structure a system around high-level operations build on primitive operations. Such a structure is common in information systems that support transactions. A transaction encapsulates a set of changes to data. The Command pattern offers a way to model transactions. Commands have a common interface, letting you invoke all transactions the same way. The pattern also makes it easy to extend the system with new transactions

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
