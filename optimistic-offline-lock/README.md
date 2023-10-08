---
title: Optimistic Offline Lock
category: Concurrency
language: en
tag:
- Data access
---

## Intent

Provide an ability to avoid concurrent changes of one record in relational databases.

## Explanation

Each transaction during object modifying checks equation of object's version before start of transaction
and before commit itself.

**Real world example**
> Since people love money, the best (and most common) example is banking system:
> imagine you have 100$ on your e-wallet and two people are trying to send you 50$ both at a time.
> Without locking, your system will start **two different thread**, each of whose will read your current balance
> and just add 50$. The last thread won't re-read balance and will just rewrite it.
> So, instead 200$ you will have only 150$.

**In plain words**
> Each transaction during object modifying will save object's last version and check it before saving.
> If it differs, the transaction will be rolled back.

**Wikipedia says**
> Optimistic concurrency control (OCC), also known as optimistic locking,
> is a concurrency control method applied to transactional systems such as
> relational database management systems and software transactional memory.

**Programmatic Example**  
Let's simulate the case from *real world example*. Imagine we have next entity:

```java
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bank card entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {

  /**
   * Primary key.
   */
  private long id;

  /**
   * Foreign key points to card's owner.
   */
  private long personId;

  /**
   * Sum of money. 
   */
  private float sum;

  /**
   * Current version of object;
   */
  private int version;
}
```

Then the correct modifying will be like this:

```java

import lombok.RequiredArgsConstructor;

/**
 * Service to update {@link Card} entity.
 */
@RequiredArgsConstructor
public class CardUpdateService implements UpdateService<Card> {

  private final JpaRepository<Card> cardJpaRepository;

  @Override
  @Transactional(rollbackFor = ApplicationException.class) //will roll back transaction in case ApplicationException
  public Card doUpdate(Card card, long cardId) {
    float additionalSum = card.getSum();
    Card cardToUpdate = cardJpaRepository.findById(cardId);
    int initialVersion = cardToUpdate.getVersion();
    float resultSum = cardToUpdate.getSum() + additionalSum;
    cardToUpdate.setSum(resultSum);
    //Maybe more complex business-logic e.g. HTTP-requests and so on

    if (initialVersion != cardJpaRepository.getEntityVersionById(cardId)) {
      String exMessage = String.format("Entity with id %s were updated in another transaction", cardId);
      throw new ApplicationException(exMessage);
    }

    cardJpaRepository.update(cardToUpdate);
    return cardToUpdate;
  }
}
```

## Applicability

Since optimistic locking can cause degradation of system's efficiency and reliability due to
many retries/rollbacks, it's important to use it safely. They are useful in case when transactions are not so long
and does not distributed among many microservices, when you need to reduce network/database overhead.

Important to note that you should not choose this approach in case when modifying one object
in different threads is common situation.

## Tutorials

- [Offline Concurrency Control](https://www.baeldung.com/cs/offline-concurrency-control)
- [Optimistic lock in JPA](https://www.baeldung.com/jpa-optimistic-locking)

## Known uses

- [Hibernate ORM](https://docs.jboss.org/hibernate/orm/4.3/devguide/en-US/html/ch05.html)

## Consequences

**Advantages**:

- Reduces network/database overhead
- Let to avoid database deadlock
- Improve the performance and scalability of the application

**Disadvantages**:

- Increases complexity of the application
- Requires mechanism of versioning
- Requires rollback/retry mechanisms

## Related patterns

- [Pessimistic Offline Lock](https://martinfowler.com/eaaCatalog/pessimisticOfflineLock.html)

## Credits

- [Source (Martin Fowler)](https://martinfowler.com/eaaCatalog/optimisticOfflineLock.html)
- [Advantages and disadvantages](https://www.linkedin.com/advice/0/what-benefits-drawbacks-using-optimistic)
- [Comparison of optimistic and pessimistic locks](https://www.linkedin.com/advice/0/what-advantages-disadvantages-using-optimistic)