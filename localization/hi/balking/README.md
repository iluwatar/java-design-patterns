---
title: Balking
category: Concurrency
language: hi
tag:
 - Decoupling
---

## हेतु

बाल्किंग पैटर्न का उपयोग किसी ऑब्जेक्ट को किसी निश्चित कोड को निष्पादित करने से रोकने के लिए किया जाता है यदि वह अधूरा है
या अनुचित स्थिति.

## व्याख्या

वास्तविक दुनिया का उदाहरण

> कपड़े धोने की मशीन में कपड़े धोने की शुरुआत करने के लिए एक स्टार्ट-बटन होता है। धोते समय
> मशीन निष्क्रिय है तो बटन अपेक्षानुसार काम करता है, लेकिन यदि वह पहले से ही धुलाई कर रहा है तो बटन काम करता है
> कुछ नहीं.

साफ़ शब्दों में

> बैल्किंग पैटर्न का उपयोग करते हुए, एक निश्चित कोड केवल तभी निष्पादित होता है जब ऑब्जेक्ट विशेष स्थिति में हो।

विकिपीडिया कहता है

> बैल्किंग पैटर्न एक सॉफ्टवेयर डिज़ाइन पैटर्न है जो किसी ऑब्जेक्ट पर केवल तभी क्रिया निष्पादित करता है
> वस्तु एक विशेष अवस्था में है। उदाहरण के लिए, यदि कोई ऑब्जेक्ट ज़िप फ़ाइलें और कॉलिंग पढ़ता है
> विधि ऑब्जेक्ट पर एक गेट विधि को लागू करती है जब ज़िप फ़ाइल खुली नहीं होती है, तो ऑब्जेक्ट "बाल्क" हो जाएगा
> अनुरोध पर.

**प्रोग्रामेटिक उदाहरण**

इस उदाहरण के कार्यान्वयन में, `WashingMachine` एक ऑब्जेक्ट है जिसमें दो स्थितियाँ हैं जिनमें यह हो सकता है
होना: सक्षम और धुलाई। यदि मशीन सक्षम है, तो थ्रेड-सेफ का उपयोग करके स्थिति वॉशिंग में बदल जाती है
तरीका। दूसरी ओर, यदि यह पहले से ही धुलाई कर रहा है और कोई अन्य थ्रेड `wash()` निष्पादित करता है
यह ऐसा नहीं करेगा और बिना कुछ किए वापस लौट आएगा।

यहां `WashingMachine` वर्ग के प्रासंगिक भाग दिए गए हैं।

```java
@Slf4j
public class WashingMachine {

  private final DelayProvider delayProvider;
  private WashingMachineState washingMachineState;

  public WashingMachine(DelayProvider delayProvider) {
    this.delayProvider = delayProvider;
    this.washingMachineState = WashingMachineState.ENABLED;
  }

  public WashingMachineState getWashingMachineState() {
    return washingMachineState;
  }

  public void wash() {
    synchronized (this) {
      var machineState = getWashingMachineState();
      LOGGER.info("{}: Actual machine state: {}", Thread.currentThread().getName(), machineState);
      if (this.washingMachineState == WashingMachineState.WASHING) {
        LOGGER.error("Cannot wash if the machine has been already washing!");
        return;
      }
      this.washingMachineState = WashingMachineState.WASHING;
    }
    LOGGER.info("{}: Doing the washing", Thread.currentThread().getName());
    this.delayProvider.executeAfterDelay(50, TimeUnit.MILLISECONDS, this::endOfWashing);
  }

  public synchronized void endOfWashing() {
    washingMachineState = WashingMachineState.ENABLED;
    LOGGER.info("{}: Washing completed.", Thread.currentThread().getId());
  }
}
```

यहां `WashingMachine` द्वारा उपयोग किया जाने वाला सरल `DelayProvider` इंटरफ़ेस है।

```java
public interface DelayProvider {
  void executeAfterDelay(long interval, TimeUnit timeUnit, Runnable task);
}
```

अब हम `WashingMachine` का उपयोग करके एप्लिकेशन का परिचय देते हैं।

```java
  public static void main(String... args) {
    final var washingMachine = new WashingMachine();
    var executorService = Executors.newFixedThreadPool(3);
    for (int i = 0; i < 3; i++) {
      executorService.execute(washingMachine::wash);
    }
    executorService.shutdown();
    try {
      executorService.awaitTermination(10, TimeUnit.SECONDS);
    } catch (InterruptedException ie) {
      LOGGER.error("ERROR: Waiting on executor service shutdown!");
      Thread.currentThread().interrupt();
    }
  }
```

यहां प्रोग्राम का कंसोल आउटपुट है।

```
14:02:52.268 [pool-1-thread-2] INFO com.iluwatar.balking.WashingMachine - pool-1-thread-2: Actual machine state: ENABLED
14:02:52.272 [pool-1-thread-2] INFO com.iluwatar.balking.WashingMachine - pool-1-thread-2: Doing the washing
14:02:52.272 [pool-1-thread-3] INFO com.iluwatar.balking.WashingMachine - pool-1-thread-3: Actual machine state: WASHING
14:02:52.273 [pool-1-thread-3] ERROR com.iluwatar.balking.WashingMachine - Cannot wash if the machine has been already washing!
14:02:52.273 [pool-1-thread-1] INFO com.iluwatar.balking.WashingMachine - pool-1-thread-1: Actual machine state: WASHING
14:02:52.273 [pool-1-thread-1] ERROR com.iluwatar.balking.WashingMachine - Cannot wash if the machine has been already washing!
14:02:52.324 [pool-1-thread-2] INFO com.iluwatar.balking.WashingMachine - 14: Washing completed.
```

## क्लास डायग्राम

![alt text](../../../balking/etc/balking.png "Balking")

## प्रयोज्यता

जब बाल्किंग पैटर्न का प्रयोग करें

* आप किसी वस्तु पर तभी कोई कार्रवाई करना चाहते हैं जब वह किसी विशेष स्थिति में हो
* वस्तुएँ आम तौर पर केवल ऐसी स्थिति में होती हैं जो अस्थायी रूप से लेकिन किसी अज्ञात के लिए झुकने की संभावना होती है
  लगने वाला समय

## संबंधित पैटर्न

* [Guarded Suspension Pattern](https://java-design-patterns.com/patterns/guarded-suspension/)
* [Double Checked Locking Pattern](https://java-design-patterns.com/patterns/double-checked-locking/)

## श्रेय

* [Patterns in Java: A Catalog of Reusable Design Patterns Illustrated with UML, 2nd Edition, Volume 1](https://www.amazon.com/gp/product/0471227293/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0471227293&linkId=0e39a59ffaab93fb476036fecb637b99)
