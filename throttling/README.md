---
title: "Throttling Pattern in Java: Optimizing Resource Usage in High-Demand Applications"
shortTitle: Throttling
description: "Explore the Throttling design pattern in Java to manage application stability and prevent system overload. Learn how rate limiting ensures consistent performance and system resilience. Ideal for developers and software architects."
category: Resource management
language: en
tag:
  - API design
  - Fault tolerance
  - Performance
  - Resilience
  - Scalability
---

## Also known as

* Rate Limiting

## Intent of Throttling Design Pattern

The Throttling Pattern, also known as Rate Limiting, limits the number of requests a system can process within a given time frame to prevent overload and ensure stability. It is crucial for resource management in Java applications.

## Detailed Explanation of Throttling Pattern with Real-World Examples

Real-world example

> Imagine a popular amusement park that limits the number of visitors who can enter per hour to prevent overcrowding. This ensures that all visitors can enjoy the park without long wait times and maintain a pleasant experience. Similarly, the Throttling design pattern in software controls the rate of requests to a system, preventing it from being overwhelmed and ensuring consistent performance for all users.

In plain words

> Throttling pattern is used to rate-limit access to a resource.

[Microsoft documentation](https://docs.microsoft.com/en-us/azure/architecture/patterns/throttling) says

> Control the consumption of resources used by an instance of an application, an individual tenant, or an entire service. This can allow the system to continue to function and meet service level agreements, even when an increase in demand places an extreme load on resources.

## Programmatic Example of Throttling Pattern in Java

In this Java example, we demonstrate throttling. A young human and an old dwarf walk into a bar. They start ordering beers from the bartender. The bartender immediately sees that the young human shouldn't consume too many drinks too fast and refuses to serve if enough time has not passed. For the old dwarf, the serving rate can be higher.

`BarCustomer` class presents the clients of the `Bartender` API. `CallsCount` tracks the number of calls per `BarCustomer`.

```java
@Getter
public class BarCustomer {

    private final String name;
    private final int allowedCallsPerSecond;

    public BarCustomer(String name, int allowedCallsPerSecond, CallsCount callsCount) {
        if (allowedCallsPerSecond < 0) {
            throw new InvalidParameterException("Number of calls less than 0 not allowed");
        }
        this.name = name;
        this.allowedCallsPerSecond = allowedCallsPerSecond;
        callsCount.addTenant(name);
    }
}
```

```java
@Slf4j
public final class CallsCount {
    private final Map<String, AtomicLong> tenantCallsCount = new ConcurrentHashMap<>();

    public void addTenant(String tenantName) {
        tenantCallsCount.putIfAbsent(tenantName, new AtomicLong(0));
    }

    public void incrementCount(String tenantName) {
        tenantCallsCount.get(tenantName).incrementAndGet();
    }

    public long getCount(String tenantName) {
        return tenantCallsCount.get(tenantName).get();
    }

    public void reset() {
        tenantCallsCount.replaceAll((k, v) -> new AtomicLong(0));
        LOGGER.info("reset counters");
    }
}
```

Next, the service that the tenants are calling is introduced. To track the call count, a throttler timer is used.

```java
public interface Throttler {
    void start();
}
```

```java
public class ThrottleTimerImpl implements Throttler {

    private final int throttlePeriod;
    private final CallsCount callsCount;

    public ThrottleTimerImpl(int throttlePeriod, CallsCount callsCount) {
        this.throttlePeriod = throttlePeriod;
        this.callsCount = callsCount;
    }

    @Override
    public void start() {
        new Timer(true).schedule(new TimerTask() {
            @Override
            public void run() {
                callsCount.reset();
            }
        }, 0, throttlePeriod);
    }
}
```

`Bartender` offers the `orderDrink` service to the `BarCustomer`s. The customers probably don't know that the beer serving rate is limited by their appearances.

```java
class Bartender {

    private static final Logger LOGGER = LoggerFactory.getLogger(Bartender.class);
    private final CallsCount callsCount;

    public Bartender(Throttler timer, CallsCount callsCount) {
        this.callsCount = callsCount;
        timer.start();
    }

    public int orderDrink(BarCustomer barCustomer) {
        var tenantName = barCustomer.getName();
        var count = callsCount.getCount(tenantName);
        if (count >= barCustomer.getAllowedCallsPerSecond()) {
            LOGGER.error("I'm sorry {}, you've had enough for today!", tenantName);
            return -1;
        }
        callsCount.incrementCount(tenantName);
        LOGGER.debug("Serving beer to {} : [{} consumed] ", barCustomer.getName(), count + 1);
        return getRandomCustomerId();
    }

    private int getRandomCustomerId() {
        return ThreadLocalRandom.current().nextInt(1, 10000);
    }
}
```

Now it is possible to see the full example in action. `BarCustomer` young human is rate-limited to 2 calls per second and the old dwarf to 4.

```java
@Slf4j
public class App {

    public static void main(String[] args) {
        var callsCount = new CallsCount();
        var human = new BarCustomer("young human", 2, callsCount);
        var dwarf = new BarCustomer("dwarf soldier", 4, callsCount);

        var executorService = Executors.newFixedThreadPool(2);

        executorService.execute(() -> makeServiceCalls(human, callsCount));
        executorService.execute(() -> makeServiceCalls(dwarf, callsCount));

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

    private static void makeServiceCalls(BarCustomer barCustomer, CallsCount callsCount) {
        var timer = new ThrottleTimerImpl(1000, callsCount);
        var service = new Bartender(timer, callsCount);
        // Sleep is introduced to keep the output in check and easy to view and analyze the results.
        IntStream.range(0, 50).forEach(i -> {
            service.orderDrink(barCustomer);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                LOGGER.error("Thread interrupted: {}", e.getMessage());
            }
        });
    }
}
```

An excerpt from the example's console output:

```
18:46:36.218 [Timer-0] INFO com.iluwatar.throttling.CallsCount - reset counters
18:46:36.218 [Timer-1] INFO com.iluwatar.throttling.CallsCount - reset counters
18:46:36.242 [pool-1-thread-2] DEBUG com.iluwatar.throttling.Bartender - Serving beer to dwarf soldier : [1 consumed] 
18:46:36.242 [pool-1-thread-1] DEBUG com.iluwatar.throttling.Bartender - Serving beer to young human : [1 consumed] 
18:46:36.342 [pool-1-thread-2] DEBUG com.iluwatar.throttling.Bartender - Serving beer to dwarf soldier : [2 consumed] 
18:46:36.342 [pool-1-thread-1] DEBUG com.iluwatar.throttling.Bartender - Serving beer to young human : [2 consumed] 
18:46:36.443 [pool-1-thread-1] ERROR com.iluwatar.throttling.Bartender - I'm sorry young human, you've had enough for today!
18:46:36.443 [pool-1-thread-2] DEBUG com.iluwatar.throttling.Bartender - Serving beer to dwarf soldier : [3 consumed] 
18:46:36.544 [pool-1-thread-1] ERROR com.iluwatar.throttling.Bartender - I'm sorry young human, you've had enough for today!
18:46:36.544 [pool-1-thread-2] DEBUG com.iluwatar.throttling.Bartender - Serving beer to dwarf soldier : [4 consumed] 
18:46:36.645 [pool-1-thread-2] ERROR com.iluwatar.throttling.Bartender - I'm sorry dwarf soldier, you've had enough for today!
18:46:36.645 [pool-1-thread-1] ERROR com.iluwatar.throttling.Bartender - I'm sorry young human, you've had enough for today!
18:46:36.745 [pool-1-thread-1] ERROR com.iluwatar.throttling.Bartender - I'm sorry young human, you've had enough for today!
18:46:36.745 [pool-1-thread-2] ERROR com.iluwatar.throttling.Bartender - I'm sorry dwarf soldier, you've had enough for today!
18:46:36.846 [pool-1-thread-1] ERROR com.iluwatar.throttling.Bartender - I'm sorry young human, you've had enough for today!
18:46:36.846 [pool-1-thread-2] ERROR com.iluwatar.throttling.Bartender - I'm sorry dwarf soldier, you've had enough for today!
18:46:36.947 [pool-1-thread-2] ERROR com.iluwatar.throttling.Bartender - I'm sorry dwarf soldier, you've had enough for today!
18:46:36.947 [pool-1-thread-1] ERROR com.iluwatar.throttling.Bartender - I'm sorry young human, you've had enough for today!
18:46:37.048 [pool-1-thread-2] ERROR com.iluwatar.throttling.Bartender - I'm sorry dwarf soldier, you've had enough for today!
18:46:37.048 [pool-1-thread-1] ERROR com.iluwatar.throttling.Bartender - I'm sorry young human, you've had enough for today!
18:46:37.148 [pool-1-thread-1] ERROR com.iluwatar.throttling.Bartender - I'm sorry young human, you've had enough for today!
18:46:37.148 [pool-1-thread-2] ERROR com.iluwatar.throttling.Bartender - I'm sorry dwarf soldier, you've had enough for today!
```

## When to Use the Throttling Pattern in Java

* You need to protect resources from being overwhelmed by too many requests.
* You want to ensure fair usage of a service among multiple users.
* You need to maintain the quality of service under high load conditions.

## Real-World Applications of Throttling Pattern in Java

* APIs of major cloud providers like AWS, Google Cloud, and Azure use throttling to manage resource usage.
* Web services to prevent denial-of-service (DoS) attacks by limiting the number of requests from a single IP address.
* Online platforms like social media sites and e-commerce websites to ensure even distribution of server load.

## Benefits and Trade-offs of Throttling Pattern

Benefits:

* Prevents resource exhaustion, ensuring system stability.
* Helps in maintaining consistent performance and quality of service.
* Improves fault tolerance by avoiding system crashes under high load.

Trade-offs:

* May cause increased latency or delay in request processing.
* Requires careful tuning to balance between resource protection and user experience.
* Could lead to denial of service to legitimate users if not configured correctly.

## Related Java Design Patterns

* [Circuit Breaker](https://java-design-patterns.com/patterns/circuit-breaker/): Works in tandem with throttling to prevent repeated attempts to access an overloaded service.
* Bulkhead: Isolates different parts of the system to limit the impact of throttling on other components.

## References and Credits

* [Throttling pattern (Microsoft)](https://docs.microsoft.com/en-us/azure/architecture/patterns/throttling)
* [Cloud Design Patterns: Prescriptive Architecture Guidance for Cloud Applications](https://amzn.to/4dLvowg)
