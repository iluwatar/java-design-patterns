---
title: Event Aggregator
shortTitle: Event Aggregator
category: Structural
language: ar
tag:
  - Reactive
---

## الاسم

مجمع الأحداث

## النية

قد يصبح النظام الذي يحتوي على العديد من الكائنات معقدًا عندما يريد العميل الاشتراك في الأحداث. يجب على العميل
البحث والتسجيل في
كل كائن على حدة، وإذا كان كل كائن يحتوي على العديد من الأحداث فإن كل حدث يتطلب اشتراكًا
منفصلًا. يعمل مجمع الأحداث كمصدر واحد
للأحداث للعديد من الكائنات. يقوم بالتسجيل في جميع الأحداث للكائنات المتعددة مما يسمح للعملاء
بالتسجيل فقط مع المجمع.

## الشرح

مثال واقعي

> يجلس الملك جوفري على عرش الحديد ويحكم الممالك السبع في وينترفل. يحصل على معظم معلوماته الهامة من يد الملك،
> وهو ثاني أقوى شخص في المملكة. يد الملك لديها العديد من المستشارين المقربين الذين يوفرون له معلومات هامة حول الأحداث التي تحدث في المملكة.

بكلمات بسيطة

> مجمع الأحداث هو وسيط للأحداث يقوم بجمع الأحداث من مصادر متعددة وتقديمها للمراقبين المسجلين.

**مثال برمجي**

في مثالنا البرمجي، نعرض تنفيذ نمط مجمع الأحداث. بعض الكائنات
هي مستمعون للأحداث، وبعضها الآخر هو مرسلو أحداث، والمجمع للأحداث يقوم بكلتا الوظيفتين.


```java
public interface EventObserver {
  void onEvent(Event e);
}

public abstract class EventEmitter {

  private final Map<Event, List<EventObserver>> observerLists;

  public EventEmitter() {
    observerLists = new HashMap<>();
  }

  public final void registerObserver(EventObserver obs, Event e) {
    ...
  }

  protected void notifyObservers(Event e) {
    ...
  }
}
```

`KingJoffrey` يستمع إلى أحداث `KingsHand`.


```java
@Slf4j
public class KingJoffrey implements EventObserver {
  @Override
  public void onEvent(Event e) {
    LOGGER.info("Received event from the King's Hand: {}", e.toString());
  }
}
```

`ReyMano` يستمع إلى الأحداث من مرؤوسيه `LordBaelish`، `LordVarys` و `Scout`.
ما يسمعه منهم، ينقله إلى "الملك جوفري".


```java
public class KingsHand extends EventEmitter implements EventObserver {

  public KingsHand() {
  }

  public KingsHand(EventObserver obs, Event e) {
    super(obs, e);
  }

  @Override
  public void onEvent(Event e) {
    notifyObservers(e);
  }
}
```

على سبيل المثال، `LordVarys` يجد خائنًا كل يوم أحد ويقوم بإبلاغ `KingsHand`.


```java
@Slf4j
public class LordVarys extends EventEmitter implements EventObserver {
  @Override
  public void timePasses(Weekday day) {
    if (day == Weekday.SATURDAY) {
      notifyObservers(Event.TRAITOR_DETECTED);
    }
  }
}
```

الجزء التالي يوضح كيفية بناء وربط الكائنات.


```java
    var kingJoffrey = new KingJoffrey();

    var kingsHand = new KingsHand();
    kingsHand.registerObserver(kingJoffrey, Event.TRAITOR_DETECTED);
    kingsHand.registerObserver(kingJoffrey, Event.STARK_SIGHTED);
    kingsHand.registerObserver(kingJoffrey, Event.WARSHIPS_APPROACHING);
    kingsHand.registerObserver(kingJoffrey, Event.WHITE_WALKERS_SIGHTED);

    var varys = new LordVarys();
    varys.registerObserver(kingsHand, Event.TRAITOR_DETECTED);
    varys.registerObserver(kingsHand, Event.WHITE_WALKERS_SIGHTED);

    var scout = new Scout();
    scout.registerObserver(kingsHand, Event.WARSHIPS_APPROACHING);
    scout.registerObserver(varys, Event.WHITE_WALKERS_SIGHTED);

    var baelish = new LordBaelish(kingsHand, Event.STARK_SIGHTED);

    var emitters = List.of(
        kingsHand,
        baelish,
        varys,
        scout
    );

    Arrays.stream(Weekday.values())
        .<Consumer<? super EventEmitter>>map(day -> emitter -> emitter.timePasses(day))
        .forEachOrdered(emitters::forEach);
```

الناتج في وحدة التحكم بعد تنفيذ المثال.

```
18:21:52.955 [main] INFO com.iluwatar.event.aggregator.KingJoffrey - Received event from the King's Hand: Warships approaching
18:21:52.960 [main] INFO com.iluwatar.event.aggregator.KingJoffrey - Received event from the King's Hand: White walkers sighted
18:21:52.960 [main] INFO com.iluwatar.event.aggregator.KingJoffrey - Received event from the King's Hand: Stark sighted
18:21:52.960 [main] INFO com.iluwatar.event.aggregator.KingJoffrey - Received event from the King's Hand: Traitor detected
```

## قابلية الاستخدام

استخدم نمط "مجمّع الأحداث" عندما

* يعد مجمّع الأحداث خيارًا جيدًا عندما يكون لديك العديد من الكائنات التي تعد مصادر محتملة للأحداث. بدلاً من جعل المراقب يتعامل مع التسجيل مع كل منها، يمكنك مركزية منطق التسجيل في مجمّع الأحداث. بالإضافة إلى تبسيط التسجيل، يبسط مجمّع الأحداث أيضًا مشكلات إدارة الذاكرة في استخدام المراقبين.

## الأنماط المتعلقة

* [Observer](https://java-design-patterns.com/patterns/observer/)

## الاعتمادات

* [مارتن فاولر - مجمّع الأحداث](http://martinfowler.com/eaaDev/EventAggregator.html)
