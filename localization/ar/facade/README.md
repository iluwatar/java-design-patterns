---
title: Facade
shortTitle: Facade
category: Structural
language: ar
tag:
  - Gang Of Four
  - Decoupling
---

## الهدف

توفير واجهة موحدة لمجموعة من واجهات النظام الفرعي. تقوم الواجهة البسيطة بتحديد واجهة تسهل استخدام النظام الفرعي.

## الشرح

مثال واقعي

> كيف يعمل منجم الذهب؟ "حسنًا، ينزل العمال لاستخراج الذهب!" تقول. هذا ما تعتقده لأنك تستخدم واجهة بسيطة يوفرها منجم الذهب، ولكن من الداخل يجب أن يقوم بالكثير من الأشياء لتحقيق ذلك. هذه الواجهة البسيطة للنظام الفرعي المعقد هي الواجهة الأمامية.

بإيجاز

> يوفر نمط الواجهة الأمامية واجهة مبسطة لنظام فرعي معقد.

تقول ويكيبيديا

> الواجهة الأمامية هي كائن يوفر واجهة مبسطة لجسم من الكود الأكبر، مثل مكتبة الفصول.

**مثال برمجي**

لنأخذ مثال منجم الذهب. هنا لدينا تسلسل عمال الأقزام في المنجم. أولاً، لدينا فئة أساسية `DwarvenMineWorker`:


```java

@Slf4j
public abstract class DwarvenMineWorker {

    public void goToSleep() {
        LOGGER.info("{} goes to sleep.", name());
    }

    public void wakeUp() {
        LOGGER.info("{} wakes up.", name());
    }

    public void goHome() {
        LOGGER.info("{} goes home.", name());
    }

    public void goToMine() {
        LOGGER.info("{} goes to the mine.", name());
    }

    private void action(Action action) {
        switch (action) {
            case GO_TO_SLEEP -> goToSleep();
            case WAKE_UP -> wakeUp();
            case GO_HOME -> goHome();
            case GO_TO_MINE -> goToMine();
            case WORK -> work();
            default -> LOGGER.info("Undefined action");
        }
    }

    public void action(Action... actions) {
        Arrays.stream(actions).forEach(this::action);
    }

    public abstract void work();

    public abstract String name();

    enum Action {
        GO_TO_SLEEP, WAKE_UP, GO_HOME, GO_TO_MINE, WORK
    }
}
```

ثم لدينا الفئات المحددة للأقزام `DwarvenTunnelDigger`، `DwarvenGoldDigger` و `DwarvenCartOperator`:


```java
@Slf4j
public class DwarvenTunnelDigger extends DwarvenMineWorker {

  @Override
  public void work() {
    LOGGER.info("{} creates another promising tunnel.", name());
  }

  @Override
  public String name() {
    return "Dwarven tunnel digger";
  }
}

@Slf4j
public class DwarvenGoldDigger extends DwarvenMineWorker {

  @Override
  public void work() {
    LOGGER.info("{} digs for gold.", name());
  }

  @Override
  public String name() {
    return "Dwarf gold digger";
  }
}

@Slf4j
public class DwarvenCartOperator extends DwarvenMineWorker {

  @Override
  public void work() {
    LOGGER.info("{} moves gold chunks out of the mine.", name());
  }

  @Override
  public String name() {
    return "Dwarf cart operator";
  }
}

```

لإدارة جميع هؤلاء العمال في منجم الذهب، لدينا `FachadaDwarvenGoldmine`:


```java
public class DwarvenGoldmineFacade {

  private final List<DwarvenMineWorker> workers;

  public DwarvenGoldmineFacade() {
      workers = List.of(
            new DwarvenGoldDigger(),
            new DwarvenCartOperator(),
            new DwarvenTunnelDigger());
  }

  public void startNewDay() {
    makeActions(workers, DwarvenMineWorker.Action.WAKE_UP, DwarvenMineWorker.Action.GO_TO_MINE);
  }

  public void digOutGold() {
    makeActions(workers, DwarvenMineWorker.Action.WORK);
  }

  public void endDay() {
    makeActions(workers, DwarvenMineWorker.Action.GO_HOME, DwarvenMineWorker.Action.GO_TO_SLEEP);
  }

  private static void makeActions(Collection<DwarvenMineWorker> workers,
      DwarvenMineWorker.Action... actions) {
    workers.forEach(worker -> worker.action(actions));
  }
}
```

الآن سنستخدم الواجهة:


```java
var facade = new DwarvenGoldmineFacade();
facade.startNewDay();
facade.digOutGold();
facade.endDay();
```

إخراج البرنامج:


```java
// Dwarf gold digger wakes up.
// Dwarf gold digger goes to the mine.
// Dwarf cart operator wakes up.
// Dwarf cart operator goes to the mine.
// Dwarven tunnel digger wakes up.
// Dwarven tunnel digger goes to the mine.
// Dwarf gold digger digs for gold.
// Dwarf cart operator moves gold chunks out of the mine.
// Dwarven tunnel digger creates another promising tunnel.
// Dwarf gold digger goes home.
// Dwarf gold digger goes to sleep.
// Dwarf cart operator goes home.
// Dwarf cart operator goes to sleep.
// Dwarven tunnel digger goes home.
// Dwarven tunnel digger goes to sleep.
```

## مخطط الفئات

![alt text](./etc/facade.urm.png "مخطط فئة نمط الواجهة")

## قابلية التطبيق

استخدم نمط الواجهة عندما:

* ترغب في توفير واجهة بسيطة إلى نظام فرعي معقد. غالبًا ما تصبح الأنظمة الفرعية أكثر تعقيدًا مع تطورها. معظم الأنماط، عند تطبيقها، تؤدي إلى المزيد من الفئات الأصغر. هذا يجعل النظام الفرعي أكثر قابلية لإعادة الاستخدام وأسهل في التخصيص، ولكن أيضًا يصبح أكثر صعوبة في الاستخدام للعملاء الذين لا يحتاجون إلى تخصيصه. يمكن أن توفر الواجهة عرضًا بسيطًا افتراضيًا للنظام الفرعي الذي يكفي لمعظم العملاء. فقط العملاء الذين يحتاجون إلى مزيد من التخصيص سيضطرون للنظر إلى ما وراء الواجهة.
* هناك العديد من الاعتمادات بين العملاء وفئات تنفيذ التجريد. إدخال واجهة لفصل النظام الفرعي عن العملاء والأنظمة الفرعية الأخرى، مما يعزز الاستقلالية وقابلية النقل للنظام الفرعي.
* ترغب في تقسيم أنظمتك الفرعية. استخدم واجهة لتعريف نقطة دخول إلى كل مستوى من النظام الفرعي. إذا كانت الأنظمة الفرعية تعتمد على بعضها البعض، يمكنك تبسيط الاعتمادات بينها من خلال جعلها تتواصل مع بعضها البعض فقط من خلال واجهاتها.

## الدروس التعليمية

* [DigitalOcean](https://www.digitalocean.com/community/tutorials/facade-design-pattern-in-java)

* [Refactoring Guru](https://refactoring.guru/design-patterns/facade)
* [GeekforGeeks](https://www.geeksforgeeks.org/facade-design-pattern-introduction/)
* [Tutorialspoint](https://www.tutorialspoint.com/design_pattern/facade_pattern.htm)

## الاعتمادات

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
