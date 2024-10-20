---
title: Template Method
shortTitle: Template Method
category: Behavioral
language: ko
tag:
- Gang of Four
---

## 의도

동작 상의 알고리즘의 뼈대를 정의하고 일부 단계들을 하위 클래스에게 미룹니다. 템플릿 메소드는 알고리즘 구조의 변경없이 하위 클래스들이 알고리즘의 특정 단계들을 재정의할 수 있게 합니다.

## 설명

실제 예제

> 일반적으로 물건을 훔치는 절차는 동일합니다. 첫번째 단계로 대상을 고릅니다. 다음 단계로 대상을 어느정도 혼란스럽게 만듭니다. 마지막 단계로 물건을 훔칩니다. 하지만, 이 단계를 실행하는 구체적인 방법에는 여러가지가 있습니다.

쉽게 말하자면

> 템플릿 메소드 패턴은 일반적인 단계의 아웃라인을 부모 클래스에 제시합니다. 그리고 구체적인 자식 클래스의 구현으로 디테일한 사항들을 정의합니다.

Wikipedia에 의하면

> 객체지향 프로그래밍에서, 템플릿 메소드는 디자인 패턴 책에서 감마를 포함한 책의 저자들이 확인한 행동 디자인 패턴 중 하나입니다. 템플릿 메소드는 보통 추상 수퍼클래스인 수퍼클래스에서의 메소드이며 수많은 high-level 단계들의 관점에서 동작의 뼈대를 정의합니다. 이러한 절차들은 템플릿 메소드와 동일한 클래스의 추가적인 헬퍼 메소드들에 의해 구현됩니다.

**코드 예제**

먼저 구체적인 구현과 함께 템플릿 방법 클래스를 소개합니다. 하위클래스들이 템플릿 메소드를 재정의하지 않는 것을 확실히 하기위해 템플릿 메소드(예제에서는 메소드명`steal`)는 `final`로 선언해야 합니다. 그렇지 않으면 기본 클래스에 정의된 뼈대는 하위 클래스에서 재정의될 수 있습니다.


```java
@Slf4j
public abstract class StealingMethod {

  protected abstract String pickTarget();

  protected abstract void confuseTarget(String target);

  protected abstract void stealTheItem(String target);

  public final void steal() {
    var target = pickTarget();
    LOGGER.info("The target has been chosen as {}.", target);
    confuseTarget(target);
    stealTheItem(target);
  }
}

@Slf4j
public class SubtleMethod extends StealingMethod {

  @Override
  protected String pickTarget() {
    return "shop keeper";
  }

  @Override
  protected void confuseTarget(String target) {
    LOGGER.info("Approach the {} with tears running and hug him!", target);
  }

  @Override
  protected void stealTheItem(String target) {
    LOGGER.info("While in close contact grab the {}'s wallet.", target);
  }
}

@Slf4j
public class HitAndRunMethod extends StealingMethod {

  @Override
  protected String pickTarget() {
    return "old goblin woman";
  }

  @Override
  protected void confuseTarget(String target) {
    LOGGER.info("Approach the {} from behind.", target);
  }

  @Override
  protected void stealTheItem(String target) {
    LOGGER.info("Grab the handbag and run away fast!");
  }
}
```

템플릿 메소드를 담고 있는 반쪽짜리 도적 클래스입니다.

```java
public class HalflingThief {

  private StealingMethod method;

  public HalflingThief(StealingMethod method) {
    this.method = method;
  }

  public void steal() {
    method.steal();
  }

  public void changeMethod(StealingMethod method) {
    this.method = method;
  }
}
```

그리고 마지막으로, 반쪽짜리 도둑이 다른 도둑질 방법을 어떻게 활용하는지 보여줍니다.

```java
    var thief = new HalflingThief(new HitAndRunMethod());
    thief.steal();
    thief.changeMethod(new SubtleMethod());
    thief.steal();
```

## 클래스 다이어그램

![alt text](./etc/template_method_urm.png "Template Method")

## 적용 가능성

템플릿 메소드는 사용되어야합니다.

* 알고리즘의 변하지않는 부분을 한번만 구현하고, 다양할 수 있는 행위의 구현을 서브클래스에게 맡기기 위해
* 중복되는 코드를 피하기위해 서브클래스들 사이의 공통적인 행위를 공통 클래스에서 고려하고 현지화해야 하는 경우. 이것은 Opdyke 와 Johonson이 묘사한 "일반화하기 위한 수정"의 좋은 예입니다. 먼저 기존 코드들 간의 차이를 확인한 다음 새로운 동작으로 구분합니다. 마지막으로, 다른 코드들을  새로운 동작중 하나를 호출하는 템플릿 메소드로 대체합니다.
* 서브클래스들의 확장을 컨트롤하기 위해. 특정 지점에서 "훅" 작업을 호출하여 해당 지점에서만 확장을 허용하는 템플릿 메서드를 정의할 수 있습니다.

## 튜토리얼

* [Template-method Pattern Tutorial](https://www.journaldev.com/1763/template-method-design-pattern-in-java)

## 실제 사례

* [javax.servlet.GenericServlet.init](https://jakarta.ee/specifications/servlet/4.0/apidocs/javax/servlet/GenericServlet.html#init--):
  Method `GenericServlet.init(ServletConfig config)` calls the parameterless method `GenericServlet.init()` which is intended to be overridden in subclasses.
  Method `GenericServlet.init(ServletConfig config)` is the template method in this example.

## 크레딧

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
