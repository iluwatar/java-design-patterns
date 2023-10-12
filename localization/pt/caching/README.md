---
title: Cache
category: Behavioral
language: pt
tag:
  - Performance
  - Cloud distributed
---


## Propósito

O padrão de armazenamento em cache evita a custosa reaquisição de recursos por não liberá-los ou limpá-los imediatamente após o uso. Os recursos mantêm sua identidade, são mantidos em algum tipo de armazenamento de acesso rápido e são reutilizados para evitar a necessidade de buscá-los novamente.
## Explicação

Exemplo de uso em mundo real:

> Uma equipe está trabalhando em um site que oferece novos lares para gatos abandonados. As pessoas podem postar seus gatos no site depois de se registrarem, mas todos os novos posts precisam da aprovação de um dos moderadores do site. As contas dos moderadores do site tem um campo específico sinalizando isso e os dados são salvos em um banco de dados MongoDB. 
> Checar esse campo específico todas vez que um post é visualizado custa caro e é uma boa ideia usar armazenamento em cache nesse caso. 

De modo simples:

> O padrão de armazenamento em cache guarda dados que são requisitados frequentemente em um armazenamento de rápido acesso para melhorar a performance.

De acordo com a Wikipedia:

>Em computação, cache é um componente de hardware ou software que guarda dados que quando futuramente solicitados, podem ser servidos de maneira mais rapida. 
>Os dados armazenados em cache podem ser o resultado de algum cálculo anterior ou a cópia de outros dados que anteriormente estava armazenados em outro lugar. 
>Um "cache hit" (acerto de cache) acontece quando o dado requisitado é encontrado no cache, enquanto um "cache miss" (falta de cache) acontece quando os dado não é encontrado no cache.
>Os "cache hits" são lidos diretamente do cache, o que é mais rápido do que recalcular ou buscar os dados em outro lugar. Portante quando mais requisições forem atendidas pelo cache, mais rápido o sistema performa.

**Exemplo programático**

Vamos primeiro analisar a camada de dados da nossa aplicação. As classes que nos interessa são `UserAccount` que é um objeto Java simples que contém detalhes da conta do usuário e a interface `DbManager` que lida com a leitura e escrita desses objetos no banco de dados. 
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
No exemplos, vamos demonstrar várias políticas de armazenamento de cache diferentes:

* Write-through: Escreve os dados no cache e no DB em uma única transação.
* Write-around: Escreve os dados diretamente no DB em vez de escrever no cache.
  Write-behind: Escreve os dados no cache inicialmente enquanto os dados são escritos no banco de dados apenas quando o cache está cheio.
  Cache-aside: Delega a responsabilidade de manter os dados sincronizados em ambas as fontes de dados (cache e DB) para a própria aplicação.
* Read-through: Essa estratégia também está incluída nas estratégias mencionadas acima e retorna dados do cache (cache hit) para o chamador, se existir, caso contrário (cache miss), consulta o banco de dados e armazena os dados no cache para uso futuro.
  
The cache implementation in `LruCache` is a hash table accompanied by a doubly 
linked-list. The linked-list helps in capturing and maintaining the LRU data in the cache. When 
data is queried (from the cache), added (to the cache), or updated, the data is moved to the front 
of the list to depict itself as the most-recently-used data. The LRU data is always at the end of 
the list.

A implementação de cache em `LruCache` é uma tabela hash acompanhada por uma lista duplamente encadeada - ou lista duplamente ligada (Doubly linked list). 
Essa lista ligada ajuda a capturar e manter os dados LRU (Least Recently Used - Menos Recentemente Usados) no cache.
Quando os dados são consultados (do cache), adicionados (ao cache) ou atualizados, os dados são movidos para o início da lista como os dados mais recentemente usados. Os dados LRU estão sempre no final da lista.

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

A próxima camada que vamos analisar é `CacheStore`, que implementa as diferentes estratégias de armazenamento em cache.

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

A classe `AppManager` ajuda em fazer a ponte de comunicação entre a classe principal e o backend da aplicação. A conexão com o DB é inicializada por meio dessa classe. A estratégia ou política de cache escolhida também é inicializada aqui.
Antes do cache poder ser usado, é preciso definir o tamanho do cache.
Dependendo da estratégio de armazenamento de cache escolhida, a classe `AppManager` chamará a função (método) apropriada na classe `CacheStore`.

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

Essa é nossa classe princial e como usamos a implmentação:

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

## Diagrama de classes

![alt text](./etc/caching.png "Caching")

## Aplicabilidade

Utilize os padrões de armazenamento em cache quando:

* Repetidas aquisições, inicialização e liberação do mesmo recurso causam sobrecarga de desempenho desnecessária.

## Padrões relacionados

* [Proxy](https://java-design-patterns.com/patterns/proxy/)

## Créditos

* [Write-through, write-around, write-back: Cache explained](http://www.computerweekly.com/feature/Write-through-write-around-write-back-Cache-explained)
* [Read-Through, Write-Through, Write-Behind, and Refresh-Ahead Caching](https://docs.oracle.com/cd/E15357_01/coh.360/e15723/cache_rtwtwbra.htm#COHDG5177)
* [Cache-Aside pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/cache-aside)
* [Java EE 8 High Performance: Master techniques such as memory optimization, caching, concurrency, and multithreading to achieve maximum performance from your enterprise applications](https://www.amazon.com/gp/product/178847306X/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=178847306X&linkId=e948720055599f248cdac47da9125ff4)
* [Java Performance: In-Depth Advice for Tuning and Programming Java 8, 11, and Beyond](https://www.amazon.com/gp/product/1492056111/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1492056111&linkId=7e553581559b9ec04221259e52004b08)
* [Effective Java](https://www.amazon.com/gp/product/B078H61SCH/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=B078H61SCH&linkId=f06607a0b48c76541ef19c5b8b9e7882)
* [Java Performance: The Definitive Guide: Getting the Most Out of Your Code](https://www.amazon.com/gp/product/1449358454/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1449358454&linkId=475c18363e350630cc0b39ab681b2687)
