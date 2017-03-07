---
layout: page
title: FAQ
permalink: /faq/
icon: fa-question
page-index: 5
---

### Q1: What is the difference between State and Strategy patterns? {#Q1}

While the implementation is similar they solve different problems. The State
pattern deals with what state an object is in - it encapsulates state-dependent
behavior.
The Strategy pattern deals with how an object performs a certain task - it
encapsulates an algorithm.

### Q2: What is the difference between Strategy and Template Method patterns? {#Q2}

In Template Method the algorithm is chosen at compile time via inheritance.
With Strategy pattern the algorithm is chosen at runtime via composition.

### Q3: What is the difference between Proxy and Decorator patterns? {#Q3}

The difference is the intent of the patterns. While Proxy controls access to
the object Decorator is used to add responsibilities to the object.

### Q4: What is the difference between Chain of Responsibility and Intercepting Filter patterns? {#Q4}

While the implementations look similar there are differences. The Chain of
Responsibility forms a chain of request processors and the processors are then
executed one by one until the correct processor is found. In Intercepting
Filter the chain is constructed from filters and the whole chain is always
executed.

### Q5: What is the difference between Visitor and Double Dispatch patterns? {#Q5}

The Visitor pattern is a means of adding a new operation to existing classes.
Double dispatch is a means of dispatching function calls with respect to two
polymorphic types, rather than a single polymorphic type, which is what
languages like C++ and Java _do not_ support directly.

### Q6: What are the differences between Flyweight and Object Pool patterns? {#Q6}

They differ in the way they are used.

Pooled objects can simultaneously be used by a single "client" only. For that,
a pooled object must be checked out from the pool, then it can be used by a
client, and then the client must return the object back to the pool. Multiple
instances of identical objects may exist, up to the maximal capacity of the
pool.

In contrast, a Flyweight object is singleton, and it can be used simultaneously
by multiple clients.

As for concurrent access, pooled objects can be mutable and they usually don't
need to be thread safe, as typically, only one thread is going to use a
specific instance at the same time. Flyweight must either be immutable (the
best option), or implement thread safety.

As for performance and scalability, pools can become bottlenecks, if all the
pooled objects are in use and more clients need them, threads will become
blocked waiting for available object from the pool. This is not the case with
Flyweight.

### Q7: What are the differences between FluentInterface and Builder patterns? {#Q7}

Fluent interfaces are sometimes confused with the Builder pattern, because they share method chaining and a fluent usage. However, fluent interfaces are not primarily used to create shared (mutable) objects, but to configure complex objects without having to respecify the target object on every property change. 

### Q8: What is the difference between java.io.Serialization and Memento pattern? {#Q8}

Memento is typically used to implement rollback/save-point support. Example we might want to mark the state of an object at a point in time, do some work and then decide to rollback to the previous state. 

On the other hand serialization may be used as a tool to save the state of an object into byte[] and preserving the contents in memory or disk. When someone invokes the memento to revert object's previous state then we can deserialize the information stored and recreate previous state. 

So Memento is a pattern and serialization is a tool that can be used to implement this pattern. Other ways to implement the pattern can be to clone the contents of the object and keep track of those clones.
