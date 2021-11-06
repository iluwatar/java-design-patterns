---
layout: pattern
title: Service Layer
folder: service-layer
permalink: /patterns/service-layer/
categories: Architectural
language: en
tags:
 - Data access
---

## Intent

Service Layer is an abstraction over domain logic. It defines application's boundary with a layer of services that 
establishes a set of available operations and coordinates the application's response in each operation.

## Explanation

Typically applications require different kinds of interfaces to the data they store and the logic they implement. 
Despite their different purposes, these interfaces often need common interactions with the application to access and 
manipulate its data and invoke its business logic. Encoding the logic of the interactions separately in each module 
causes a lot of duplication. It's better to centralize building the business logic inside single Service Layer to avoid 
these pitfalls.
 
Real world example

> We are writing an application that tracks wizards, spellbooks and spells. Wizards may have spellbooks and spellbooks 
may have spells.           

In plain words

> Service Layer is an abstraction over application's business logic.   

Wikipedia says

> Service layer is an architectural pattern, applied within the service-orientation design paradigm, which aims to 
organize the services, within a service inventory, into a set of logical layers. Services that are categorized into 
a particular layer share functionality. This helps to reduce the conceptual overhead related to managing the service 
inventory, as the services belonging to the same layer address a smaller set of activities.

**Programmatic Example**

The example application demonstrates interactions between a client `App` and a service `MagicService` that allows
interaction between wizards, spellbooks and spells. The service is implemented with 3-layer architecture 
(entity, dao, service).

For this explanation we are looking at one vertical slice of the system. Let's start from the entity layer and look at 
`Wizard` class. Other entities not shown here are `Spellbook` and `Spell`.

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

Above the entity layer we have DAOs. For `Wizard` the DAO layer looks as follows.

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

Next we can look at the Service Layer, which in our case consists of a single `MagicService`.

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

And finally we can show how the client `App` interacts with `MagicService` in the Service Layer.

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


## Class diagram
![alt text](./etc/service-layer.png "Service Layer")

## Applicability
Use the Service Layer pattern when

* You want to encapsulate domain logic under API
* You need to implement multiple interfaces with common logic and data

## Credits

* [Martin Fowler - Service Layer](http://martinfowler.com/eaaCatalog/serviceLayer.html)
* [Patterns of Enterprise Application Architecture](https://www.amazon.com/gp/product/0321127420/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321127420&linkCode=as2&tag=javadesignpat-20&linkId=d9f7d37b032ca6e96253562d075fcc4a)
