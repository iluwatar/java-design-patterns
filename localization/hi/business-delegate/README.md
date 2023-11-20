---
title: Business Delegate
category: Structural
language: hi
tag:
 - Decoupling
---

## हेतु

बिजनेस डेलीगेट पैटर्न बीच में एक अमूर्त परत जोड़ता है
प्रस्तुतिकरण और व्यावसायिक स्तर। पैटर्न का उपयोग करके हम ढीला युग्मन प्राप्त करते हैं
स्तरों के बीच और पता लगाने, कनेक्ट करने के तरीके के बारे में ज्ञान को समाहित करें,
और एप्लिकेशन बनाने वाली व्यावसायिक वस्तुओं के साथ इंटरैक्ट करें।

## व्याख्या

वास्तविक दुनिया का उदाहरण

> एक मोबाइल फोन एप्लिकेशन आपके फोन पर मौजूदा किसी भी फिल्म को स्ट्रीम करने का वादा करता है। यह पकड़ लेता है
> उपयोगकर्ता की खोज स्ट्रिंग और इसे व्यवसाय प्रतिनिधि को भेजता है। व्यापार प्रतिनिधि
> सबसे उपयुक्त वीडियो स्ट्रीमिंग सेवा का चयन करता है और वहां से वीडियो चलाता है।

सादे शब्दों में

> व्यावसायिक प्रतिनिधि प्रस्तुतिकरण और व्यावसायिक स्तरों के बीच एक अमूर्त परत जोड़ता है।

विकिपीडिया कहता है

> बिजनेस डेलिगेट एक जावा ईई डिज़ाइन पैटर्न है। यह पैटर्न युग्मन को कम करने का निर्देश दे रहा है
> व्यावसायिक सेवाओं और कनेक्टेड प्रेजेंटेशन स्तर के बीच, और कार्यान्वयन को छिपाने के लिए
> सेवाओं का विवरण (ईजेबी आर्किटेक्चर की खोज और पहुंच सहित)। व्यापार प्रतिनिधि
> प्रेजेंटेशन स्तर से व्यावसायिक वस्तुओं को आमंत्रित करने के लिए एक एडाप्टर के रूप में कार्य करता है।

**प्रोग्रामेटिक उदाहरण**

सबसे पहले, हमारे पास वीडियो स्ट्रीमिंग सेवाओं और कुछ कार्यान्वयन के लिए एक सार है।

```java
public interface VideoStreamingService {
  void doProcessing();
}

@Slf4j
public class NetflixService implements VideoStreamingService {
  @Override
  public void doProcessing() {
    LOGGER.info("NetflixService is now processing");
  }
}

@Slf4j
public class YouTubeService implements VideoStreamingService {
  @Override
  public void doProcessing() {
    LOGGER.info("YouTubeService is now processing");
  }
}
```

फिर हमारे पास एक लुकअप सेवा है जो यह तय करती है कि कौन सी वीडियो स्ट्रीमिंग सेवा का उपयोग किया जाए।

```java
@Setter
public class BusinessLookup {

  private NetflixService netflixService;
  private YouTubeService youTubeService;

  public VideoStreamingService getBusinessService(String movie) {
    if (movie.toLowerCase(Locale.ROOT).contains("die hard")) {
      return netflixService;
    } else {
      return youTubeService;
    }
  }
}
```

व्यवसाय प्रतिनिधि मूवी प्लेबैक अनुरोधों को उपयुक्त स्थान पर रूट करने के लिए व्यवसाय लुकअप का उपयोग करता है
वीडियो स्ट्रीमिंग सेवा.

```java
@Setter
public class BusinessDelegate {

  private BusinessLookup lookupService;

  public void playbackMovie(String movie) {
    VideoStreamingService videoStreamingService = lookupService.getBusinessService(movie);
    videoStreamingService.doProcessing();
  }
}
```

मोबाइल क्लाइंट व्यवसाय स्तर पर कॉल करने के लिए व्यवसाय प्रतिनिधि का उपयोग करता है।

```java
public class MobileClient {

  private final BusinessDelegate businessDelegate;

  public MobileClient(BusinessDelegate businessDelegate) {
    this.businessDelegate = businessDelegate;
  }

  public void playbackMovie(String movie) {
    businessDelegate.playbackMovie(movie);
  }
}
```

अंत में, हम कार्रवाई में पूरा उदाहरण दिखा सकते हैं।

```java
  public static void main(String[] args) {

    // prepare the objects
    var businessDelegate = new BusinessDelegate();
    var businessLookup = new BusinessLookup();
    businessLookup.setNetflixService(new NetflixService());
    businessLookup.setYouTubeService(new YouTubeService());
    businessDelegate.setLookupService(businessLookup);

    // create the client and use the business delegate
    var client = new MobileClient(businessDelegate);
    client.playbackMovie("Die Hard 2");
    client.playbackMovie("Maradona: The Greatest Ever");
  }
```

यहाँ कंसोल आउटपुट है.

```
21:15:33.790 [main] INFO com.iluwatar.business.delegate.NetflixService - NetflixService is now processing
21:15:33.794 [main] INFO com.iluwatar.business.delegate.YouTubeService - YouTubeService is now processing
```

## क्लास डायग्राम

![alt text](../../../business-delegate/etc/business-delegate.urm.png "Business Delegate")

## संबंधित पैटर्न

* [Service locator pattern](https://java-design-patterns.com/patterns/service-locator/)

## प्रयोज्यता

बिजनेस डेलीगेट पैटर्न का उपयोग कब करें

* आप प्रस्तुतिकरण और व्यावसायिक स्तरों के बीच ढीला युग्मन चाहते हैं
* आप एकाधिक व्यावसायिक सेवाओं के लिए कॉल व्यवस्थित करना चाहते हैं
* आप सेवा लुकअप और सेवा कॉल को समाहित करना चाहते हैं

## ट्यूटोरियल

* [Business Delegate Pattern at TutorialsPoint](https://www.tutorialspoint.com/design_pattern/business_delegate_pattern.htm)

## Credits

* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
* [Core J2EE Patterns: Best Practices and Design Strategies](https://www.amazon.com/gp/product/0130648841/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0130648841&linkId=a0100de2b28c71ede8db1757fb2b5947)
