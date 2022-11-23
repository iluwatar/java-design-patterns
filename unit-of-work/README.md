---
title: Unit Of Work
category: Architectural
language: en
tag:
 - Data access
 - Performance
---

## Intent

When a business transaction is completed, all the updates are sent as one big unit of work to be 
persisted in one go to minimize database round-trips. 

## Explanation

Real-world example

> Arms dealer has a database containing weapon information. Merchants all over the town are 
> constantly updating this information and it causes a high load on the database server. To make the 
> load more manageable we apply to Unit of Work pattern to send many small updates in batches.       

In plain words

> Unit of Work merges many small database updates in a single batch to optimize the number of 
> round-trips. 

[MartinFowler.com](https://martinfowler.com/eaaCatalog/unitOfWork.html) says

> Maintains a list of objects affected by a business transaction and coordinates the writing out of 
> changes and the resolution of concurrency problems.

**Programmatic Example**

Here's the `Weapon` entity that is being persisted in the database.

```java
@Getter
@RequiredArgsConstructor
public class Weapon {
    private final Integer id;
    private final String name;
}
```

The essence of the implementation is the `ArmsDealer` implementing the Unit of Work pattern. 
It maintains a map of database operations (`context`) that need to be done and when `commit` is 
called it applies them in a single batch.

```java
public interface IUnitOfWork<T> {
    
  String INSERT = "INSERT";
  String DELETE = "DELETE";
  String MODIFY = "MODIFY";

  void registerNew(T entity);

  void registerModified(T entity);

  void registerDeleted(T entity);

  void commit();
}

@Slf4j
@RequiredArgsConstructor
public class ArmsDealer implements IUnitOfWork<Weapon> {

    private final Map<String, List<Weapon>> context;
    private final WeaponDatabase weaponDatabase;

    @Override
    public void registerNew(Weapon weapon) {
        LOGGER.info("Registering {} for insert in context.", weapon.getName());
        register(weapon, UnitActions.INSERT.getActionValue());
    }

    @Override
    public void registerModified(Weapon weapon) {
        LOGGER.info("Registering {} for modify in context.", weapon.getName());
        register(weapon, UnitActions.MODIFY.getActionValue());

    }

    @Override
    public void registerDeleted(Weapon weapon) {
        LOGGER.info("Registering {} for delete in context.", weapon.getName());
        register(weapon, UnitActions.DELETE.getActionValue());
    }

    private void register(Weapon weapon, String operation) {
        var weaponsToOperate = context.get(operation);
        if (weaponsToOperate == null) {
            weaponsToOperate = new ArrayList<>();
        }
        weaponsToOperate.add(weapon);
        context.put(operation, weaponsToOperate);
    }

    /**
     * All UnitOfWork operations are batched and executed together on commit only.
     */
    @Override
    public void commit() {
        if (context == null || context.size() == 0) {
            return;
        }
        LOGGER.info("Commit started");
        if (context.containsKey(UnitActions.INSERT.getActionValue())) {
            commitInsert();
        }

        if (context.containsKey(UnitActions.MODIFY.getActionValue())) {
            commitModify();
        }
        if (context.containsKey(UnitActions.DELETE.getActionValue())) {
            commitDelete();
        }
        LOGGER.info("Commit finished.");
    }

    private void commitInsert() {
        var weaponsToBeInserted = context.get(UnitActions.INSERT.getActionValue());
        for (var weapon : weaponsToBeInserted) {
            LOGGER.info("Inserting a new weapon {} to sales rack.", weapon.getName());
            weaponDatabase.insert(weapon);
        }
    }

    private void commitModify() {
        var modifiedWeapons = context.get(UnitActions.MODIFY.getActionValue());
        for (var weapon : modifiedWeapons) {
            LOGGER.info("Scheduling {} for modification work.", weapon.getName());
            weaponDatabase.modify(weapon);
        }
    }

    private void commitDelete() {
        var deletedWeapons = context.get(UnitActions.DELETE.getActionValue());
        for (var weapon : deletedWeapons) {
            LOGGER.info("Scrapping {}.", weapon.getName());
            weaponDatabase.delete(weapon);
        }
    }
}
```

Here is how the whole app is put together.

```java
// create some weapons
var enchantedHammer = new Weapon(1, "enchanted hammer");
var brokenGreatSword = new Weapon(2, "broken great sword");
var silverTrident = new Weapon(3, "silver trident");

// create repository
var weaponRepository = new ArmsDealer(new HashMap<String, List<Weapon>>(), new WeaponDatabase());

// perform operations on the weapons
weaponRepository.registerNew(enchantedHammer);
weaponRepository.registerModified(silverTrident);
weaponRepository.registerDeleted(brokenGreatSword);
weaponRepository.commit();
```

Here is the console output.

```
21:39:21.984 [main] INFO com.iluwatar.unitofwork.ArmsDealer - Registering enchanted hammer for insert in context.
21:39:21.989 [main] INFO com.iluwatar.unitofwork.ArmsDealer - Registering silver trident for modify in context.
21:39:21.989 [main] INFO com.iluwatar.unitofwork.ArmsDealer - Registering broken great sword for delete in context.
21:39:21.989 [main] INFO com.iluwatar.unitofwork.ArmsDealer - Commit started
21:39:21.989 [main] INFO com.iluwatar.unitofwork.ArmsDealer - Inserting a new weapon enchanted hammer to sales rack.
21:39:21.989 [main] INFO com.iluwatar.unitofwork.ArmsDealer - Scheduling silver trident for modification work.
21:39:21.989 [main] INFO com.iluwatar.unitofwork.ArmsDealer - Scrapping broken great sword.
21:39:21.989 [main] INFO com.iluwatar.unitofwork.ArmsDealer - Commit finished.
```

## Class diagram

![alt text](./etc/unit-of-work.urm.png "unit-of-work")

## Applicability

Use the Unit Of Work pattern when

* To optimize the time taken for database transactions.
* To send changes to database as a unit of work which ensures atomicity of the transaction.
* To reduce the number of database calls.

## Tutorials

* [Repository and Unit of Work Pattern](https://www.programmingwithwolfgang.com/repository-and-unit-of-work-pattern/)
* [Unit of Work - a Design Pattern](https://mono.software/2017/01/13/unit-of-work-a-design-pattern/)

## Credits

* [Design Pattern - Unit Of Work Pattern](https://www.codeproject.com/Articles/581487/Unit-of-Work-Design-Pattern)
* [Unit Of Work](https://martinfowler.com/eaaCatalog/unitOfWork.html)
* [Patterns of Enterprise Application Architecture](https://www.amazon.com/gp/product/0321127420/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321127420&linkCode=as2&tag=javadesignpat-20&linkId=d9f7d37b032ca6e96253562d075fcc4a)
