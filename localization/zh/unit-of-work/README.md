---
title: Unit Of Work
shortTitle: Unit Of Work
category: Architectural
language: zn
tag:
 - Data access
 - Performance
---

## 又被称为
工作单元

## 目的

当一个业务事务完成时，所有的更新都作为一个大的工作单元一次性发送，以最小化数据库的往返次数进行持久化。

## 解释

现实世界例子

> 武器商人拥有一个包含武器信息的数据库。
> 全城的商贩们都在不断地更新这些信息，这导致数据库服务器的负载很高。
> 为了使负载更易于管理，我们应用了工作单元模式，将许多小的更新批量发送。

用直白的话来说

> 工作单元将许多小的数据库更新合并成一个批次。
> 以优化往返次数。

[MartinFowler.com](https://martinfowler.com/eaaCatalog/unitOfWork.html) 中说

> 维护一个受业务事务影响的对象列表。
> 并协调写出更改和解决并发问题。

**编程样例**

以下是要持久化到数据库中的 `Weapon` 的实体。

```java
@Getter
@RequiredArgsConstructor
public class Weapon {
    private final Integer id;
    private final String name;
}
```

实现的核心是 `ArmsDealer` 实现了工作单元模式。
它维护了一个需要完成的数据库操作映射 (`context`) 当调用 `commit` 时
它会一次性批量应用这些操作。

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

以下描述了整个应用是如何组装起来的。

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

以下是控制台输出。

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

## 类图

![alt text](./etc/unit-of-work.urm.png "unit-of-work")

## 应用

在以下情况时使用单元模式

* 为了优化数据库事务所需的时间。
* 作为工作单元将更改发送到数据库，确保事务的原子性。
* 为了减少数据库调用的次数。

## 教程

* [Repository and Unit of Work Pattern](https://www.programmingwithwolfgang.com/repository-and-unit-of-work-pattern/)
* [Unit of Work - a Design Pattern](https://mono.software/2017/01/13/unit-of-work-a-design-pattern/)

## 鸣谢

* [Design Pattern - Unit Of Work Pattern](https://www.codeproject.com/Articles/581487/Unit-of-Work-Design-Pattern)
* [Unit Of Work](https://martinfowler.com/eaaCatalog/unitOfWork.html)
* [Patterns of Enterprise Application Architecture](https://www.amazon.com/gp/product/0321127420/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321127420&linkCode=as2&tag=javadesignpat-20&linkId=d9f7d37b032ca6e96253562d075fcc4a)
