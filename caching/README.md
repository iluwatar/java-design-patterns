---
layout: pattern
title: Caching
folder: caching
permalink: /patterns/caching/
categories: Behavioral
language: en
tags:
  - Performance
  - Cloud distributed
---

## Intent

The caching pattern avoids expensive re-acquisition of resources by not releasing them immediately 
after use. The resources retain their identity, are kept in some fast-access storage, and are 
re-used to avoid having to acquire them again.

## Explanation

Real world example

> A team is working on a website that provides new homes for abandoned cats. People can post their
> cats on the website after registering, but all the new posts require approval from one of the
> site moderators. The user accounts of the site moderators contain a specific flag and the data
> is stored in a MongoDB database. Checking for the moderator flag each time a post is viewed 
> becomes expensive and it's a good idea to utilize caching here.

In plain words

> Caching pattern keeps frequently needed data in fast-access storage to improve performance.

Wikipedia says:

> In computing, a cache is a hardware or software component that stores data so that future 
> requests for that data can be served faster; the data stored in a cache might be the result of 
> an earlier computation or a copy of data stored elsewhere. A cache hit occurs when the requested 
> data can be found in a cache, while a cache miss occurs when it cannot. Cache hits are served by 
> reading data from the cache, which is faster than recomputing a result or reading from a slower 
> data store; thus, the more requests that can be served from the cache, the faster the system 
> performs.

**Programmatic Example**

Let's first look at the data layer of our application. The interesting classes are `UserAccount`
which is a simple Java object containing the user account details, and `DbManager` which handles
reading and writing of these objects to/from MongoDB database.

```java
@Setter
@Getter
@AllArgsConstructor
@ToString
public class UserAccount {
  private String userId;
  private String userName;
  private String additionalInfo;
}

@Slf4j
public final class DbManager {

  private static MongoClient mongoClient;
  private static MongoDatabase db;

  private DbManager() { /*...*/ }

  public static void createVirtualDb() { /*...*/ }

  public static void connect() throws ParseException { /*...*/ }

  public static UserAccount readFromDb(String userId) { /*...*/ }

  public static void writeToDb(UserAccount userAccount) { /*...*/ }

  public static void updateDb(UserAccount userAccount) { /*...*/ }

  public static void upsertDb(UserAccount userAccount) { /*...*/ }
}
```

In the example, we are demonstrating various different caching policies

* Write-through writes data to the cache and DB in a single transaction
* Write-around writes data immediately into the DB instead of the cache
* Write-behind writes data into the cache initially whilst the data is only written into the DB 
  when the cache is full
* Cache-aside pushes the responsibility of keeping the data synchronized in both data sources to 
  the application itself
* Read-through strategy is also included in the aforementioned strategies and it returns data from 
  the cache to the caller if it exists, otherwise queries from DB and stores it into the cache for 
  future use.
  
The cache implementation in `LruCache` is a hash table accompanied by a doubly 
linked-list. The linked-list helps in capturing and maintaining the LRU data in the cache. When 
data is queried (from the cache), added (to the cache), or updated, the data is moved to the front 
of the list to depict itself as the most-recently-used data. The LRU data is always at the end of 
the list.

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

The next layer we are going to look at is `CacheStore` which implements the different caching
strategies.

```java
@Slf4j
public class CacheStore {

  private static LruCache cache;

  /* ... details omitted ... */

  public static UserAccount readThrough(String userId) {
    if (cache.contains(userId)) {
      LOGGER.info("# Cache Hit!");
      return cache.get(userId);
    }
    LOGGER.info("# Cache Miss!");
    UserAccount userAccount = DbManager.readFromDb(userId);
    cache.set(userId, userAccount);
    return userAccount;
  }

  public static void writeThrough(UserAccount userAccount) {
    if (cache.contains(userAccount.getUserId())) {
      DbManager.updateDb(userAccount);
    } else {
      DbManager.writeToDb(userAccount);
    }
    cache.set(userAccount.getUserId(), userAccount);
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

`AppManager` helps to bridge the gap in communication between the main class and the application's
back-end. DB connection is initialized through this class. The chosen caching strategy/policy is
also initialized here. Before the cache can be used, the size of the cache has to be set. Depending 
on the chosen caching policy, `AppManager` will call the appropriate function in the `CacheStore` 
class.

```java
@Slf4j
public final class AppManager {

  private static CachingPolicy cachingPolicy;

  private AppManager() {
  }

  public static void initDb(boolean useMongoDb) { /* ... */ }

  public static void initCachingPolicy(CachingPolicy policy) { /* ... */ }

  public static void initCacheCapacity(int capacity) { /* ... */ }

  public static UserAccount find(String userId) {
    if (cachingPolicy == CachingPolicy.THROUGH || cachingPolicy == CachingPolicy.AROUND) {
      return CacheStore.readThrough(userId);
    } else if (cachingPolicy == CachingPolicy.BEHIND) {
      return CacheStore.readThroughWithWriteBackPolicy(userId);
    } else if (cachingPolicy == CachingPolicy.ASIDE) {
      return findAside(userId);
    }
    return null;
  }

  public static void save(UserAccount userAccount) {
    if (cachingPolicy == CachingPolicy.THROUGH) {
      CacheStore.writeThrough(userAccount);
    } else if (cachingPolicy == CachingPolicy.AROUND) {
      CacheStore.writeAround(userAccount);
    } else if (cachingPolicy == CachingPolicy.BEHIND) {
      CacheStore.writeBehind(userAccount);
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

Here is what we do in the main class of the application.

```java
@Slf4j
public class App {

  public static void main(String[] args) {
    AppManager.initDb(false);
    AppManager.initCacheCapacity(3);
    var app = new App();
    app.useReadAndWriteThroughStrategy();
    app.useReadThroughAndWriteAroundStrategy();
    app.useReadThroughAndWriteBehindStrategy();
    app.useCacheAsideStategy();
  }

  public void useReadAndWriteThroughStrategy() {
    LOGGER.info("# CachingPolicy.THROUGH");
    AppManager.initCachingPolicy(CachingPolicy.THROUGH);
    var userAccount1 = new UserAccount("001", "John", "He is a boy.");
    AppManager.save(userAccount1);
    LOGGER.info(AppManager.printCacheContent());
    AppManager.find("001");
    AppManager.find("001");
  }

  public void useReadThroughAndWriteAroundStrategy() { /* ... */ }

  public void useReadThroughAndWriteBehindStrategy() { /* ... */ }

  public void useCacheAsideStategy() { /* ... */ }
}
```

Finally, here is some of the console output from the program.

```
12:32:53.845 [main] INFO com.iluwatar.caching.App - # CachingPolicy.THROUGH
12:32:53.900 [main] INFO com.iluwatar.caching.App - 
--CACHE CONTENT--
UserAccount(userId=001, userName=John, additionalInfo=He is a boy.)
----
```

## Class diagram

![alt text](./etc/caching.png "Caching")

## Applicability

Use the Caching pattern(s) when

* Repetitious acquisition, initialization, and release of the same resource cause unnecessary 
  performance overhead.

## Related patterns

* [Proxy](https://java-design-patterns.com/patterns/proxy/)

## Credits

* [Write-through, write-around, write-back: Cache explained](http://www.computerweekly.com/feature/Write-through-write-around-write-back-Cache-explained)
* [Read-Through, Write-Through, Write-Behind, and Refresh-Ahead Caching](https://docs.oracle.com/cd/E15357_01/coh.360/e15723/cache_rtwtwbra.htm#COHDG5177)
* [Cache-Aside pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/cache-aside)
* [Java EE 8 High Performance: Master techniques such as memory optimization, caching, concurrency, and multithreading to achieve maximum performance from your enterprise applications](https://www.amazon.com/gp/product/178847306X/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=178847306X&linkId=e948720055599f248cdac47da9125ff4)
* [Java Performance: In-Depth Advice for Tuning and Programming Java 8, 11, and Beyond](https://www.amazon.com/gp/product/1492056111/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1492056111&linkId=7e553581559b9ec04221259e52004b08)
* [Effective Java](https://www.amazon.com/gp/product/B078H61SCH/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=B078H61SCH&linkId=f06607a0b48c76541ef19c5b8b9e7882)
* [Java Performance: The Definitive Guide: Getting the Most Out of Your Code](https://www.amazon.com/gp/product/1449358454/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1449358454&linkId=475c18363e350630cc0b39ab681b2687)
