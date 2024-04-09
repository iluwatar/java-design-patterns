---
title: Health Check Pattern
category: Behavioral
language: es
tag:
  - Performance
  - Microservices
  - Resilience
  - Observability
---

# Health Check Pattern

## También conocido como
Health Monitoring, Service Health Check

## Propósito
Garantizar la estabilidad y resistencia de los servicios en una arquitectura de microservicios proporcionando una forma de supervisar y diagnosticar su estado.

## Explicación
En la arquitectura de microservicios, es crítico comprobar continuamente la salud de los servicios individuales. El Health Check Pattern es un mecanismo para que los microservicios expongan su estado de salud. Este patrón se implementa incluyendo un punto final de comprobación de salud en los microservicios que devuelve el estado actual del servicio. Esto es vital para mantener la resistencia del sistema y la disponibilidad operativa.

Para obtener más información, consulte el patrón API Health Check en [Microservices.io](https://microservices.io/patterns/observability/health-check-api.html).


## Ejemplo del mundo real
En un entorno nativo en la nube, como Kubernetes o AWS ECS, las comprobaciones de estado se utilizan para garantizar que los contenedores se ejecutan correctamente. Si un servicio falla su chequeo de salud, puede ser reiniciado o reemplazado automáticamente, asegurando alta disponibilidad y resiliencia.

## En pocas palabras
El patrón de comprobación de la salud es como una visita periódica al médico para los servicios en una arquitectura de microservicios. Ayuda en la detección temprana de problemas y asegura que los servicios estén sanos y disponibles.


## Ejemplo Programático
Aquí, se proporcionan ejemplos detallados de implementaciones de chequeo de salud en un entorno de microservicios.

### AsynchronousHealthChecker
Un componente de comprobación de salud asíncrono que ejecuta comprobaciones de salud en un hilo separado.

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
Un indicador de salud que comprueba la salud de la CPU del sistema.

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
Un indicador de estado personalizado que comprueba periódicamente el estado de una base de datos y almacena en caché el resultado. Aprovecha un comprobador de estado asíncrono para realizar las comprobaciones de estado.

- `AsynchronousHealthChecker`: Un componente para realizar comprobaciones de estado de forma asíncrona.
- `CacheManager`: Gestiona el almacenamiento en caché de los resultados de los controles de salud.
- `HealthCheckRepository`: Un repositorio para consultar datos relacionados con la salud desde la base de datos.

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
Un indicador de salud que comprueba la salud de las transacciones de la base de datos intentando realizar una transacción de prueba utilizando un mecanismo de reintento.

- Repositorio de comprobaciones de estado**: Un repositorio para realizar comprobaciones de salud en la base de datos.
- AsynchronousHealthChecker**: Un comprobador de salud asíncrono utilizado para ejecutar comprobaciones de salud en un hilo independiente.
- Plantilla de reintento**: Una plantilla de reintento utilizada para reintentar la transacción de prueba si falla debido a un error transitorio.

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
Un indicador de salud personalizado que comprueba el estado de recogida de basura de la aplicación e informa del estado de salud en consecuencia.

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
Un indicador de salud personalizado que comprueba el uso de memoria de la aplicación e informa del estado de salud en consecuencia.

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



## Usando Spring Boot Actuator para Comprobaciones de Salud
Spring Boot Actuator proporciona una funcionalidad de comprobación de salud incorporada que puede integrarse fácilmente en su aplicación. Añadiendo la dependencia Spring Boot Actuator, puede exponer la información de comprobación de estado a través de un punto final predefinido, normalmente `/actuator/health`.

## Salida
Esto muestra la salida del patrón de comprobación de salud utilizando una petición GET al punto final de salud de Actuator.

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

## Diagrama de clases
![Health Check Pattern](./etc/health-check.png)

## Aplicabilidad
Utilice el Patrón de Comprobación de Salud cuando:
- Tienes una aplicación compuesta por múltiples servicios y necesitas monitorizar la salud de cada servicio individualmente.
- Desea implementar la recuperación o sustitución automática de servicios basándose en el estado de salud.
- Está empleando herramientas de orquestación o automatización que se basan en comprobaciones de estado para gestionar instancias de servicio.

## Tutoriales
- Implementación de Health Checks en Java usando Spring Boot Actuator.

## Usos conocidos
- Kubernetes Liveness y Readiness Probes
- Comprobaciones de estado de AWS Elastic Load Balancing
- Actuador Spring Boot

## Consecuencias
**Pros:**
- Mejora la tolerancia a fallos del sistema detectando fallos y permitiendo una rápida recuperación.
- Mejora la visibilidad del estado del sistema para la supervisión operativa y las alertas.

**Contras:**
- Añade complejidad a la implementación del servicio.
- Requiere una estrategia para gestionar los fallos en cascada cuando los servicios dependientes no están en buen estado.

## Patrones relacionados
- Circuit Breaker
- Retry Pattern
- Timeout Pattern

## Créditos
Inspirado en el patrón Health Check API de [microservices.io](https://microservices.io/patterns/observability/health-check-api.html), y el tema [#2695](https://github.com/iluwatar/java-design-patterns/issues/2695) en el repositorio de patrones de diseño Java de iluwatar.