---
title: Async Method Invocation
shortTitle: Async Method Invocation
category: Concurrency
language: ar
tag:
 - Reactive
---

## الغرض

الاستدعاء غير المتزامن للطرق (invocación de método asincrónico) هو نمط حيث لا يتم حظر الخيط أو العملية التي تستدعي الطريقة أثناء انتظار النتائج. يوفر النمط معالجة متوازية للمهام المستقلة المتعددة ويسترجع النتائج عبر
التعريفات الراجعة (callbacks) أو بالانتظار حتى ينتهي الإجراء.

## الشرح

مثال يومي

> إطلاق الصواريخ الفضائية هو عمل مثير. يعطي قائد المهمة أمر الإطلاق و
> بعد فترة غير محددة من الوقت، يتم إطلاق الصاروخ بنجاح أو يفشل بشكل كارثي.

بعبارة أخرى

> يستدعي الاستدعاء غير المتزامن للطرق الإجراء ويعود فورًا قبل أن تنتهي المهمة
> يتم إرجاع نتائج الإجراء لاحقًا إلى الاستدعاء (callback).

حسب ويكيبيديا

> في البرمجة متعددة العمليات، يعتبر استدعاء الطريقة غير المتزامن (AMI)، المعروف أيضًا بـ
> الاستدعاءات غير المتزامنة أو النمط غير المتزامن هو نمط تصميم حيث لا يتم حظر مكان الاستدعاء
> أثناء انتظار انتهاء الكود المستدعى. بدلاً من ذلك، يتم إخطار خيط الاستدعاء عندما تصل الاستجابة. الاستطلاع للحصول على إجابة هو خيار غير مرغوب فيه.

**مثال برمجي**

في هذا المثال، نحن نطلق صواريخ فضائية وننشر مركبات قمرية.

تُظهر التطبيق ما يفعله نمط الاستدعاء غير المتزامن للطريقة. الأجزاء الرئيسية للنمط هي
`AsyncResult` الذي يعد حاوية وسيطة لقيمة تم تقييمها بشكل غير متزامن،
`AsyncCallback` الذي يمكن توفيره ليتم تنفيذه عند اكتمال المهمة و `AsyncExecutor` الذي
يدير تنفيذ المهام غير المتزامنة.


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

`ThreadAsyncExecutor` هو تنفيذ لـ `AsyncExecutor`. يتم تسليط الضوء على بعض من أجزائه الرئيسية أدناه.


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

الآن كل شيء جاهز لإطلاق بعض الصواريخ لرؤية كيف يعمل كل شيء.


```java
public static void main(String[] args) throws Exception {
  // الآن كل شيء جاهز لإطلاق بعض الصواريخ لرؤية كيف يعمل كل شيء.

  var executor = new ThreadAsyncExecutor();

 // يبدأ بعض المهام غير المتزامنة بأوقات معالجة مختلفة، اثنان من الأخيرين مع محركات استرجاع (callback handlers)

  final var asyncResult1 = executor.startProcess(lazyval(10, 500));
  final var asyncResult2 = executor.startProcess(lazyval("test", 300));
  final var asyncResult3 = executor.startProcess(lazyval(50L, 700));
  final var asyncResult4 = executor.startProcess(lazyval(20, 400), callback("Desplegando el rover lunar"));
  final var asyncResult5 =
      executor.startProcess(lazyval("devolución de llamada callback", 600), callback("Desplegando el rover lunar"));

 // يحاكي المعالجة في الخيط أو العملية الحالية بينما يتم تنفيذ المهام غير المتزامنة في خيوط أو عملياتها الخاصة

  Subproceso.dormir(350); // هيه، نحن نعمل بجد هنا

  log("قائد المهمة يشرب القهوة
");

 // ينتظر حتى اكتمال المهام

  final var result1 = executor.endProcess(asyncResult1);
  final var result2 = executor.endProcess(asyncResult2);
  final var result3 = executor.endProcess(asyncResult3);
  asyncResult4.await();
  asyncResult5.await();

// يسجل نتائج المهام، يتم تسجيل الاسترجاعات فور اكتمالها
log("الصاروخ الفضائي <" + resultado1 + "> أكمل إطلاقه");
log("الصاروخ الفضائي <" + resultado2 + "> أكمل إطلاقه");
log("الصاروخ الفضائي <" + result3 + "> أكمل إطلاقه");

}
```

Here is the output from the program console.

```java
21:47:08.227 [executor-2] INFO com.iluwatar.async.method.invocation.App - الصاروخ الفضائي <prueba> تم إطلاقه بنجاح
21:47:08.269 [main] INFO com.iluwatar.async.method.invocation.App - قائد المهمة يشرب القهوة
21:47:08.318 [executor-4] INFO com.iluwatar.async.method.invocation.App - الصاروخ الفضائي <20> تم إطلاقه بنجاح
21:47:08.335 [executor-4] INFO com.iluwatar.async.method.invocation.App - نشر الروفر القمري <20>
21:47:08.414 [executor-1] INFO com.iluwatar.async.method.invocation.App  - الصاروخ الفضائي <10> تم إطلاقه بنجاح
21:47:08.519 [executor-5] INFO com.iluwatar.async.method.invocation.App - الصاروخ الفضائي <devolución de llamada callback> تم إطلاقه بنجاح
21:47:08.519 [executor-5] INFO com.iluwatar.async.method.invocation.App - تنفيذ المركبة القمرية <devolución de llamada callback>
21:47:08.616 [executor-3] INFO com.iluwatar.async.method.invocation.App - الصاروخ الفضائي <50> تم إطلاقه بنجاح
21:47:08.617 [main] INFO com.iluwatar.async.method.invocation.App - إطلاق الصاروخ الفضائي <10> تم
21:47:08.617 [main] INFO com.iluwatar.async.method.invocation.App - إطلاق الصاروخ الفضائي <prueba> تم
21:47:08.618 [main] INFO com.iluwatar.async.method.invocation.App - إطلاق الصاروخ الفضائي <50> تم

```

# مخطط الفئة

![texto alternativo](./etc/async-method-invocation.png "Invocación de método asíncrono")

## القابلية للتطبيق

استخدم نمط استدعاء الطريقة غير المتزامنة عندما

* يكون لديك مهام مستقلة متعددة يمكن تنفيذها بشكل متوازٍ
* تحتاج إلى تحسين أداء مجموعة من المهام المتسلسلة
* لديك قدرة معالجة محدودة أو مهام تنفيذ طويلة الأمد ولا ينبغي أن تنتظر الدعوة حتى تصبح المهام جاهزة

## أمثلة يومية

* [FutureTask](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/FutureTask.html)
* [CompletableFuture](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html)
* [ExecutorService](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html)
* [Patrón asíncrono basado en tareas](https://msdn.microsoft.com/en-us/library/hh873175.aspx)
