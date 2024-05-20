---
title: Service Layer
category: Architectural
language: en
tag:
    - API design
    - Business
    - Decoupling
    - Enterprise patterns
    - Layered architecture
---

## Also known as

* Application Facade

## Intent

Encapsulate business logic in a distinct layer to promote separation of concerns and to provide a well-defined API for the presentation layer.

## Explanation

Real-world example

> Consider a large restaurant where orders are taken by waitstaff and then sent to different kitchen sections (e.g., grill, salad, dessert). Each section specializes in a part of the meal, but the waitstaff don't interact directly with the kitchen staff. Instead, all orders go through a head chef who coordinates the workflow. The head chef acts like the service layer, handling the business logic (order coordination) and providing a unified interface for the waitstaff (presentation layer) to interact with the kitchen (data access layer).

In plain words

> A pattern that encapsulates business logic into a distinct layer to promote separation of concerns and provide a clear API for the presentation layer.

Wikipedia says

> Service layer is an architectural pattern, applied within the service-orientation design paradigm, which aims to organize the services, within a service inventory, into a set of logical layers. Services that are categorized into a particular layer share functionality. This helps to reduce the conceptual overhead related to managing the service inventory, as the services belonging to the same layer address a smaller set of activities.

**Programmatic Example**

The example application demonstrates interactions between a client `App` and a service `MagicService` that allows interaction between wizards, spellbooks and spells. The service is implemented with 3-layer architecture
(entity, dao, service).

For this explanation we are looking at one vertical slice of the system. Let's start from the entity layer and look at `Wizard` class. Other entities not shown here are `Spellbook` and `Spell`.

```java

@Entity
@Table(name = "WIZARD")
@Getter
@Setter
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

The program output:

```
INFO  [2024-05-20 10:00:24,548] com.iluwatar.servicelayer.app.App: Enumerating all wizards
INFO  [2024-05-20 10:00:24,550] com.iluwatar.servicelayer.app.App: Aderlard Boud
INFO  [2024-05-20 10:00:24,550] com.iluwatar.servicelayer.app.App: Anaxis Bajraktari
INFO  [2024-05-20 10:00:24,551] com.iluwatar.servicelayer.app.App: Xuban Munoa
INFO  [2024-05-20 10:00:24,551] com.iluwatar.servicelayer.app.App: Blasius Dehooge
INFO  [2024-05-20 10:00:24,551] com.iluwatar.servicelayer.app.App: Enumerating all spellbooks
INFO  [2024-05-20 10:00:24,554] com.iluwatar.servicelayer.app.App: Book of Orgymon
INFO  [2024-05-20 10:00:24,554] com.iluwatar.servicelayer.app.App: Book of Aras
INFO  [2024-05-20 10:00:24,554] com.iluwatar.servicelayer.app.App: Book of Kritior
INFO  [2024-05-20 10:00:24,554] com.iluwatar.servicelayer.app.App: Book of Tamaex
INFO  [2024-05-20 10:00:24,554] com.iluwatar.servicelayer.app.App: Book of Idores
INFO  [2024-05-20 10:00:24,554] com.iluwatar.servicelayer.app.App: Book of Opaen
INFO  [2024-05-20 10:00:24,554] com.iluwatar.servicelayer.app.App: Book of Kihione
INFO  [2024-05-20 10:00:24,554] com.iluwatar.servicelayer.app.App: Enumerating all spells
INFO  [2024-05-20 10:00:24,558] com.iluwatar.servicelayer.app.App: Ice dart
INFO  [2024-05-20 10:00:24,558] com.iluwatar.servicelayer.app.App: Invisibility
INFO  [2024-05-20 10:00:24,558] com.iluwatar.servicelayer.app.App: Stun bolt
INFO  [2024-05-20 10:00:24,558] com.iluwatar.servicelayer.app.App: Confusion
INFO  [2024-05-20 10:00:24,558] com.iluwatar.servicelayer.app.App: Darkness
INFO  [2024-05-20 10:00:24,558] com.iluwatar.servicelayer.app.App: Fireball
INFO  [2024-05-20 10:00:24,558] com.iluwatar.servicelayer.app.App: Enchant weapon
INFO  [2024-05-20 10:00:24,558] com.iluwatar.servicelayer.app.App: Rock armour
INFO  [2024-05-20 10:00:24,558] com.iluwatar.servicelayer.app.App: Light
INFO  [2024-05-20 10:00:24,558] com.iluwatar.servicelayer.app.App: Bee swarm
INFO  [2024-05-20 10:00:24,559] com.iluwatar.servicelayer.app.App: Haste
INFO  [2024-05-20 10:00:24,559] com.iluwatar.servicelayer.app.App: Levitation
INFO  [2024-05-20 10:00:24,559] com.iluwatar.servicelayer.app.App: Magic lock
INFO  [2024-05-20 10:00:24,559] com.iluwatar.servicelayer.app.App: Summon hell bat
INFO  [2024-05-20 10:00:24,559] com.iluwatar.servicelayer.app.App: Water walking
INFO  [2024-05-20 10:00:24,559] com.iluwatar.servicelayer.app.App: Magic storm
INFO  [2024-05-20 10:00:24,559] com.iluwatar.servicelayer.app.App: Entangle
INFO  [2024-05-20 10:00:24,559] com.iluwatar.servicelayer.app.App: Find wizards with spellbook 'Book of Idores'
INFO  [2024-05-20 10:00:24,560] com.iluwatar.servicelayer.app.App: Xuban Munoa has 'Book of Idores'
INFO  [2024-05-20 10:00:24,560] com.iluwatar.servicelayer.app.App: Find wizards with spell 'Fireball'
INFO  [2024-05-20 10:00:24,562] com.iluwatar.servicelayer.app.App: Aderlard Boud has 'Fireball'
```

## Class diagram

![Service Layer](./etc/service-layer.png "Service Layer")

## Applicability

* Use when you need to separate business logic from presentation logic.
* Ideal for applications with complex business rules and workflows.
* Suitable for projects requiring a clear API for the presentation layer.

## Known Uses

* Java EE applications where Enterprise JavaBeans (EJB) are used to implement the service layer.
* Spring Framework applications using the @Service annotation to denote service layer classes.
* Web applications that need to separate business logic from controller logic.

## Consequences

Benefits:

* Promotes code reuse by encapsulating business logic in one place.
* Enhances testability by isolating business logic.
* Improves maintainability and flexibility of the application.

Trade-offs:

* May introduce additional complexity by adding another layer to the application.
* Can result in performance overhead due to the extra layer of abstraction.

## Related Patterns

* [Facade](https://java-design-patterns.com/patterns/facade/): Simplifies interactions with complex subsystems by providing a unified interface.
* [DAO (Data Access Object)](https://java-design-patterns.com/patterns/dao/): Often used together with the Service Layer to handle data persistence.
* [MVC (Model-View-Controller)](https://java-design-patterns.com/patterns/model-view-controller/): The Service Layer can be used to encapsulate business logic in the model component.

## Credits

* [Core J2EE Patterns: Best Practices and Design Strategies](https://amzn.to/4cAbDap)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
* [Spring in Action](https://amzn.to/4asnpSG)
* [Martin Fowler - Service Layer](http://martinfowler.com/eaaCatalog/serviceLayer.html)
