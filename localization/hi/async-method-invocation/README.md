---
title: Async Method Invocation
category: Concurrency
language: hi
tag:
 - Reactive
---

## हेतु

एसिंक्रोनस विधि मंगलाचरण एक पैटर्न है जहां कॉलिंग थ्रेड होता है
कार्यों के परिणाम की प्रतीक्षा करते समय अवरुद्ध नहीं किया जाता है। पैटर्न समानांतर प्रदान करता है
अनेक स्वतंत्र कार्यों को संसाधित करना और इनके माध्यम से परिणाम प्राप्त करना
कॉलबैक या सब कुछ पूरा होने तक प्रतीक्षा करना। 

## व्याख्या

वास्तविक दुनिया का उदाहरण

>अंतरिक्ष रॉकेट लॉन्च करना एक रोमांचक व्यवसाय है। मिशन कमांड लॉन्च करने का आदेश देता है और
> कुछ अनिश्चित समय के बाद, रॉकेट या तो सफलतापूर्वक लॉन्च होता है या बुरी तरह विफल हो जाता है।

साफ़ शब्दों में

> एसिंक्रोनस विधि मंगलाचरण कार्य प्रसंस्करण शुरू करता है और कार्य पूरा होने से तुरंत पहले वापस आ जाता है
> तैयार. कार्य प्रसंस्करण के परिणाम बाद में कॉल करने वाले को लौटा दिए जाते हैं।

विकिपीडिया कहता है

> मल्टीथ्रेडेड कंप्यूटर प्रोग्रामिंग में, एसिंक्रोनस मेथड इनवोकेशन (एएमआई) के रूप में भी जाना जाता है
> एसिंक्रोनस विधि कॉल या एसिंक्रोनस पैटर्न एक डिज़ाइन पैटर्न है जिसमें कॉल साइट
> कॉल किए गए कोड के समाप्त होने की प्रतीक्षा करते समय अवरुद्ध नहीं होता है। इसके बजाय, कॉलिंग थ्रेड है
> उत्तर आने पर सूचित किया जाएगा। उत्तर के लिए मतदान एक अवांछित विकल्प है।

**प्रोग्रामेटिक उदाहरण**

इस उदाहरण में, हम अंतरिक्ष रॉकेट लॉन्च कर रहे हैं और चंद्र रोवर्स तैनात कर रहे हैं।

एप्लिकेशन एसिंक विधि मंगलाचरण पैटर्न प्रदर्शित करता है। पैटर्न के प्रमुख भाग हैं
`AsyncResult` जो अतुल्यकालिक रूप से मूल्यांकन किए गए मान के लिए एक मध्यवर्ती कंटेनर है,
`AsyncCallback` जिसे कार्य पूरा होने पर निष्पादित करने के लिए प्रदान किया जा सकता है और `AsyncExecutor`
async कार्यों के निष्पादन का प्रबंधन करता है।

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

फिर हम यह देखने के लिए कुछ रॉकेट लॉन्च करने के लिए तैयार हैं कि सब कुछ एक साथ कैसे काम करता है।

```java
public static void main(String[] args) throws Exception {
  // construct a new executor that will run async tasks
  var executor = new ThreadAsyncExecutor();

  // start few async tasks with varying processing times, two last with callback handlers
  final var asyncResult1 = executor.startProcess(lazyval(10, 500));
  final var asyncResult2 = executor.startProcess(lazyval("test", 300));
  final var asyncResult3 = executor.startProcess(lazyval(50L, 700));
  final var asyncResult4 = executor.startProcess(lazyval(20, 400), callback("Deploying lunar rover"));
  final var asyncResult5 =
      executor.startProcess(lazyval("callback", 600), callback("Deploying lunar rover"));

  // emulate processing in the current thread while async tasks are running in their own threads
  Thread.sleep(350); // Oh boy, we are working hard here
  log("Mission command is sipping coffee");

  // wait for completion of the tasks
  final var result1 = executor.endProcess(asyncResult1);
  final var result2 = executor.endProcess(asyncResult2);
  final var result3 = executor.endProcess(asyncResult3);
  asyncResult4.await();
  asyncResult5.await();

  // log the results of the tasks, callbacks log immediately when complete
  log("Space rocket <" + result1 + "> launch complete");
  log("Space rocket <" + result2 + "> launch complete");
  log("Space rocket <" + result3 + "> launch complete");
}
```

यहां प्रोग्राम कंसोल आउटपुट है।

```java
21:47:08.227 [executor-2] INFO com.iluwatar.async.method.invocation.App - Space rocket <test> launched successfully
21:47:08.269 [main] INFO com.iluwatar.async.method.invocation.App - Mission command is sipping coffee
21:47:08.318 [executor-4] INFO com.iluwatar.async.method.invocation.App - Space rocket <20> launched successfully
21:47:08.335 [executor-4] INFO com.iluwatar.async.method.invocation.App - Deploying lunar rover <20>
21:47:08.414 [executor-1] INFO com.iluwatar.async.method.invocation.App - Space rocket <10> launched successfully
21:47:08.519 [executor-5] INFO com.iluwatar.async.method.invocation.App - Space rocket <callback> launched successfully
21:47:08.519 [executor-5] INFO com.iluwatar.async.method.invocation.App - Deploying lunar rover <callback>
21:47:08.616 [executor-3] INFO com.iluwatar.async.method.invocation.App - Space rocket <50> launched successfully
21:47:08.617 [main] INFO com.iluwatar.async.method.invocation.App - Space rocket <10> launch complete
21:47:08.617 [main] INFO com.iluwatar.async.method.invocation.App - Space rocket <test> launch complete
21:47:08.618 [main] INFO com.iluwatar.async.method.invocation.App - Space rocket <50> launch complete
```

# क्लास डायग्राम

![alt text](../../../async-method-invocation/etc/async-method-invocation.png "Async Method Invocation")

## प्रयोज्यता

जब async विधि मंगलाचरण पैटर्न का उपयोग करें

* आपके पास कई स्वतंत्र कार्य हैं जो समानांतर में चल सकते हैं
* आपको अनुक्रमिक कार्यों के समूह के प्रदर्शन में सुधार करने की आवश्यकता है
* आपके पास सीमित मात्रा में प्रसंस्करण क्षमता या लंबे समय तक चलने वाले कार्य हैं और कॉल करने वाले को कार्यों के तैयार होने का इंतजार नहीं करना चाहिए

## वास्तविक दुनिया के उदाहरण

* [FutureTask](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/FutureTask.html)
* [CompletableFuture](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html)
* [ExecutorService](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html)
* [Task-based Asynchronous Pattern](https://msdn.microsoft.com/en-us/library/hh873175.aspx)
