---
title: Command
shortTitle: Command
category: Behavioral
language: ar
tag:
    - Gang of Four
---

## أيضًا يعرف بـ

* إجراء
* معاملة

## الهدف

يُغلف نمط التصميم Command الطلب ككائن، مما يسمح بتمرير العملاء مع قوائم الانتظار، الطلبات، والعمليات. كما يدعم أيضًا التراجع عن العمليات.

## الشرح

### مثال واقعي

> يوجد ساحر يلقي تعويذات على عفريت. يتم تنفيذ التعويذات على العفريت واحدة تلو الأخرى. التعويذة الأولى تصغر العفريت والتعويذة الثانية تجعله غير مرئي. بعد ذلك، يقوم الساحر بالتراجع عن التعويذات واحدة تلو الأخرى. كل تعويذة هي كائن أمر يمكن التراجع عنها.

### بكلمات بسيطة:

> تخزين الطلبات ككائنات أمر يسمح بتنفيذ الإجراء أو التراجع عنه في وقت لاحق.

### تقول ويكيبيديا:

> في البرمجة الكائنية التوجه، نمط الأمر هو نمط تصميم سلوكي حيث يتم استخدام كائن لتغليف كافة المعلومات اللازمة لتنفيذ إجراء أو تحفيز حدث في وقت لاحق.

### مثال برمجي

إليك الكود البرمجي مع الساحر `Wizard` والعفريت `Goblin`. دعونا نبدأ بفئة الساحر `Wizard`.


```java

@Slf4j
public class Wizard {

    private final Deque<Runnable> undoStack = new LinkedList<>();
    private final Deque<Runnable> redoStack = new LinkedList<>();

    public Wizard() {
    }

    public void castSpell(Runnable runnable) {
        runnable.run();
        undoStack.offerLast(runnable);
    }

    public void undoLastSpell() {
        if (!undoStack.isEmpty()) {
            var previousSpell = undoStack.pollLast();
            redoStack.offerLast(previousSpell);
            previousSpell.run();
        }
    }

    public void redoLastSpell() {
        if (!redoStack.isEmpty()) {
            var previousSpell = redoStack.pollLast();
            undoStack.offerLast(previousSpell);
            previousSpell.run();
        }
    }

    @Override
    public String toString() {
        return "Wizard";
    }
}
```

### التالي، لدينا العفريت `Goblin` الذي هو الهدف `Target` للتعويذات.


```java

@Slf4j
public abstract class Target {

    private Size size;

    private Visibility visibility;

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    @Override
    public abstract String toString();

    public void printStatus() {
        LOGGER.info("{}, [size={}] [visibility={}]", this, getSize(), getVisibility());
    }
}

public class Goblin extends Target {

    public Goblin() {
        setSize(Size.NORMAL);
        setVisibility(Visibility.VISIBLE);
    }

    @Override
    public String toString() {
        return "Goblin";
    }

    public void changeSize() {
        var oldSize = getSize() == Size.NORMAL ? Size.SMALL : Size.NORMAL;
        setSize(oldSize);
    }

    public void changeVisibility() {
        var visible = getVisibility() == Visibility.INVISIBLE
                ? Visibility.VISIBLE : Visibility.INVISIBLE;
        setVisibility(visible);
    }
}
```

### أخيرًا، لدينا الساحر في الدالة الرئيسية وهو يلقي التعويذات.


```java
public static void main(String[]args){
        var wizard=new Wizard();
        var goblin=new Goblin();

        // casts shrink/unshrink spell
        wizard.castSpell(goblin::changeSize);

        // casts visible/invisible spell
        wizard.castSpell(goblin::changeVisibility);

        // undo and redo casts
        wizard.undoLastSpell();
        wizard.redoLastSpell();
```

### هذا هو المثال قيد التنفيذ.


```java
var wizard=new Wizard();
        var goblin=new Goblin();

        goblin.printStatus();
        wizard.castSpell(goblin::changeSize);
        goblin.printStatus();

        wizard.castSpell(goblin::changeVisibility);
        goblin.printStatus();

        wizard.undoLastSpell();
        goblin.printStatus();

        wizard.undoLastSpell();
        goblin.printStatus();

        wizard.redoLastSpell();
        goblin.printStatus();

        wizard.redoLastSpell();
        goblin.printStatus();
```

### إليك مخرجات البرنامج:


```java
Goblin,[size=normal][visibility=visible]
        Goblin,[size=small][visibility=visible]
        Goblin,[size=small][visibility=invisible]
        Goblin,[size=small][visibility=visible]
        Goblin,[size=normal][visibility=visible]
        Goblin,[size=small][visibility=visible]
        Goblin,[size=small][visibility=invisible]
```

## تطبيق

استخدم نمط الأمر (Command) في الحالات التالية:

* لتحديد كائنات باستخدام إجراء لتنفيذه. يمكنك التعبير عن هذه التحديدات باستخدام لغة إجراء مع دالة رد اتصال، أي دالة يتم تسجيلها في مكان ما ليتم استدعاؤها في وقت لاحق. الأوامر هي بديل موجه للكائنات لردود الاتصال.
* لتحديد، وضع في طابور وتنفيذ الطلبات في أوقات مختلفة. يمكن أن يكون لكائن الأمر حياة مستقلة عن الطلب الأصلي. إذا كان يمكن تمثيل مستلم الطلب بطريقة مستقلة عن مساحة العناوين، فيمكنك نقل كائن الأمر للطلب إلى عملية مختلفة وتنفيذ الطلب هناك.
* دعم الإلغاء. يمكن لعملية تنفيذ الأمر تخزين الحالة لإلغاء تأثيراتها في نفس الأمر. يجب أن تحتوي واجهة الأمر على عملية إضافية لإلغاء التنفيذ التي تعيد تأثيرات استدعاء سابق لتنفيذ. يتم تخزين الأوامر التي تم تنفيذها في قائمة تاريخ. يمكن تحقيق وظيفة التراجع وإعادة التنفيذ بشكل غير محدود من خلال استعراض هذه القائمة للأمام والخلف عن طريق استدعاء إلغاء التنفيذ والتنفيذ، على التوالي.
* دعم تسجيل التغييرات بحيث يمكن تطبيقها مرة أخرى في حال حدوث عطل في النظام. من خلال إضافة عمليات تحميل وتخزين إلى واجهة الأوامر، يمكنك الاحتفاظ بسجل مستمر للتغييرات. يتطلب استعادة العطل إعادة تحميل الأوامر المسجلة من القرص وتنفيذها مرة أخرى باستخدام عملية التنفيذ.
* هيكلة النظام حول عمليات عالية المستوى مبنية على عمليات بدائية. هذه الهيكلة شائعة في أنظمة المعلومات التي تدعم المعاملات. المعاملة تحتوي على مجموعة من التغييرات في البيانات. يوفر نمط الأمر طريقة لنمذجة المعاملات. تحتوي الأوامر على واجهة مشتركة تسمح باستدعاء جميع المعاملات بنفس الطريقة. كما يسهل النمط توسيع النظام مع معاملات جديدة.
* الحفاظ على سجل من الطلبات.
* تنفيذ وظيفة رد الاتصال.
* تنفيذ وظيفة التراجع.

## الاستخدامات المعروفة

* الأزرار في واجهة المستخدم الرسومية وعناصر القائمة في تطبيقات سطح المكتب.
* العمليات في أنظمة قواعد البيانات والأنظمة المعاملاتية التي تدعم التراجع (rollback).
* تسجيل الماكرو في التطبيقات مثل محرري النصوص وجداول البيانات.
* [java.lang.Runnable](http://docs.oracle.com/javase/8/docs/api/java/lang/Runnable.html)
* [org.junit.runners.model.Statement](https://github.com/junit-team/junit4/blob/master/src/main/java/org/junit/runners/model/Statement.java)
* [Netflix Hystrix](https://github.com/Netflix/Hystrix/wiki)
* [javax.swing.Action](http://docs.oracle.com/javase/8/docs/api/javax/swing/Action.html)

## العواقب

المزايا:

* يفصل الكائن الذي يستدعي العملية عن الكائن الذي يعرف كيفية تنفيذها.
* من السهل إضافة أوامر جديدة، لأنه لا يتعين عليك تغيير الفئات الموجودة.
* يمكنك تجميع مجموعة من الأوامر في أمر مركب.

العيوب:

* يزيد عدد الفئات لكل أمر فردي.
* قد يعقد التصميم عند إضافة طبقات متعددة بين المرسلين والمستلمين.

## الأنماط ذات الصلة

* [Composite](https://java-design-patterns.com/patterns/composite/): يمكن تجميع الأوامر باستخدام نمط المركب لإنشاء أوامر كبيرة.
* [Memento](https://java-design-patterns.com/patterns/memento/): يمكن استخدامه لتنفيذ آليات التراجع.
* [Observer](https://java-design-patterns.com/patterns/observer/): يمكن ملاحظة النمط لتغييرات التي تفعّل الأوامر.

## المصادر

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=f27d2644fbe5026ea448791a8ad09c94)
* [Pattern-Oriented Software Architecture, Volume 1: A System of Patterns](https://amzn.to/3PFUqSY)
