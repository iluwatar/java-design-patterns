---
layout: pattern
title: Throttling
folder: throttling
permalink: /patterns/throttling/zh
categories: Behavioral
language: zh
tags:
 - Performance
 - Cloud distributed
---

## 目的
确保给定的客户端无法访问超过分配限制的服务资源。

## 解释

真实世界的例子

> 一家大型跨国公司为其客户提供 API。 API 是限速的，每个
> 客户每秒只能拨打一定数量的电话。

简单来说

> 节流模式用于限制对资源的访问。

[Microsoft documentation](https://docs.microsoft.com/en-us/azure/architecture/patterns/throttling) 提到
> 控制应用程序实例、单个租户、
> 或整个服务。这可以让系统继续运行并满足服务水平
> 协议，即使需求增加对资源造成极大负担。

**程序示例**

租户类呈现 API 的客户端。 CallsCount 跟踪每个租户的 API 调用数。

```java
public class Tenant {

  private final String name;
  private final int allowedCallsPerSecond;

  public Tenant(String name, int allowedCallsPerSecond, CallsCount callsCount) {
    if (allowedCallsPerSecond < 0) {
      throw new InvalidParameterException("Number of calls less than 0 not allowed");
    }
    this.name = name;
    this.allowedCallsPerSecond = allowedCallsPerSecond;
    callsCount.addTenant(name);
  }

  public String getName() {
    return name;
  }

  public int getAllowedCallsPerSecond() {
    return allowedCallsPerSecond;
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
    LOGGER.debug("Resetting the map.");
    tenantCallsCount.replaceAll((k, v) -> new AtomicLong(0));
  }
}
```

接下来我们介绍租户正在调用的服务。为了跟踪呼叫计数，我们使用
油门定时器。
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

class B2BService {

  private static final Logger LOGGER = LoggerFactory.getLogger(B2BService.class);
  private final CallsCount callsCount;

  public B2BService(Throttler timer, CallsCount callsCount) {
    this.callsCount = callsCount;
    timer.start();
  }

  public int dummyCustomerApi(Tenant tenant) {
    var tenantName = tenant.getName();
    var count = callsCount.getCount(tenantName);
    LOGGER.debug("Counter for {} : {} ", tenant.getName(), count);
    if (count >= tenant.getAllowedCallsPerSecond()) {
      LOGGER.error("API access per second limit reached for: {}", tenantName);
      return -1;
    }
    callsCount.incrementCount(tenantName);
    return getRandomCustomerId();
  }

  private int getRandomCustomerId() {
    return ThreadLocalRandom.current().nextInt(1, 10000);
  }
}
```

现在我们准备好查看完整的示例。租户阿迪达斯的速率限制为每人 5 个电话
第二和耐克到6。
```java
  public static void main(String[] args) {
    var callsCount = new CallsCount();
    var adidas = new Tenant("Adidas", 5, callsCount);
    var nike = new Tenant("Nike", 6, callsCount);

    var executorService = Executors.newFixedThreadPool(2);
    executorService.execute(() -> makeServiceCalls(adidas, callsCount));
    executorService.execute(() -> makeServiceCalls(nike, callsCount));
    executorService.shutdown();
    
    try {
      executorService.awaitTermination(10, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      LOGGER.error("Executor Service terminated: {}", e.getMessage());
    }
  }

  private static void makeServiceCalls(Tenant tenant, CallsCount callsCount) {
    var timer = new ThrottleTimerImpl(10, callsCount);
    var service = new B2BService(timer, callsCount);
    // Sleep is introduced to keep the output in check and easy to view and analyze the results.
    IntStream.range(0, 20).forEach(i -> {
      service.dummyCustomerApi(tenant);
      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
        LOGGER.error("Thread interrupted: {}", e.getMessage());
      }
    });
  }
```


## 类图

![alt text](./etc/throttling_urm.png "Throttling pattern class diagram")

## 适用性
应使用节流模式：

* 当需要限制服务访问以不影响服务性能时。
* 当多个客户端使用相同的服务资源时，必须根据每个客户端的使用情况进行限制。

## 鸣谢

* [Throttling pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/throttling)
* [Cloud Design Patterns: Prescriptive Architecture Guidance for Cloud Applications (Microsoft patterns & practices)](https://www.amazon.com/gp/product/B00ITGHBBS/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=B00ITGHBBS&linkId=12aacdd0cec04f372e7152689525631a)
