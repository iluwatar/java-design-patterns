---
title: Observer (अवलोकक पैटर्न)
category: Behavioral (व्यावहारिक पैटर्न)
language: हिन्दी
tag:
 - Gang Of Four(गैंग ऑफ फोर) 
 - Reactive (रीएक्टिव)
---

## के रूप में भी जाना जाता है

डिपेन्डन्ट, पब्लिश-सब्सक्राइब

## उद्देश्य

ऑब्जेक्ट के बीच एक-से-बहुत डिपेंडेंसी को परिभाषित करना ताकि जब एक ऑब्जेक्ट की स्थिति में परिवर्तन होता है, तो उसके सभी डिपेंडेंट्स को स्वचालित रूप से सूचित किया और अपडेट किया जाए।

## स्पष्टीकरण

वास्तविक दुनिया मे उदाहरण

>एक दूर के देश में होबिट्स और ऑर्क्स की जातियां वास करती हैं। दोनों ही अधिकांशत: बाहर ही रहते हैं, इसलिए वे मौसम के परिवर्तनों का करीबी ध्यान रखते हैं। कहा जा सकता है कि वे मौसम को निरंतर देखते रहते हैं।

सामान्य शब्दों में:

>एक ऑब्जेक्ट के स्थिति में परिवर्तन को प्राप्त करने के लिए एक अवलोकक के रूप में पंजीकृत करना।

विकिपीडिया का कहना है:

>ऑब्जेक्ट पैटर्न एक सॉफ़्टवेयर डिज़ाइन पैटर्न है जिसमें एक ऑब्जेक्ट, जिसे सब्जेक्ट कहा जाता है, अपने डिपेंडेंट्स, जिन्हें अवलोकक कहा जाता है, की एक सूची बनाता है और उन्हें स्वचालित रूप से किसी भी स्थिति परिवर्तन की सूचना देता है, आमतौर पर उनके किसी भी मेथड को कॉल करके।

**कार्यात्मक उदाहरण**

चलो पहले WeatherObserver इंटरफेस और हमारे जातियां, Orcs और Hobbits को परिचय दें।

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

फिर यहाँ Weather है जो निरंतर बदल रहा है।

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

यहाँ पूर्ण उदाहरण है जो कार्रवाई में है।

```java
    var weather = new Weather();
    weather.addObserver(new Orcs());
    weather.addObserver(new Hobbits());
    weather.timePasses();
    weather.timePasses();
    weather.timePasses();
    weather.timePasses();
```

प्रोग्राम का आउटपुट:

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

## Class diagram

![alt text](./etc/observer.png "Observer")

## अनुप्रयोगिता

आप निम्नलिखित स्थितियों में अवलोकक पैटर्न का उपयोग कर सकते हैं:

1.  जब किसी व्यापन (abstraction) में दो पहलु होते हैं, जिनमें से एक दूसरे पर निर्भर होता है। इन पहलुओं को अलग-अलग ऑब्जेक्ट में संग्रहित करने से आप उन्हें अलग-अलग रूपों में बदल सकते हैं और स्वतंत्रता से पुन: उपयोग कर सकते हैं।
    
2.  जब किसी ऑब्जेक्ट में परिवर्तन करने से अन्य ऑब्जेक्ट को भी बदलने की आवश्यकता होती है, और आप नहीं जानते कि कितने ऑब्जेक्ट को बदलना होगा।
    
3.  जब किसी ऑब्जेक्ट को अन्य ऑब्जेक्ट को सूचित करने की क्षमता होनी चाहिए, और आप नहीं जानना चाहते कि ये ऑब्जेक्ट कौन होते हैं। अन्य शब्दों में, आप चाहते हैं कि ये ऑब्जेक्ट्स टाइटली कपल्ड (tightly coupled) न हों।

## ज्ञात उपयोग

* [java.util.Observer](http://docs.oracle.com/javase/8/docs/api/java/util/Observer.html)
* [java.util.EventListener](http://docs.oracle.com/javase/8/docs/api/java/util/EventListener.html)
* [javax.servlet.http.HttpSessionBindingListener](http://docs.oracle.com/javaee/7/api/javax/servlet/http/HttpSessionBindingListener.html)
* [RxJava](https://github.com/ReactiveX/RxJava)

## श्रेय

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Java Generics and Collections](https://www.amazon.com/gp/product/0596527756/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596527756&linkCode=as2&tag=javadesignpat-20&linkId=246e5e2c26fe1c3ada6a70b15afcb195)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)


