---
title: Throttling
shortTitle: Throttling
category: Behavioral
language: es
tag:
 - Performance
 - Cloud distributed
---

## Propósito

Garantizar que un cliente determinado no pueda acceder a los recursos del servicio más allá del límite asignado.

## Explicación

Ejemplo del mundo real

> Un joven humano y un viejo enano entran en un bar. Empiezan a pedir cervezas al camarero.
> El camarero se da cuenta inmediatamente de que el joven humano no debe consumir demasiadas bebidas demasiado rápido
> y se niega a servir si no ha pasado suficiente tiempo. Para el viejo enano, el ritmo de servicio puede ser mayor.
> ser mayor.

En palabras sencillas

> El patrón de Throttling se utiliza para limitar la velocidad de acceso a un recurso.

[Microsoft documentation](https://docs.microsoft.com/en-us/azure/architecture/patterns/throttling) dice

> Controlar el consumo de recursos utilizados por una instancia de una aplicación, un tenant individual,
> o un servicio completo. Esto puede permitir que el sistema continúe funcionando y cumpla con los acuerdos de nivel de servicio, incluso cuando un aumento de la demanda impone una carga extrema sobre los recursos.

**Ejemplo programático**

La clase `BarCustomer` presenta los clientes de la API `Bartender`. La clase `CallsCount` registra el número de
llamadas por `BarCustomer`.

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

A continuación, se introduce el servicio al que llaman los inquilinos. Para realizar un seguimiento del número de llamadas, se utiliza un temporizador de estrangulamiento.

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

El `Bartender` ofrece el servicio `orderDrink` a los `BarCustomer`s. Los clientes probablemente no
saben que la tasa de servicio de cerveza está limitada por su apariencia.

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

Ahora es posible ver el ejemplo completo en acción. BarCustomer` el joven humano está limitado a 2
llamadas por segundo y el viejo enano a 4.

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

Un extracto de la salida de consola del ejemplo:

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

## Diagrama de clases

![alt text](./etc/throttling_urm.png "Throttling pattern class diagram")

## Aplicabilidad

El patrón Throttling debe utilizarse:

* Cuando se necesita restringir el acceso al servicio para no tener un alto impacto en el rendimiento del mismo.
* Cuando varios clientes consumen los mismos recursos del servicio y la restricción debe hacerse en función del uso por cliente.

## Créditos

* [Throttling pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/throttling)
* [Cloud Design Patterns: Prescriptive Architecture Guidance for Cloud Applications (Microsoft patterns & practices)](https://www.amazon.com/gp/product/B00ITGHBBS/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=B00ITGHBBS&linkId=12aacdd0cec04f372e7152689525631a)
