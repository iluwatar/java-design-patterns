---
title: Caching
category: Behavioral
language: hi
tag:
  - Performance
  - Cloud distributed
---

## हेतु

कैशिंग पैटर्न संसाधनों को तुरंत जारी न करके उनके महंगे पुन: अधिग्रहण से बचाता है
उपयोग के बाद। संसाधन अपनी पहचान बनाए रखते हैं, कुछ तेज़-पहुँच वाले भंडारण में रखे जाते हैं, और हैं
उन्हें दोबारा प्राप्त करने से बचने के लिए पुन: उपयोग किया जाता है।

## व्याख्या

वास्तविक दुनिया का उदाहरण

> एक टीम एक ऐसी वेबसाइट पर काम कर रही है जो परित्यक्त बिल्लियों के लिए नए घर उपलब्ध कराती है। लोग अपनी पोस्ट कर सकते हैं
> पंजीकरण के बाद वेबसाइट पर बिल्लियाँ, लेकिन सभी नए पोस्ट के लिए इनमें से किसी एक से अनुमोदन की आवश्यकता होती है
> साइट मॉडरेटर. साइट मॉडरेटर के उपयोगकर्ता खातों में एक विशिष्ट ध्वज और डेटा होता है
> MongoDB डेटाबेस में संग्रहीत है। हर बार पोस्ट देखे जाने पर मॉडरेटर फ़्लैग की जाँच करना
> महंगा हो जाता है और यहां कैशिंग का उपयोग करना एक अच्छा विचार है।

साफ़ शब्दों में

> कैशिंग पैटर्न प्रदर्शन को बेहतर बनाने के लिए अक्सर आवश्यक डेटा को फास्ट-एक्सेस स्टोरेज में रखता है।

विकिपीडिया कहता है:

> कंप्यूटिंग में, कैश एक हार्डवेयर या सॉफ़्टवेयर घटक है जो भविष्य में डेटा संग्रहीत करता है
> उस डेटा के लिए अनुरोध तेजी से प्रस्तुत किए जा सकते हैं; कैश में संग्रहीत डेटा का परिणाम हो सकता है
> पहले की गणना या अन्यत्र संग्रहीत डेटा की एक प्रति। अनुरोध किए जाने पर कैश हिट होता है
> डेटा कैश में पाया जा सकता है, जबकि कैश मिस तब होता है जब ऐसा नहीं हो पाता। कैश हिट द्वारा परोसे जाते हैं
> कैश से डेटा पढ़ना, जो किसी परिणाम की पुन: गणना करने या धीमी गति से पढ़ने की तुलना में तेज़ है
> डेटा स्टोर; इस प्रकार, कैश से जितने अधिक अनुरोध दिए जा सकेंगे, सिस्टम उतना ही तेज़ होगा
> प्रदर्शन करता है.

**प्रोग्रामेटिक उदाहरण**

आइए सबसे पहले हमारे एप्लिकेशन के डेटा स्तर को देखें। दिलचस्प कक्षाएं `UserAccount` हैं
जो एक सरल जावा ऑब्जेक्ट है जिसमें उपयोगकर्ता खाता विवरण और `DbManager` इंटरफ़ेस शामिल है जो संभालता है
डेटाबेस से/इन ऑब्जेक्ट को पढ़ना और लिखना।

```java
@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserAccount {
  private String userId;
  private String userName;
  private String additionalInfo;
}

public interface DbManager {

  void connect();
  void disconnect();
  
  UserAccount readFromDb(String userId);
  UserAccount writeToDb(UserAccount userAccount);
  UserAccount updateDb(UserAccount userAccount);
  UserAccount upsertDb(UserAccount userAccount);
}
```

उदाहरण में, हम विभिन्न कैशिंग नीतियों का प्रदर्शन कर रहे हैं

* राइट-थ्रू एक ही लेनदेन में कैश और डीबी में डेटा लिखता है
* राइट-अराउंड डेटा को कैश के बजाय तुरंत डीबी में लिखता है
* राइट-बैक शुरू में डेटा को कैश में लिखता है जबकि डेटा केवल डीबी में लिखा जाता है
  जब कैश भर जाए
* कैश-साइड दोनों डेटा स्रोतों में डेटा को सिंक्रनाइज़ रखने की ज़िम्मेदारी को आगे बढ़ाता है
  आवेदन ही
* उपरोक्त रणनीतियों में रीड-थ्रू रणनीति भी शामिल है और यह डेटा लौटाती है
  यदि यह मौजूद है तो कॉल करने वाले को कैश, अन्यथा डीबी से क्वेरी करता है और इसे कैश में संग्रहीत करता है
  भविष्य के काम।

`LruCache` में कैश कार्यान्वयन एक डबल के साथ एक हैश तालिका है
लिंक्ड सूची। लिंक्ड-लिस्ट कैश में एलआरयू डेटा को कैप्चर करने और बनाए रखने में मदद करती है। कब
डेटा को क्वेरी किया जाता है (कैश से), जोड़ा जाता है (कैश में), या अपडेट किया जाता है, डेटा को सामने ले जाया जाता है
सूची में खुद को सबसे हाल ही में उपयोग किए गए डेटा के रूप में दर्शाया गया है। एलआरयू डेटा हमेशा अंत में होता है
सूची।

```java
@Slf4j
public class LruCache {

  static class Node {
    String userId;
    UserAccount userAccount;
    Node previous;
    Node next;

    public Node(String userId, UserAccount userAccount) {
      this.userId = userId;
      this.userAccount = userAccount;
    }
  }
  
  /* ... omitted details ... */

  public LruCache(int capacity) {
    this.capacity = capacity;
  }

  public UserAccount get(String userId) {
    if (cache.containsKey(userId)) {
      var node = cache.get(userId);
      remove(node);
      setHead(node);
      return node.userAccount;
    }
    return null;
  }

  public void set(String userId, UserAccount userAccount) {
    if (cache.containsKey(userId)) {
      var old = cache.get(userId);
      old.userAccount = userAccount;
      remove(old);
      setHead(old);
    } else {
      var newNode = new Node(userId, userAccount);
      if (cache.size() >= capacity) {
        LOGGER.info("# Cache is FULL! Removing {} from cache...", end.userId);
        cache.remove(end.userId); // remove LRU data from cache.
        remove(end);
        setHead(newNode);
      } else {
        setHead(newNode);
      }
      cache.put(userId, newNode);
    }
  }

  public boolean contains(String userId) {
    return cache.containsKey(userId);
  }
  
  public void remove(Node node) { /* ... */ }
  public void setHead(Node node) { /* ... */ }
  public void invalidate(String userId) { /* ... */ }
  public boolean isFull() { /* ... */ }
  public UserAccount getLruData() { /* ... */ }
  public void clear() { /* ... */ }
  public List<UserAccount> getCacheDataInListForm() { /* ... */ }
  public void setCapacity(int newCapacity) { /* ... */ }
}
```

अगली परत जिसे हम देखने जा रहे हैं वह `CacheStore` है जो विभिन्न कैशिंग को लागू करती है
रणनीतियाँ।

```java
@Slf4j
public class CacheStore {

  private static final int CAPACITY = 3;
  private static LruCache cache;
  private final DbManager dbManager;

  /* ... details omitted ... */

  public UserAccount readThrough(final String userId) {
    if (cache.contains(userId)) {
      LOGGER.info("# Found in Cache!");
      return cache.get(userId);
    }
    LOGGER.info("# Not found in cache! Go to DB!!");
    UserAccount userAccount = dbManager.readFromDb(userId);
    cache.set(userId, userAccount);
    return userAccount;
  }

  public void writeThrough(final UserAccount userAccount) {
    if (cache.contains(userAccount.getUserId())) {
      dbManager.updateDb(userAccount);
    } else {
      dbManager.writeToDb(userAccount);
    }
    cache.set(userAccount.getUserId(), userAccount);
  }

  public void writeAround(final UserAccount userAccount) {
    if (cache.contains(userAccount.getUserId())) {
      dbManager.updateDb(userAccount);
      // Cache data has been updated -- remove older
      cache.invalidate(userAccount.getUserId());
      // version from cache.
    } else {
      dbManager.writeToDb(userAccount);
    }
  }

  public static void clearCache() {
    if (cache != null) {
      cache.clear();
    }
  }

  public static void flushCache() {
    LOGGER.info("# flushCache...");
    Optional.ofNullable(cache)
        .map(LruCache::getCacheDataInListForm)
        .orElse(List.of())
        .forEach(DbManager::updateDb);
  }

  /* ... omitted the implementation of other caching strategies ... */

}
```

`AppManager` मुख्य वर्ग और एप्लिकेशन के बीच संचार में अंतर को पाटने में मदद करता है
पिछला भाग। इस वर्ग के माध्यम से DB कनेक्शन प्रारंभ किया जाता है। चुनी गई कैशिंग रणनीति/नीति है
यहाँ भी आरंभ किया गया। कैश का उपयोग करने से पहले, कैश का आकार सेट करना होगा। निर्भर करता है
चुनी गई कैशिंग नीति पर, `AppManager` `CacheStore` में उपयुक्त फ़ंक्शन को कॉल करेगा
कक्षा।

```java
@Slf4j
public final class AppManager {

  private static CachingPolicy cachingPolicy;
  private final DbManager dbManager;
  private final CacheStore cacheStore;

  private AppManager() {
  }

  public void initDb() { /* ... */ }

  public static void initCachingPolicy(CachingPolicy policy) { /* ... */ }

  public static void initCacheCapacity(int capacity) { /* ... */ }

  public UserAccount find(final String userId) {
    LOGGER.info("Trying to find {} in cache", userId);
    if (cachingPolicy == CachingPolicy.THROUGH
            || cachingPolicy == CachingPolicy.AROUND) {
      return cacheStore.readThrough(userId);
    } else if (cachingPolicy == CachingPolicy.BEHIND) {
      return cacheStore.readThroughWithWriteBackPolicy(userId);
    } else if (cachingPolicy == CachingPolicy.ASIDE) {
      return findAside(userId);
    }
    return null;
  }

  public void save(final UserAccount userAccount) {
    LOGGER.info("Save record!");
    if (cachingPolicy == CachingPolicy.THROUGH) {
      cacheStore.writeThrough(userAccount);
    } else if (cachingPolicy == CachingPolicy.AROUND) {
      cacheStore.writeAround(userAccount);
    } else if (cachingPolicy == CachingPolicy.BEHIND) {
      cacheStore.writeBehind(userAccount);
    } else if (cachingPolicy == CachingPolicy.ASIDE) {
      saveAside(userAccount);
    }
  }

  public static String printCacheContent() {
    return CacheStore.print();
  }

  /* ... details omitted ... */
}
```

एप्लिकेशन के मुख्य वर्ग में हम यही करते हैं।

```java
@Slf4j
public class App {

  public static void main(final String[] args) {
    boolean isDbMongo = isDbMongo(args);
    if(isDbMongo){
      LOGGER.info("Using the Mongo database engine to run the application.");
    } else {
      LOGGER.info("Using the 'in Memory' database to run the application.");
    }
    App app = new App(isDbMongo);
    app.useReadAndWriteThroughStrategy();
    String splitLine = "==============================================";
    LOGGER.info(splitLine);
    app.useReadThroughAndWriteAroundStrategy();
    LOGGER.info(splitLine);
    app.useReadThroughAndWriteBehindStrategy();
    LOGGER.info(splitLine);
    app.useCacheAsideStategy();
    LOGGER.info(splitLine);
  }

  public void useReadAndWriteThroughStrategy() {
    LOGGER.info("# CachingPolicy.THROUGH");
    appManager.initCachingPolicy(CachingPolicy.THROUGH);

    var userAccount1 = new UserAccount("001", "John", "He is a boy.");

    appManager.save(userAccount1);
    LOGGER.info(appManager.printCacheContent());
    appManager.find("001");
    appManager.find("001");
  }

  public void useReadThroughAndWriteAroundStrategy() { /* ... */ }

  public void useReadThroughAndWriteBehindStrategy() { /* ... */ }

  public void useCacheAsideStategy() { /* ... */ }
}
```

## क्लास डायग्राम

![alt text](../../../caching/etc/caching.png "Caching")

## प्रयोज्यता

जब कैशिंग पैटर्न का उपयोग करें

* एक ही संसाधन का बार-बार अधिग्रहण, आरंभीकरण और जारी करना अनावश्यक कारण बनता है
  प्रदर्शन उपरि.

## संबंधित पैटर्न

* [Proxy](https://java-design-patterns.com/patterns/proxy/)

## श्रेय

* [Write-through, write-around, write-back: Cache explained](http://www.computerweekly.com/feature/Write-through-write-around-write-back-Cache-explained)
* [Read-Through, Write-Through, Write-Behind, and Refresh-Ahead Caching](https://docs.oracle.com/cd/E15357_01/coh.360/e15723/cache_rtwtwbra.htm#COHDG5177)
* [Cache-Aside pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/cache-aside)
* [Java EE 8 High Performance: Master techniques such as memory optimization, caching, concurrency, and multithreading to achieve maximum performance from your enterprise applications](https://www.amazon.com/gp/product/178847306X/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=178847306X&linkId=e948720055599f248cdac47da9125ff4)
* [Java Performance: In-Depth Advice for Tuning and Programming Java 8, 11, and Beyond](https://www.amazon.com/gp/product/1492056111/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1492056111&linkId=7e553581559b9ec04221259e52004b08)
* [Effective Java](https://www.amazon.com/gp/product/B078H61SCH/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=B078H61SCH&linkId=f06607a0b48c76541ef19c5b8b9e7882)
* [Java Performance: The Definitive Guide: Getting the Most Out of Your Code](https://www.amazon.com/gp/product/1449358454/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1449358454&linkId=475c18363e350630cc0b39ab681b2687)
