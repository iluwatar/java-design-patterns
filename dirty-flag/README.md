---
title: "Dirty Flag Pattern in Java: Optimizing Performance with Change Tracking"
shortTitle: Dirty Flag
description: "Learn about the Dirty Flag design pattern in Java for efficient state tracking and resource management. Avoid unnecessary computations with practical examples and use cases."
category: Behavioral
language: en
tag:
  - Game programming
  - Performance
  - Resource management
  - State tracking
---

## Also known as

* Change Tracking
* Is-Modified Flag

## Intent of Dirty Flag Design Pattern

The Dirty Flag design pattern is employed to avoid unnecessary computations or resource-heavy operations by maintaining a boolean flag that tracks whether the state of an object has changed ('dirty') or remains unchanged ('clean'). This flag, when set, indicates that a particular operation, such as recalculating or refreshing data, needs to be performed again to reflect the updated state.

## Detailed Explanation of Dirty Flag Pattern with Real-World Examples

Real-world example

> Imagine a library with an electronic catalog system that tracks which books are checked out and returned. Each book record has a "dirty flag" that gets marked whenever a book is checked out or returned. At the end of each day, the library staff reviews only those records marked as "dirty" to update the physical inventory, instead of checking every single book in the library. This system significantly reduces the effort and time required for daily inventory checks by focusing only on the items that have changed status, analogous to how the Dirty Flag design pattern minimizes resource-intensive operations by performing them only when necessary.

In plain words

> The Dirty Flag design pattern minimizes unnecessary operations by using a flag to track when an object's state has changed and an update is needed.

Wikipedia says

> A dirty bit or modified bit is a bit that is associated with a block of computer memory and indicates whether the corresponding block of memory has been modified. The dirty bit is set when the processor writes to (modifies) this memory. The bit indicates that its associated block of memory has been modified and has not been saved to storage yet. When a block of memory is to be replaced, its corresponding dirty bit is checked to see if the block needs to be written back to secondary memory before being replaced or if it can simply be removed. Dirty bits are used by the CPU cache and in the page replacement algorithms of an operating system.

## Programmatic Example of Dirty Flag Pattern in Java

The `DataFetcher` class is responsible for fetching data from a file. It has a dirty flag that indicates whether the data in the file has changed since the last fetch.

```java
public class DataFetcher {
    private long lastFetched;
    private boolean isDirty = true;
    // Other properties and methods...
}
```

The `DataFetcher` class has a fetch method that checks the dirty flag before fetching data. If the flag is true, it fetches the data from the file and sets the flag to false. If the flag is false, it returns the previously fetched data.

```java
public List<String> fetch() {
    if (!isDirty) {
        return data;
    }
    data = fetchFromDatabase();
    isDirty = false;
    return data;
}
```

The `World` class uses the `DataFetcher` to fetch data. It has a `fetch` method that calls the `fetch` method of the `DataFetcher`.

```java
public class World {
    private final DataFetcher fetcher = new DataFetcher();
  
    public List<String> fetch() {
        return fetcher.fetch();
    }
}
```

The `App` class contains the `main` method that demonstrates the use of the Dirty Flag pattern. It creates a `World` object and fetches data from it in a loop. The `World` object uses the `DataFetcher` to fetch data, and the `DataFetcher` only fetches data from the file if the dirty flag is true.

```java
@Slf4j
public class App {

  public void run() {
    final var executorService = Executors.newSingleThreadScheduledExecutor();
    executorService.scheduleAtFixedRate(new Runnable() {
      final World world = new World();

      @Override
      public void run() {
        var countries = world.fetch();
        LOGGER.info("Our world currently has the following countries:-");
        countries.stream().map(country -> "\t" + country).forEach(LOGGER::info);
      }
    }, 0, 15, TimeUnit.SECONDS); // Run at every 15 seconds.
  }

  public static void main(String[] args) {
    var app = new App();
    app.run();
  }
}
```

The program output is as follows:

```
12:06:02.612 [pool-1-thread-1] INFO com.iluwatar.dirtyflag.DataFetcher -- world.txt is dirty! Re-fetching file content...
12:06:02.615 [pool-1-thread-1] INFO com.iluwatar.dirtyflag.App -- Our world currently has the following countries:-
12:06:02.616 [pool-1-thread-1] INFO com.iluwatar.dirtyflag.App -- 	UNITED_KINGDOM
12:06:02.616 [pool-1-thread-1] INFO com.iluwatar.dirtyflag.App -- 	MALAYSIA
12:06:02.616 [pool-1-thread-1] INFO com.iluwatar.dirtyflag.App -- 	UNITED_STATES
```

## When to Use the Dirty Flag Pattern in Java

* When an operation is resource-intensive and only necessary after certain changes have occurred.
* In scenarios where checking for changes is significantly cheaper than performing the operation itself, enhancing cost-effectiveness.
* Within systems where objects maintain state that is expensive to update and the updates are infrequent, promoting performance efficiency.

## Dirty Flag Pattern Java Tutorials

* [89: Design Patterns: Dirty Flag (TakeUpCode)](https://www.takeupcode.com/podcast/89-design-patterns-dirty-flag/)

## Real-World Applications of Dirty Flag Pattern in Java

* Graphic rendering engines to update only parts of the scene that have changed, utilizing the Dirty Flag pattern for efficient rendering.
* Web applications for partial page rendering or caching strategies.
* Database applications for tracking changes in datasets to minimize write operations, ensuring efficient database management.

## Benefits and Trade-offs of Dirty Flag Pattern

Benefits:

* Reduces computational and resource overhead by avoiding unnecessary operations, leading to performance gains.
* Can significantly improve performance in systems where operations are costly and changes are infrequent, fostering system optimization.
* Simplifies the decision-making process about when to perform certain operations, aiding in effective resource allocation.

Trade-offs:

* Introduces complexity by adding state management responsibility to the system.
* Requires diligent management of the flag to ensure it accurately reflects the state changes, avoiding stale or incorrect data.
* Potentially increases the risk of bugs related to improper flag resetting, impacting system reliability.

## Related Java Design Patterns

* [Observer](https://java-design-patterns.com/patterns/observer/): Can be used in conjunction to notify interested parties when the dirty flag is set or cleared.
* [Memento](https://java-design-patterns.com/patterns/memento/): Useful for storing the previous state of an object, which can work hand in hand with dirty flag logic to revert to clean states.
* [Command](https://java-design-patterns.com/patterns/command/): Commands can set the dirty flag when executed, indicating a change in state that requires attention.

## References and Credits

* [Game Programming Patterns](https://amzn.to/3PUzbgu)
* [J2EE Design Patterns](https://amzn.to/4dpzgmx)
