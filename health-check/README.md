---
title: Health Check Pattern
category: Behavioral
language: en
tag:
  - Performance
  - Microservices
  - Resilience
  - Observability
---

# Health Check Pattern

## Also known as
Health Monitoring, Service Health Check

## Intent
To ensure the stability and resilience of services in a microservices architecture by providing a way to monitor and diagnose their health.

## Explanation
In microservices architecture, it's critical to continuously check the health of individual services. The Health Check Pattern is a mechanism for microservices to expose their health status. This pattern is implemented by including a health check endpoint in microservices that returns the service's current state. This is vital for maintaining system resilience and operational readiness.

For more information, see the Health Check API pattern on [Microservices.io](https://microservices.io/patterns/observability/health-check-api.html).


## Real World Example
In a cloud-native environment, such as Kubernetes or AWS ECS, health checks are used to ensure that containers are running correctly. If a service fails its health check, it can be automatically restarted or replaced, ensuring high availability and resilience.

## In Plain Words
The Health Check Pattern is like a regular doctor's visit for services in a microservices architecture. It helps in early detection of issues and ensures that services are healthy and available.


## Programmatic Example
Here, provided detailed examples of health check implementations in a microservices environment.

### AsynchronousHealthChecker
An asynchronous health checker component that executes health checks in a separate thread.
```java
  /**
   * Performs a health check asynchronously using the provided health check logic with a specified
   * timeout.
   *
   * @param healthCheck the health check logic supplied as a {@code Supplier<Health>}
   * @param timeoutInSeconds the maximum time to wait for the health check to complete, in seconds
   * @return a {@code CompletableFuture<Health>} object that represents the result of the health
   *     check
   */
  public CompletableFuture<Health> performCheck(
      Supplier<Health> healthCheck, long timeoutInSeconds) {
    CompletableFuture<Health> future =
        CompletableFuture.supplyAsync(healthCheck, healthCheckExecutor);

    // Schedule a task to enforce the timeout
    healthCheckExecutor.schedule(
        () -> {
          if (!future.isDone()) {
            LOGGER.error(HEALTH_CHECK_TIMEOUT_MESSAGE);
            future.completeExceptionally(new TimeoutException(HEALTH_CHECK_TIMEOUT_MESSAGE));
          }
        },
        timeoutInSeconds,
        TimeUnit.SECONDS);

    return future.handle(
        (result, throwable) -> {
          if (throwable != null) {
            LOGGER.error(HEALTH_CHECK_FAILED_MESSAGE, throwable);
            // Check if the throwable is a TimeoutException or caused by a TimeoutException
            Throwable rootCause =
                throwable instanceof CompletionException ? throwable.getCause() : throwable;
            if (!(rootCause instanceof TimeoutException)) {
              LOGGER.error(HEALTH_CHECK_FAILED_MESSAGE, rootCause);
              return Health.down().withException(rootCause).build();
            } else {
              LOGGER.error(HEALTH_CHECK_TIMEOUT_MESSAGE, rootCause);
              // If it is a TimeoutException, rethrow it wrapped in a CompletionException
              throw new CompletionException(rootCause);
            }
          } else {
            return result;
          }
        });
  }
```

### CpuHealthIndicator
A health indicator that checks the health of the system's CPU.
```java
  /**
   * Checks the health of the system's CPU and returns a health indicator object.
   *
   * @return a health indicator object
   */
  @Override
  public Health health() {
    if (!(osBean instanceof com.sun.management.OperatingSystemMXBean sunOsBean)) {
      LOGGER.error("Unsupported operating system MXBean: {}", osBean.getClass().getName());
      return Health.unknown()
          .withDetail(ERROR_MESSAGE, "Unsupported operating system MXBean")
          .build();
    }

    double systemCpuLoad = sunOsBean.getCpuLoad() * 100;
    double processCpuLoad = sunOsBean.getProcessCpuLoad() * 100;
    int availableProcessors = sunOsBean.getAvailableProcessors();
    double loadAverage = sunOsBean.getSystemLoadAverage();

    Map<String, Object> details = new HashMap<>();
    details.put("timestamp", Instant.now());
    details.put("systemCpuLoad", String.format("%.2f%%", systemCpuLoad));
    details.put("processCpuLoad", String.format("%.2f%%", processCpuLoad));
    details.put("availableProcessors", availableProcessors);
    details.put("loadAverage", loadAverage);

    if (systemCpuLoad > systemCpuLoadThreshold) {
      LOGGER.error(HIGH_SYSTEM_CPU_LOAD_MESSAGE, systemCpuLoad);
      return Health.down()
          .withDetails(details)
          .withDetail(ERROR_MESSAGE, HIGH_SYSTEM_CPU_LOAD_MESSAGE_WITHOUT_PARAM)
          .build();
    } else if (processCpuLoad > processCpuLoadThreshold) {
      LOGGER.error(HIGH_PROCESS_CPU_LOAD_MESSAGE, processCpuLoad);
      return Health.down()
          .withDetails(details)
          .withDetail(ERROR_MESSAGE, HIGH_PROCESS_CPU_LOAD_MESSAGE_WITHOUT_PARAM)
          .build();
    } else if (loadAverage > (availableProcessors * loadAverageThreshold)) {
      LOGGER.error(HIGH_LOAD_AVERAGE_MESSAGE, loadAverage);
      return Health.up()
          .withDetails(details)
          .withDetail(ERROR_MESSAGE, HIGH_LOAD_AVERAGE_MESSAGE_WITHOUT_PARAM)
          .build();
    } else {
      return Health.up().withDetails(details).build();
    }
  }

```



### CustomHealthIndicator
A custom health indicator that periodically checks the health of a database and caches the result. It leverages an asynchronous health checker to perform the health checks.

- `AsynchronousHealthChecker`: A component for performing health checks asynchronously.
- `CacheManager`: Manages caching of health check results.
- `HealthCheckRepository`: A repository for querying health-related data from the database.

```java
/**
 * Perform a health check and cache the result.
 *
 * @return the health status of the application
 * @throws HealthCheckInterruptedException if the health check is interrupted
 */
@Override
@Cacheable(value = "health-check", unless = "#result.status == 'DOWN'")
public Health health() {
    LOGGER.info("Performing health check");
    CompletableFuture<Health> healthFuture =
        healthChecker.performCheck(this::check, timeoutInSeconds);
    try {
        return healthFuture.get(timeoutInSeconds, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        LOGGER.error("Health check interrupted", e);
        throw new HealthCheckInterruptedException(e);
    } catch (Exception e) {
        LOGGER.error("Health check failed", e);
        return Health.down(e).build();
    }
}

/**
 * Checks the health of the database by querying for a simple constant value expected from the
 * database.
 *
 * @return Health indicating UP if the database returns the constant correctly, otherwise DOWN.
 */
private Health check() {
    Integer result = healthCheckRepository.checkHealth();
    boolean databaseIsUp = result != null && result == 1;
    LOGGER.info("Health check result: {}", databaseIsUp);
    return databaseIsUp
        ? Health.up().withDetail("database", "reachable").build()
        : Health.down().withDetail("database", "unreachable").build();
}

/**
 * Evicts all entries from the health check cache. This is scheduled to run at a fixed rate
 * defined in the application properties.
 */
@Scheduled(fixedRateString = "${health.check.cache.evict.interval:60000}")
public void evictHealthCache() {
    LOGGER.info("Evicting health check cache");
    try {
        Cache healthCheckCache = cacheManager.getCache("health-check");
        LOGGER.info("Health check cache: {}", healthCheckCache);
        if (healthCheckCache != null) {
            healthCheckCache.clear();
        }
    } catch (Exception e) {
        LOGGER.error("Failed to evict health check cache", e);
    }
}

```

### DatabaseTransactionHealthIndicator
A health indicator that checks the health of database transactions by attempting to perform a test transaction using a retry mechanism.

- **HealthCheckRepository**: A repository for performing health checks on the database.
- **AsynchronousHealthChecker**: An asynchronous health checker used to execute health checks in a separate thread.
- **RetryTemplate**: A retry template used to retry the test transaction if it fails due to a transient error.

```java
/**
 * Performs a health check by attempting to perform a test transaction with retry support.
 *
 * @return the health status of the database transactions
 */
@Override
public Health health() {
    LOGGER.info("Calling performCheck with timeout {}", timeoutInSeconds);
    Supplier<Health> dbTransactionCheck =
        () -> {
            try {
                healthCheckRepository.performTestTransaction();
                return Health.up().build();
            } catch (Exception e) {
                LOGGER.error("Database transaction health check failed", e);
                return Health.down(e).build();
            }
        };
    try {
        return asynchronousHealthChecker.performCheck(dbTransactionCheck, timeoutInSeconds).get();
    } catch (InterruptedException | ExecutionException e) {
        LOGGER.error("Database transaction health check timed out or was interrupted", e);
        Thread.currentThread().interrupt();
        return Health.down(e).build();
    }
}
```


### GarbageCollectionHealthIndicator
A custom health indicator that checks the garbage collection status of the application and reports the health status accordingly.
```java
  /**
   * Performs a health check by gathering garbage collection metrics and evaluating the overall
   * health of the garbage collection system.
   *
   * @return a {@link Health} object representing the health status of the garbage collection system
   */
  @Override
  public Health health() {
    List<GarbageCollectorMXBean> gcBeans = getGarbageCollectorMxBeans();
    List<MemoryPoolMXBean> memoryPoolMxBeans = getMemoryPoolMxBeans();
    Map<String, Map<String, String>> gcDetails = new HashMap<>();

    for (GarbageCollectorMXBean gcBean : gcBeans) {
      Map<String, String> collectorDetails = createCollectorDetails(gcBean, memoryPoolMxBeans);
      gcDetails.put(gcBean.getName(), collectorDetails);
    }
    return Health.up().withDetails(gcDetails).build();
  }

```

### MemoryHealthIndicator
A custom health indicator that checks the memory usage of the application and reports the health status accordingly.
```java
  /**
   * Performs a health check by checking the memory usage of the application.
   *
   * @return the health status of the application
   */
  public Health checkMemory() {
    Supplier<Health> memoryCheck =
        () -> {
          MemoryMXBean memoryMxBean = ManagementFactory.getMemoryMXBean();
          MemoryUsage heapMemoryUsage = memoryMxBean.getHeapMemoryUsage();
          long maxMemory = heapMemoryUsage.getMax();
          long usedMemory = heapMemoryUsage.getUsed();

          double memoryUsage = (double) usedMemory / maxMemory;
          String format = String.format("%.2f%% of %d max", memoryUsage * 100, maxMemory);

          if (memoryUsage < memoryThreshold) {
            LOGGER.info("Memory usage is below threshold: {}", format);
            return Health.up().withDetail("memory usage", format).build();
          } else {
            return Health.down().withDetail("memory usage", format).build();
          }
        };

    try {
      CompletableFuture<Health> future =
          asynchronousHealthChecker.performCheck(memoryCheck, timeoutInSeconds);
      return future.get();
    } catch (InterruptedException e) {
      LOGGER.error("Health check interrupted", e);
      Thread.currentThread().interrupt();
      return Health.down().withDetail("error", "Health check interrupted").build();
    } catch (ExecutionException e) {
      LOGGER.error("Health check failed", e);
      Throwable cause = e.getCause() == null ? e : e.getCause();
      return Health.down().withDetail("error", cause.toString()).build();
    }
  }

  /**
   * Retrieves the health status of the application by checking the memory usage.
   *
   * @return the health status of the application
   */
  @Override
  public Health health() {
    return checkMemory();
  }
}
```



## Using Spring Boot Actuator for Health Checks
Spring Boot Actuator provides built-in health checking functionality that can be easily integrated into your application. By adding the Spring Boot Actuator dependency, you can expose health check information through a predefined endpoint, typically `/actuator/health`.

## Output
This shows the output of the health check pattern using a GET request to the Actuator health endpoint.

### HTTP GET Request
```
curl -X GET "http://localhost:6161/actuator/health"
```

### Output
```json
{
    "status": "UP",
    "components": {
        "cpu": {
            "status": "UP",
            "details": {
                "processCpuLoad": "0.03%",
                "availableProcessors": 10,
                "systemCpuLoad": "21.40%",
                "loadAverage": 3.3916015625,
                "timestamp": "2023-12-03T08:44:19.488422Z"
            }
        },
        "custom": {
            "status": "UP",
            "details": {
                "database": "reachable"
            }
        },
        "databaseTransaction": {
            "status": "UP"
        },
        "db": {
            "status": "UP",
            "details": {
                "database": "H2",
                "validationQuery": "isValid()"
            }
        },
        "diskSpace": {
            "status": "UP",
            "details": {
                "total": 994662584320,
                "free": 377635827712,
                "threshold": 10485760,
                "exists": true
            }
        },
        "garbageCollection": {
            "status": "UP",
            "details": {
                "G1 Young Generation": {
                    "count": "11",
                    "time": "30ms",
                    "memoryPools": "G1 Old Gen: 0.005056262016296387%"
                },
                "G1 Old Generation": {
                    "count": "0",
                    "time": "0ms",
                    "memoryPools": "G1 Old Gen: 0.005056262016296387%"
                }
            }
        },
        "livenessState": {
            "status": "UP"
        },
        "memory": {
            "status": "UP",
            "details": {
                "memory usage": "1.36% of 4294967296 max"
            }
        },
        "ping": {
            "status": "UP"
        },
        "readinessState": {
            "status": "UP"
        }
    },
    "groups": [
        "liveness",
        "readiness"
    ]
}
```

## Class Diagram
![Health Check Pattern](./etc/health-check.png)

## Applicability
Use the Health Check Pattern when:
- You have an application composed of multiple services and need to monitor the health of each service individually.
- You want to implement automatic service recovery or replacement based on health status.
- You are employing orchestration or automation tools that rely on health checks to manage service instances.

## Tutorials
- Implementing Health Checks in Java using Spring Boot Actuator.

## Known Uses
- Kubernetes Liveness and Readiness Probes
- AWS Elastic Load Balancing Health Checks
- Spring Boot Actuator

## Consequences
**Pros:**
- Enhances the fault tolerance of the system by detecting failures and enabling quick recovery.
- Improves the visibility of system health for operational monitoring and alerting.

**Cons:**
- Adds complexity to service implementation.
- Requires a strategy to handle cascading failures when dependent services are unhealthy.

## Related Patterns
- Circuit Breaker
- Retry Pattern
- Timeout Pattern

## Credits
Inspired by the Health Check API pattern from [microservices.io](https://microservices.io/patterns/observability/health-check-api.html), and the issue [#2695](https://github.com/iluwatar/java-design-patterns/issues/2695) on iluwatar's Java design patterns repository.
