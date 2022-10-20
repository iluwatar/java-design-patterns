---
layout: pattern
title: Observer
folder: observer
permalink: /patterns/observer/hi
categories: Behavioral
language: hi
tags:
- Gang Of Four
- Reactive
---

## इस रूप में भी जाना जाता है

* Dependents 
* Publish-Subscribe

## इरादा

वस्तुओं के बीच एक-से-कई निर्भरता को परिभाषित करें ताकि जब एक वस्तु स्थिति बदलती है, तो उसकी सभी आश्रितों को स्वचालित रूप से अधिसूचित और अद्यतन किया जाता है।

## व्याख्या

वास्तविक दुनिया का उदाहरण

> दूर देश में हॉबिट्स और ऑर्क्स जाति हैं। ये दोनों ज्यादातर बाहर रहते हैं इसलिए वे मौसम परिवर्तन का बारीकी से पालन करें। कहा जा सकता है की वे लगातार मौसम देख रहे हैं।

सीधे शब्दों में

> वस्तु में राज्य परिवर्तन प्राप्त करने के लिए एक पर्यवेक्षक के रूप में पंजीकरण करें।

विकिपीडिया कहता है

> ऑब्जर्वर पैटर्न एक सॉफ्टवेयर डिजाइन पैटर्न है जिसमें एक वस्तु, जिसे विषय कहा जाता है,
> अपने आश्रितों की एक सूची रखता है, जिन्हें पर्यवेक्षक कहा जाता है, और उन्हें किसी भी राज्य के बारे में स्वचालित रूप से सूचित करता है
> परिवर्तन, आमतौर पर उनके तरीकों में से एक को कॉल करके।

**प्रोग्रामेटिक उदाहरण**

आइए पहले `WeatherObserver` इंटरफ़ेस, `Orcs` और `Hobbits` का परिचय दें।

```java
public interface WeatherObserver {

  void update(WeatherType currentWeather);
}

@Slf4j
public class Orcs implements WeatherObserver {

  @Override
  public void update(WeatherType currentWeather) {
    LOGGER.info("The orcs are facing " + currentWeather.getDescription() + " weather now");
  }
}

@Slf4j
public class Hobbits implements WeatherObserver {

  @Override
  public void update(WeatherType currentWeather) {
    switch (currentWeather) {
      LOGGER.info("The hobbits are facing " + currentWeather.getDescription() + " weather now");
    }
  }
}
```

फिर यहाँ `Weather` है जो लगातार बदल रहा है।

```java
@Slf4j
public class Weather {

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

कार्रवाई में पूरा उदाहरण यहां दिया गया है:

```java
    var weather = new Weather();
    weather.addObserver(new Orcs());
    weather.addObserver(new Hobbits());
    weather.timePasses();
    weather.timePasses();
    weather.timePasses();
    weather.timePasses();
```

प्रोग्राम आउटपुट:

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

## वर्ग आरेख

![alt text](../../../observer/etc/observer.png "Observer")

## प्रयोज्यता

निम्नलिखित में से किसी भी स्थिति में प्रेक्षक पैटर्न का प्रयोग करें:

* जब एक अमूर्त के दो पहलू होते हैं, एक दूसरे पर निर्भर। इन पहलुओं को इनकैप्सुलेट करना
  अलग-अलग वस्तुएं आपको अलग-अलग करने देती हैं और स्वतंत्र रूप से उनका पुन: उपयोग करती हैं।
* जब एक वस्तु में परिवर्तन के लिए दूसरों को बदलने की आवश्यकता होती है, और आप नहीं जानते कि कितनी वस्तुओं को बदलने की आवश्यकता है
  बदला गया।
* जब कोई वस्तु अन्य वस्तुओं को सूचित करने में सक्षम होनी चाहिए, बिना यह अनुमान लगाए कि ये कौन हैं
  वस्तुएं हैं। दूसरे शब्दों में, आप इन वस्तुओं को कसकर युग्मित नहीं करना चाहते हैं।

## ज्ञात उपयोग

* [java.util.Observer](http://docs.oracle.com/javase/8/docs/api/java/util/Observer.html)
* [java.util.EventListener](http://docs.oracle.com/javase/8/docs/api/java/util/EventListener.html)
* [javax.servlet.http.HttpSessionBindingListener](http://docs.oracle.com/javaee/7/api/javax/servlet/http/HttpSessionBindingListener.html)
* [RxJava](https://github.com/ReactiveX/RxJava)

## क्रेडिट

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Java Generics and Collections](https://www.amazon.com/gp/product/0596527756/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596527756&linkCode=as2&tag=javadesignpat-20&linkId=246e5e2c26fe1c3ada6a70b15afcb195)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
