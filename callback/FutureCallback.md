---
layout: pattern
title: Future Callback
folder: callback
permalink: /patterns/callback/
categories: Other
tags:
 - Java
 - Difficulty-Beginner
 - Functional
 - Idiom
---

## Also known as
[Promises](https://en.wikipedia.org/wiki/Futures_and_promises) , [CompletableFuture](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html) , FutureTask

## Intent
Java Future provides a cancel() method to cancel the associated Callable task. This is an overloaded version of the get() method, where we can specify the time to wait for the result. It’s useful to avoid a current thread getting blocked for a longer time.

![alt text](./etc/callback.png "Callback")

## Applicability
Use the Callback pattern when

* when some arbitrary synchronous or asynchronous action must be performed after execution of some defined activity.
* when we want to cancel task in future.

* You can end up in the callback hell. With Future your code stays more composable and evolutive. By the way, Java 8 is also shipped with a whole new API called CompletableFuture that makes the usage of asynchronism more expressive and relies on Future.
* With callbacks, you enforce the thread in which the listener is called (in your case the UI thread). But what if the user doesn't want the callback to be run in the UI thread ? It's too late because this dependency has been hardcoded in your code and the caller is stuck. With Future the caller is free to choose the thread he wants to handle the result in.
* Callbacks can create a lot of garbage threads if done carelessly because each listener has to be invoked in another thread (this is, however, not the case in your example since you always use the UI thread). With Future only one thread is involved.
  [source](https://softwareengineering.stackexchange.com/a/332450)

### [Examples](https://stackoverflow.com/a/43767971/2685454) 

```
Looking at the documentation you linked to it looks like the main difference between the
Future and the Callback lies in who initiates the "request is finished, what now?" question.

Let's say we have a customer C and a baker B. And C is asking B to make him a nice cookie.
Now there are 2 possible ways the baker can return the delicious cookie to the customer.

* Future
The baker accepts the request and tells the customer: Ok, when I'm finished I'll place your cookie here on the counter.
(This agreement is the Future.)

In this scenario, the customer is responsible for checking the counter (Future) to see if the baker has finished his cookie or not.

blocking The customer stays near the counter and looks at it until the cookie is put there (Future.get()) 
or the baker puts an apology there instead (Error : Out of cookie dough).

non-blocking The customer does some other work, and once in a while checks if the cookie is waiting for him on the counter
(Future.isDone()). If the cookie is ready, the customer takes it (Future.get()).

* Callback
In this scenario the customer, after ordering his cookie, tells the baker: When my cookie is ready please give it to my pet 
robot dog here, he'll know what to do with it (This robot is the Callback).

Now the baker when the cookie is ready gives the cookie to the dog and tells him to run back to it's owner. 
The baker can continue baking the next cookie for another customer.

The dog runs back to the customer and starts wagging it's artificial tail to make the customer aware that his cookie is ready.

Notice how the customer didn't have any idea when the cookie would be given to him, nor was he actively polling the baker
to see if it was ready.

That's the main difference between the 2 scenario's. Who is responsible for initiating the "your cookie is ready,
what do you want to do with it?" question. With the Future, the customer is responsible for checking when it's ready,
either by actively waiting, or by polling every now and then. In case of the callback, the baker will call back 
to the provided function.

```
