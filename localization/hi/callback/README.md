---
title: Callback
category: Idiom
language: hi
tag:
 - Reactive
---

## हेतु

कॉलबैक निष्पादन योग्य कोड का एक टुकड़ा है जिसे अन्य कोड के तर्क के रूप में पारित किया जाता है
किसी सुविधाजनक समय पर तर्क को वापस बुलाने (निष्पादित) करने की अपेक्षा की जाती है।

## व्याख्या

वास्तविक दुनिया का उदाहरण

>कार्य निष्पादन समाप्त होने के बाद हमें सूचित किया जाना आवश्यक है। हम इसके लिए कॉलबैक विधि पास करते हैं
> निष्पादक और उसके हमें वापस बुलाने की प्रतीक्षा करें।

साफ़ शब्दों में

> कॉलबैक निष्पादक को दी गई एक विधि है जिसे निर्धारित समय पर कॉल किया जाएगा।

विकिपीडिया कहता है

> कंप्यूटर प्रोग्रामिंग में, कॉलबैक, जिसे "कॉल-आफ्टर" फ़ंक्शन के रूप में भी जाना जाता है, कोई भी निष्पादन योग्य है
> वह कोड जो अन्य कोड के तर्क के रूप में पारित किया जाता है; उस अन्य कोड को कॉल करने की उम्मीद है
> किसी निश्चित समय पर तर्क को वापस (निष्पादित) करें।

**प्रोग्रामेटिक उदाहरण**

कॉलबैक एकल विधि वाला एक सरल इंटरफ़ेस है।

```java
public interface Callback {

  void call();
}
```

आगे हम एक कार्य को परिभाषित करते हैं जो कार्य निष्पादन समाप्त होने के बाद कॉलबैक निष्पादित करेगा।

```java
public abstract class Task {

  final void executeWith(Callback callback) {
    execute();
    Optional.ofNullable(callback).ifPresent(Callback::call);
  }

  public abstract void execute();
}

@Slf4j
public final class SimpleTask extends Task {

  @Override
  public void execute() {
    LOGGER.info("Perform some important activity and after call the callback method.");
  }
}
```

अंत में, यहां बताया गया है कि हम किसी कार्य को कैसे निष्पादित करते हैं और उसके समाप्त होने पर कॉलबैक प्राप्त करते हैं।

```java
    var task = new SimpleTask();
    task.executeWith(() -> LOGGER.info("I'm done now."));
```

## क्लास डायग्राम

![alt text](../../../callback/etc/callback.png "Callback")

## प्रयोज्यता

जब कॉलबैक पैटर्न का उपयोग करें

* जब कुछ परिभाषित गतिविधि के निष्पादन के बाद कुछ मनमाना सिंक्रोनस या एसिंक्रोनस क्रिया निष्पादित की जानी चाहिए।

## वास्तविक दुनिया के उदाहरण

* [CyclicBarrier](http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/CyclicBarrier.html#CyclicBarrier%28int,%20java.lang.Runnable%29) कंस्ट्रक्टर एक कॉलबैक स्वीकार कर सकता है जो हर बार बैरियर ट्रिप होने पर ट्रिगर हो जाएगा।
