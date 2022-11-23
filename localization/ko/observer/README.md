---
title: Observer
category: Behavioral
language: ko
tag:
- Gang Of Four
- Reactive
---

## 또한 ~으로 알려진

Dependents, Publish-Subscribe

## 의도

하나의 개체가 상태를 변경하면 모든 종속 항목에 알림이 전송되고 자동으로 업데이트 되도록 개체간의 일대 다 종속성을 정의합니다.

## 설명

예시

> 멀리 떨어진 땅에는 호빗과 오크 종족이 살고 있습니다. 둘 다 대부분 야외에 있으므로 날씨 변화를 면밀히 따릅니다. 끊임없이 날씨를 관찰하고 있다고 말할 수 있습니다.

평범하게 말하자면

> observer로 등록하여 개체의 상태 변경을 수신합니다.

Wikipedia 말에 의하면

> observer 패턴은 주제라고하는 객체가 observer라고 하는 종속 항목 목록을 유지하고 일반적으로 메서드 중 하나를 호출하여 상태 변경을 자동으로 알리는 소프트웨어 디자인 패턴입니다.

**프로그램 코드 예제**

먼저 `WeatherObserver` 인터페이스와 우리의 종족 `Orcs` 와 `Hobbits` 소개하겠습니다.

```java
public interface WeatherObserver {

  void update(WeatherType currentWeather);
}

public class Orcs implements WeatherObserver {

  private static final Logger LOGGER = LoggerFactory.getLogger(Orcs.class);

  @Override
  public void update(WeatherType currentWeather) {
    LOGGER.info("The orcs are facing " + currentWeather.getDescription() + " weather now");
  }
}

public class Hobbits implements WeatherObserver {

  private static final Logger LOGGER = LoggerFactory.getLogger(Hobbits.class);

  @Override
  public void update(WeatherType currentWeather) {
    switch (currentWeather) {
      LOGGER.info("The hobbits are facing " + currentWeather.getDescription() + " weather now");
    }
  }
}
```

그리고 끊임없이 변화하는 `Weather` 가 있습니다.

```java
public class Weather {

  private static final Logger LOGGER = LoggerFactory.getLogger(Weather.class);

  private WeatherType currentWeather;
  private final List<WeatherObserver> observers;

  public Weather() {
    observers = new ArrayList<>();
    currentWeather = WeatherType.SUNNY;
  }

  public void addObserver(WeatherObserver obs) {
    observers.add(obs);
  }

  public void removeObserver(WeatherObserver obs) {
    observers.remove(obs);
  }

  /**
   * Makes time pass for weather.
   */
  public void timePasses() {
    var enumValues = WeatherType.values();
    currentWeather = enumValues[(currentWeather.ordinal() + 1) % enumValues.length];
    LOGGER.info("The weather changed to {}.", currentWeather);
    notifyObservers();
  }

  private void notifyObservers() {
    for (var obs : observers) {
      obs.update(currentWeather);
    }
  }
}
```

여기에 전체 예제가 있습니다.

```java
    var weather = new Weather();
    weather.addObserver(new Orcs());
    weather.addObserver(new Hobbits());
    weather.timePasses();
    weather.timePasses();
    weather.timePasses();
    weather.timePasses();
```

프로그램 출력 :

```
The weather changed to rainy.
The orcs are facing rainy weather now
The hobbits are facing rainy weather now
The weather changed to windy.
The orcs are facing windy weather now
The hobbits are facing windy weather now
The weather changed to cold.
The orcs are facing cold weather now
The hobbits are facing cold weather now
The weather changed to sunny.
The orcs are facing sunny weather now
The hobbits are facing sunny weather now
```

## 클래스 다이어그램

![alt text](./etc/observer.png)

## 적용 가능성

다음 상황에서 관찰자 패턴을 사용하십시오.

- 추상화에 두 가지 측면이 있을 때 하나는 다른 하나에 종속됩니다. 이러한 측면을 별도의 개체에 캡슐화하면 독립적으로 변경하고 재사용 할 수 있습니다.
- 한 개체를 변경하려면 얼마나 다른 개체를 변경해야 하는지 알 수 없는 경우입니다.
- 개체가 다른 개체가 누구인지 가정하지 않고 알릴 수 있어야하는 경우. 즉, 개체가 단단히 결합되는 것을 원하지 않습니다.

## 일반적인 사용 사례

- 한 개체를 변경하면 다른 개체도 변경됩니다.

## 실제 사례

- [java.util.Observer](http://docs.oracle.com/javase/8/docs/api/java/util/Observer.html)
- [java.util.EventListener](http://docs.oracle.com/javase/8/docs/api/java/util/EventListener.html)
- [javax.servlet.http.HttpSessionBindingListener](http://docs.oracle.com/javaee/7/api/javax/servlet/http/HttpSessionBindingListener.html)
- [RxJava](https://github.com/ReactiveX/RxJava)

## 크레딧

- [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
- [Java Generics and Collections](https://www.amazon.com/gp/product/0596527756/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596527756&linkCode=as2&tag=javadesignpat-20&linkId=246e5e2c26fe1c3ada6a70b15afcb195)
- [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
- [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
