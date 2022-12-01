---
title: Throttling
category: Behavioral
language: en
tag:
 - Performance
 - Cloud distributed
---

## Intent

Ensure that a given client is not able to access service resources more than the assigned limit.

## Explanation

Real-world example

> A young human and an old dwarf walk into a bar. They start ordering beers from the bartender.
> The bartender immediately sees that the young human shouldn't consume too many drinks too fast
> and refuses to serve if enough time has not passed. For the old dwarf, the serving rate can
> be higher.

In plain words

> Throttling pattern is used to rate-limit access to a resource. 

[Microsoft documentation](https://docs.microsoft.com/en-us/azure/architecture/patterns/throttling) says

> Control the consumption of resources used by an instance of an application, an individual tenant, 
> or an entire service. This can allow the system to continue to function and meet service level 
> agreements, even when an increase in demand places an extreme load on resources.

**Programmatic Example**

`BarCustomer` class presents the clients of the `Bartender` API. `CallsCount` tracks the number of 
calls per `BarCustomer`.

```java
public class BarCustomer {

    @Getter
    private final String name;
    @Getter
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

Next, the service that the tenants are calling is introduced. To track the call count, a throttler 
timer is used.

```java
public interface Throttler {

  void start();
}

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

`Bartender` offers the `orderDrink` service to the `BarCustomer`s. The customers probably don't
know that the beer serving rate is limited by their appearances.

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
        LOGGER.debug("Serving beer to {} : [{} consumed] ", barCustomer.getName(), count+1);
        return getRandomCustomerId();
    }

    private int getRandomCustomerId() {
        return ThreadLocalRandom.current().nextInt(1, 10000);
    }
}
```

Now it is possible to see the full example in action. `BarCustomer` young human is rate-limited to 2 
calls per second and the old dwarf to 4.

```java
public static void main(String[] args) {
    var callsCount = new CallsCount();
    var human = new BarCustomer("young human", 2, callsCount);
    var dwarf = new BarCustomer("dwarf soldier", 4, callsCount);

    var executorService = Executors.newFixedThreadPool(2);

    executorService.execute(() -> makeServiceCalls(human, callsCount));
    executorService.execute(() -> makeServiceCalls(dwarf, callsCount));

    executorService.shutdown();
    try {
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
        LOGGER.error("Executor service terminated: {}", e.getMessage());
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

## Class diagram

![alt text](./etc/throttling_urm.png "Throttling pattern class diagram")

## Applicability

The Throttling pattern should be used:

* When service access needs to be restricted not to have high impact on the performance of the service.
* When multiple clients are consuming the same service resources and restriction has to be made according to the usage per client.

## Credits

* [Throttling pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/throttling)
* [Cloud Design Patterns: Prescriptive Architecture Guidance for Cloud Applications (Microsoft patterns & practices)](https://www.amazon.com/gp/product/B00ITGHBBS/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=B00ITGHBBS&linkId=12aacdd0cec04f372e7152689525631a)
