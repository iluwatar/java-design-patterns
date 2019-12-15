---
layout: pattern
title: Retry
folder: retry
permalink: /patterns/retry/
categories: Behavioral
tags:
  - Performance
---

## Retry / resiliency
Enables an application to handle transient failures from external resources.

## Intent
Transparently retry certain operations that involve communication with external
resources, particularly over the network, isolating calling code from the 
retry implementation details.

## Explanation
The `Retry` pattern consists retrying operations on remote resources over the 
network a set number of times. It closely depends on both business and technical
requirements: how much time will the business allow the end user to wait while
the operation finishes? What are the performance characteristics of the 
remote resource during peak loads as well as our application as more threads
are waiting for the remote resource's availability? Among the errors returned
by the remote service, which can be safely ignored in order to retry? Is the 
operation [idempotent](https://en.wikipedia.org/wiki/Idempotence)?

Another concern is the impact on the calling code by implementing the retry 
mechanism. The retry mechanics should ideally be completely transparent to the
calling code (service interface remains unaltered). There are two general 
approaches to this problem: from an enterprise architecture standpoint 
(**strategic**), and a shared library standpoint (**tactical**).

*(As an aside, one interesting property is that, since implementations tend to 
be configurable at runtime, daily monitoring and operation of this capability 
is shifted over to operations support instead of the developers themselves.)*

From a strategic point of view, this would be solved by having requests
be redirected to a separate intermediary system, traditionally an 
[ESB](https://en.wikipedia.org/wiki/Enterprise_service_bus), but more recently
a [Service Mesh](https://medium.com/microservices-in-practice/service-mesh-for-microservices-2953109a3c9a).

From a tactical point of view, this would be solved by reusing shared libraries 
like [Hystrix](https://github.com/Netflix/Hystrix)[1]. This is the type of 
solution showcased in the simple example that accompanies this *README*.

In our hypothetical application, we have a generic interface for all 
operations on remote interfaces:

```java
public interface BusinessOperation<T> {
  T perform() throws BusinessException;
}
```

And we have an implementation of this interface that finds our customers 
by looking up a database:

```java
public final class FindCustomer implements BusinessOperation<String> {
  @Override
  public String perform() throws BusinessException {
    ...
  }
}
```

Our `FindCustomer` implementation can be configured to throw 
`BusinessException`s before returning the customer's ID, thereby simulating a
'flaky' service that intermittently fails. Some exceptions, like the 
`CustomerNotFoundException`, are deemed to be recoverable after some 
hypothetical analysis because the root cause of the error stems from "some
database locking issue". However, the `DatabaseNotAvailableException` is 
considered to be a definite showstopper - the application should not attempt
to recover from this error.

We can model a 'recoverable' scenario by instantiating `FindCustomer` like this:

```java
final BusinessOperation<String> op = new FindCustomer(
    "12345",
    new CustomerNotFoundException("not found"),
    new CustomerNotFoundException("still not found"),
    new CustomerNotFoundException("don't give up yet!")
);
```

In this configuration, `FindCustomer` will throw `CustomerNotFoundException`
three times, after which it will consistently return the customer's ID 
(`12345`).

In our hypothetical scenario, our analysts indicate that this operation 
typically fails 2-4 times for a given input during peak hours, and that each 
worker thread in the database subsystem typically needs 50ms to 
"recover from an error". Applying these policies would yield something like
this:

```java
final BusinessOperation<String> op = new Retry<>(
    new FindCustomer(
        "1235",
        new CustomerNotFoundException("not found"),
        new CustomerNotFoundException("still not found"),
        new CustomerNotFoundException("don't give up yet!")
    ),
    5,
    100,
    e -> CustomerNotFoundException.class.isAssignableFrom(e.getClass())
);
```

Executing `op` *once* would automatically trigger at most 5 retry attempts,
with a 100 millisecond delay between attempts, ignoring any 
`CustomerNotFoundException` thrown while trying. In this particular scenario,
due to the configuration for `FindCustomer`, there will be 1 initial attempt 
and 3 additional retries before finally returning the desired result `12345`.

If our `FindCustomer` operation were instead to throw a fatal 
`DatabaseNotFoundException`, which we were instructed not to ignore, but 
more importantly we did *not* instruct our `Retry` to ignore, then the operation
would have failed immediately upon receiving the error, not matter how many 
attempts were left.

<br/><br/>

[1] Please note that *Hystrix* is a complete implementation of the *Circuit
Breaker* pattern, of which the *Retry* pattern can be considered a subset of.

## Class diagram
![alt text](./etc/retry.png "Retry")

## Applicability
Whenever an application needs to communicate with an external resource, 
particularly in a cloud environment, and if the business requirements allow it.

## Presentations
You can view Microsoft's article [here](https://docs.microsoft.com/en-us/azure/architecture/patterns/retry).

## Consequences
**Pros:** 

* Resiliency
* Provides hard data on external failures

**Cons:** 

* Complexity
* Operations maintenance

## Related Patterns

* [Circuit Breaker](https://martinfowler.com/bliki/CircuitBreaker.html)
