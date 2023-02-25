---
title: Async Method Invocation
category: Concurrency
language: es
tag:
 - Reactive
---

## Intent

Asynchronous method invocation is a pattern where the calling thread
is not blocked while waiting results of tasks. The pattern provides parallel
processing of multiple independent tasks and retrieving the results via
callbacks or waiting until everything is done. 

## Intención

Asynchronous method invocation (invocación de método asincrónico) es un patrón en el que el hilo o subproceso de llamada
no se bloquea mientras espera resultados. El patrón proporciona pocesamiento en paralelo de múltiples tareas independientes y recuperación de los resultados a través de
devoluciones de llamada o esperando hasta que termine el procedimiento.

## Explanation
## Explicación

Real world example
Ejemplo cotidiano

> Launching space rockets is an exciting business. The mission command gives an order to launch and 
> after some undetermined time, the rocket either launches successfully or fails miserably.
> Lanzar cohetes espaciales es un negocio apasionante. El comandante de la misión da la orden de lanzamiento y
> después de un tiempo indeterminado, el cohete se lanza con éxito o falla miserablemente.




In plain words
En otras palabras

> Asynchronous method invocation starts task processing and returns immediately before the task is 
> ready. The results of the task processing are returned to the caller later.
> La invocación del método asíncrono inicia el procedimiento y regresa inmediatamente antes de que la tarea termine
> Los resultados del procedimiento se devuelven a la llamada posteriormente.

Wikipedia says
Según Wikipedia

> In multithreaded computer programming, asynchronous method invocation (AMI), also known as 
> asynchronous method calls or the asynchronous pattern is a design pattern in which the call site 
> is not blocked while waiting for the called code to finish. Instead, the calling thread is 
> notified when the reply arrives. Polling for a reply is an undesired option.


> En la programación multiproceso, la invocación de método asíncrono (AMI), también conocida como
> llamadas de método asíncrono o el patrón asíncrono es un patrón de diseño en el que el lugar de la llamada
> no se bloquea mientras espera que termine el código llamado. En cambio, el hilo de llamada es
> notificado cuando llega la respuesta. Sondear para obtener una respuesta es una opción no deseada.

**Programmatic Example**
**Ejemplo programático**

In this example, we are launching space rockets and deploying lunar rovers.
En este ejemplo, estamos lanzando cohetes espaciales y desplegando vehículos lunares.

The application demonstrates the async method invocation pattern. The key parts of the pattern are 
`AsyncResult` which is an intermediate container for an asynchronously evaluated value, 
`AsyncCallback` which can be provided to be executed on task completion and `AsyncExecutor` that 
manages the execution of the async tasks.
La aplicación demuestra lo que hace el patrón de invocación del método asíncrono. Las partes clave del patrón son
`AsyncResult` que es un contenedor intermedio para un valor evaluado de forma asíncrona,
`AsyncCallback` que se puede proporcionar para que se ejecute al finalizar la tarea y `AsyncExecutor` que
gestiona la ejecución de las tareas asíncronas.

```java
public interface AsyncResult<T> {
  boolean isCompleted();
  T getValue() throws ExecutionException;
  void await() throws InterruptedException;
}
```

```java
public interface AsyncCallback<T> {
  void onComplete(T value, Optional<Exception> ex);
}
```

```java
public interface AsyncExecutor {
  <T> AsyncResult<T> startProcess(Callable<T> task);
  <T> AsyncResult<T> startProcess(Callable<T> task, AsyncCallback<T> callback);
  <T> T endProcess(AsyncResult<T> asyncResult) throws ExecutionException, InterruptedException;
}
```

`ThreadAsyncExecutor` is an implementation of `AsyncExecutor`. Some of its key parts are highlighted 
next.
`ThreadAsyncExecutor` es una implementación de `AsyncExecutor`. Se destacan algunas de sus partes clave a continuación.

```java
public class ThreadAsyncExecutor implements AsyncExecutor {

  @Override
  public <T> AsyncResult<T> startProcess(Callable<T> task) {
    return startProcess(task, null);
  }

  @Override
  public <T> AsyncResult<T> startProcess(Callable<T> task, AsyncCallback<T> callback) {
    var result = new CompletableResult<>(callback);
    new Thread(
            () -> {
              try {
                result.setValue(task.call());
              } catch (Exception ex) {
                result.setException(ex);
              }
            },
            "executor-" + idx.incrementAndGet())
        .start();
    return result;
  }

  @Override
  public <T> T endProcess(AsyncResult<T> asyncResult)
      throws ExecutionException, InterruptedException {
    if (!asyncResult.isCompleted()) {
      asyncResult.await();
    }
    return asyncResult.getValue();
  }
}
```

Then we are ready to launch some rockets to see how everything works together.
Ahora está todo preparado para lanzar algunos cohetes y así poder ver cómo funciona todo.

```java
public static void main(String[] args) throws Exception {
  // construct a new executor that will run async tasks
  // construye un nuevo objeto executor que ejecutará tareas asíncronas
  var executor = new ThreadAsyncExecutor();

  // start few async tasks with varying processing times, two last with callback handlers
  // inicia algunas tareas asíncronas con diferentes tiempos de procesamiento, las dos últimas con controladores de devolución de llamada
  final var asyncResult1 = executor.startProcess(lazyval(10, 500));
  final var asyncResult2 = executor.startProcess(lazyval("test", 300));
  final var asyncResult3 = executor.startProcess(lazyval(50L, 700));
  final var asyncResult4 = executor.startProcess(lazyval(20, 400), callback("Deploying lunar rover"));
  final var asyncResult4 = executor.startProcess(lazyval(20, 400), callback("Desplegando el rover lunar"));
  final var asyncResult5 =
      executor.startProcess(lazyval("devolución de llamada callback", 600), callback("Deploying lunar rover"));
  final var asyncResult5 =
      executor.startProcess(lazyval("devolución de llamada callback", 600), callback("Desplegando el rover lunar"));

  // emulate processing in the current thread while async tasks are running in their own threads
  // emula el procesamiento en el hilo o subproceso actual mientras las tareas asíncronas se ejecutan en sus propios hilos o subprocesos
  Thread.sleep(350); // Oh boy, we are working hard here
  Subproceso.dormir(350); // Oye, estamos trabajando duro aquí
  log("Mission command is sipping coffee");
  log("El comandante de la misión está bebiendo café");

  // wait for completion of the tasks
  // espera a que se completen las tareas
  final var result1 = executor.endProcess(asyncResult1);
  final var result2 = executor.endProcess(asyncResult2);
  final var result3 = executor.endProcess(asyncResult3);
  asyncResult4.await();
  asyncResult5.await();

  // log the results of the tasks, callbacks log immediately when complete
  // registra los resultados de las tareas, las devoluciones de las llamadas se registran inmediatamente cuando se completan
  log("Space rocket <" + result1 + "> launch complete");
  log("Cohete espacial <" + resultado1 + "> ha completado su lanzamiento");
  log("Space rocket <" + result2 + "> launch complete");
  log("Cohete espacial <" + resultado2 + "> ha completado su lanzamiento");
  log("Space rocket <" + result3 + "> launch complete");
  log("Cohete espacial <" + result3 + "> ha completado su lanzamiento");
}
```

Here's the program console output.
Aquí está la salida de la consola del programa.

```java
21:47:08.227 [executor-2] INFO com.iluwatar.async.method.invocation.App - Space rocket <test> launched successfully
21:47:08.227 [executor-2] INFO com.iluwatar.async.method.invocation.App - Cohete espacial <prueba> lanzado con éxito
21:47:08.269 [main] INFO com.iluwatar.async.method.invocation.App - Mission command is sipping coffee
21:47:08.269 [main] INFO com.iluwatar.async.method.invocation.App - El comandante de la misión está bebiendo café
21:47:08.318 [executor-4] INFO com.iluwatar.async.method.invocation.App - Space rocket <20> launched successfully
21:47:08.318 [executor-4] INFO com.iluwatar.async.method.invocation.App - Cohete espacial <20> lanzado con éxito
21:47:08.335 [executor-4] INFO com.iluwatar.async.method.invocation.App - Deploying lunar rover <20>
method.invocation.App: Desplegando el rover lunar <20>
21:47:08.414 [executor-1] INFO com.iluwatar.async.method.invocation.App - Space rocket <10> launched successfully
invocation.App - Cohete espacial <10> lanzado con éxito
21:47:08.519 [executor-5] INFO com.iluwatar.async.method.invocation.App - Space rocket <callback> launched successfully
21:47:08.519 [executor-5] INFO com.iluwatar.async.method.invocation.App - Cohete espacial <devolución de llamada callback> lanzado con éxito
21:47:08.519 [executor-5] INFO com.iluwatar.async.method.invocation.App - Deploying lunar rover <callback>
21:47:08.519 [executor-5] INFO com.iluwatar.async.method.invocation.App - Implementando el vehículo lunar <devolución de llamada callback>
21:47:08.616 [executor-3] INFO com.iluwatar.async.method.invocation.App - Space rocket <50> launched successfully
method.invocation.App - Cohete espacial <50> lanzado con éxito
21:47:08.617 [main] INFO com.iluwatar.async.method.invocation.App - Space rocket <10> launch complete
method.invocation.App - Lanzamiento del cohete espacial <10> completado
21:47:08.617 [main] INFO com.iluwatar.async.method.invocation.App - Space rocket <test> launch complete
method.invocation.App - Lanzamiento de cohete espacial <prueba> completado
21:47:08.618 [main] INFO com.iluwatar.async.method.invocation.App - Space rocket <50> launch complete
method.invocation.App - Lanzamiento del cohete espacial <50> completado
```



# Class diagram

![alt text](./etc/async-method-invocation.png "Async Method Invocation")
# Diagrama de clase

![texto alternativo](./etc/async-method-invocation.png "Invocación de método asíncrono")

## Applicability
## Aplicabilidad

Use the async method invocation pattern when
Utiliza el patrón de invocación del método asíncrono cuando

* You have multiple independent tasks that can run in parallel
* Tienes múltiples tareas independientes que pueden ejecutarse en paralelo
* You need to improve the performance of a group of sequential tasks
* Necesitas mejorar el desempeño de un grupo de tareas secuenciales
* You have a limited amount of processing capacity or long-running tasks and the caller should not wait for the tasks to be ready
* Tienes una cantidad limitada de capacidad de procesamiento o tareas de ejecución prolongada y la llamada no debe esperar a que las tareas estén listas

## Real world examples
## Ejemplos cotidianos

* [FutureTask](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/FutureTask.html)
* [CompletableFuture](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html)
* [ExecutorService](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html)
* [Patrón asíncrono basado en tareas](https://msdn.microsoft.com/en-us/library/hh873175.aspx)