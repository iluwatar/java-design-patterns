---
layout: pattern
title: Service Layer
folder: service-layer
permalink: /patterns/service-layer/zh
categories: Architectural
language: zh
tags:
 - Data access
---

## 目的

服务层是域逻辑的抽象。它用一个服务层来定义应用程序的边界
建立一组可用操作并协调每个操作中的应用程序响应。

## 解释

通常，应用程序需要不同类型的接口来存储数据和实现逻辑。
尽管它们的目的不同，但这些接口通常需要与应用程序进行公共交互来访问和
操作其数据并调用其业务逻辑。在每个模块中分别编码交互的逻辑
造成很多重复。最好将业务逻辑集中构建在单个服务层中，以避免这种情况的发生
这些陷阱。

真实世界的例子


我们正在编写一个应用程序来跟踪巫师、魔法书和咒语。巫师可能有咒语书和咒语书
可能有法术。

坦率地说

> 服务层是对应用程序业务逻辑的抽象。

维基百科上说

> 服务层是一个体系结构模式，应用于面向服务的设计范式中，其目的是
在服务目录中将服务组织为一组逻辑层。服务被分类为
特定的层共享功能。这有助于减少与管理服务相关的概念开销
库存，因为属于同一层的服务处理较小的活动集。

编程示例

这个示例应用程序演示了一个客户端“App”和一个允许的服务“MagicService”之间的交互
巫师之间的互动，魔法书和咒语。该服务采用三层体系结构实现
(实体、刀、服务)。

在这个解释中，我们将着眼于系统的一个垂直部分。让我们从实体层开始看看
“向导”类。这里没有显示的其他实体是“Spellbook”和“Spell”。

```java
@Entity
@Table(name = "WIZARD")
public class Wizard extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(name = "WIZARD_ID")
  private Long id;

  private String name;

  @ManyToMany(cascade = CascadeType.ALL)
  private Set<Spellbook> spellbooks;

  public Wizard() {
    spellbooks = new HashSet<>();
  }

  public Wizard(String name) {
    this();
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Spellbook> getSpellbooks() {
    return spellbooks;
  }

  public void setSpellbooks(Set<Spellbook> spellbooks) {
    this.spellbooks = spellbooks;
  }

  public void addSpellbook(Spellbook spellbook) {
    spellbook.getWizards().add(this);
    spellbooks.add(spellbook);
  }

  @Override
  public String toString() {
    return name;
  }
}
```


在实体层之上，我们有dao。对于“Wizard”，DAO层如下所示。
```java
public interface WizardDao extends Dao<Wizard> {

  Wizard findByName(String name);
}

public class WizardDaoImpl extends DaoBaseImpl<Wizard> implements WizardDao {

  @Override
  public Wizard findByName(String name) {
    Transaction tx = null;
    Wizard result;
    try (var session = getSessionFactory().openSession()) {
      tx = session.beginTransaction();
      var criteria = session.createCriteria(persistentClass);
      criteria.add(Restrictions.eq("name", name));
      result = (Wizard) criteria.uniqueResult();
      tx.commit();
    } catch (Exception e) {
      if (tx != null) {
        tx.rollback();
      }
      throw e;
    }
    return result;
  }
}
```

接下来，我们可以看看服务层，在我们的例子中，它由一个“MagicService”组成。
```java
public interface MagicService {

  List<Wizard> findAllWizards();

  List<Spellbook> findAllSpellbooks();

  List<Spell> findAllSpells();

  List<Wizard> findWizardsWithSpellbook(String name);

  List<Wizard> findWizardsWithSpell(String name);
}

public class MagicServiceImpl implements MagicService {

  private final WizardDao wizardDao;
  private final SpellbookDao spellbookDao;
  private final SpellDao spellDao;

  public MagicServiceImpl(WizardDao wizardDao, SpellbookDao spellbookDao, SpellDao spellDao) {
    this.wizardDao = wizardDao;
    this.spellbookDao = spellbookDao;
    this.spellDao = spellDao;
  }

  @Override
  public List<Wizard> findAllWizards() {
    return wizardDao.findAll();
  }

  @Override
  public List<Spellbook> findAllSpellbooks() {
    return spellbookDao.findAll();
  }

  @Override
  public List<Spell> findAllSpells() {
    return spellDao.findAll();
  }

  @Override
  public List<Wizard> findWizardsWithSpellbook(String name) {
    var spellbook = spellbookDao.findByName(name);
    return new ArrayList<>(spellbook.getWizards());
  }

  @Override
  public List<Wizard> findWizardsWithSpell(String name) {
    var spell = spellDao.findByName(name);
    var spellbook = spell.getSpellbook();
    return new ArrayList<>(spellbook.getWizards());
  }
}
```

最后，我们可以展示客户端“App”如何在服务层与“MagicService”交互。

```java
    var service = new MagicServiceImpl(wizardDao, spellbookDao, spellDao);
    LOGGER.info("Enumerating all wizards");
    service.findAllWizards().stream().map(Wizard::getName).forEach(LOGGER::info);
    LOGGER.info("Enumerating all spellbooks");
    service.findAllSpellbooks().stream().map(Spellbook::getName).forEach(LOGGER::info);
    LOGGER.info("Enumerating all spells");
    service.findAllSpells().stream().map(Spell::getName).forEach(LOGGER::info);
    LOGGER.info("Find wizards with spellbook 'Book of Idores'");
    var wizardsWithSpellbook = service.findWizardsWithSpellbook("Book of Idores");
    wizardsWithSpellbook.forEach(w -> LOGGER.info("{} has 'Book of Idores'", w.getName()));
    LOGGER.info("Find wizards with spell 'Fireball'");
    var wizardsWithSpell = service.findWizardsWithSpell("Fireball");
    wizardsWithSpell.forEach(w -> LOGGER.info("{} has 'Fireball'", w.getName()));
```


## 类图
![alt text](./etc/service-layer.png "Service Layer")

## 适用性
在以下情况使用服务层模式

* 你想在API下封装域逻辑
* 你需要用通用的逻辑和数据实现多个接口

## 鸣谢

* [Martin Fowler - Service Layer](http://martinfowler.com/eaaCatalog/serviceLayer.html)
* [Patterns of Enterprise Application Architecture](https://www.amazon.com/gp/product/0321127420/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321127420&linkCode=as2&tag=javadesignpat-20&linkId=d9f7d37b032ca6e96253562d075fcc4a)
