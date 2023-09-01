---
title: Aggregator Microservices
category: Architectural
language: hi
tag:
- Cloud distributed
- Decoupling
- Microservices
---

## हेतु

उपयोगकर्ता एग्रीगेटर सेवा पर एक कॉल करता है, और एग्रीगेटर फिर प्रत्येक प्रासंगिक माइक्रोसर्विस को कॉल करता है।

## व्याख्या

वास्तविक दुनिया का उदाहरण

> हमारे वेब बाज़ार को उत्पादों और उनकी वर्तमान सूची के बारे में जानकारी की आवश्यकता है। यह एक एग्रीगेटर को कॉल करता है
> सेवा जो बदले में उत्पाद जानकारी माइक्रोसर्विस और उत्पाद इन्वेंट्री माइक्रोसर्विस को कॉल करती है
> संयुक्त जानकारी.

साफ़ शब्दों में

> एग्रीगेटर माइक्रोसर्विस विभिन्न माइक्रोसर्विसेज से डेटा के टुकड़े एकत्र करता है और प्रसंस्करण के लिए एक समुच्चय लौटाता है। 

स्टैक ओवरफ्लो कहता है

> एग्रीगेटर माइक्रोसर्विस एप्लिकेशन द्वारा आवश्यक कार्यक्षमता प्राप्त करने के लिए कई सेवाओं को आमंत्रित करता है।

**प्रोग्रामेटिक उदाहरण**

आइए डेटा मॉडल से शुरू करें। यहाँ हमारा `Product` है।

```java
public class Product {
  private String title;
  private int productInventories;
  // getters and setters ->
  ...
}
```

आगे हम अपना `Aggregator` माइक्रोसर्विस पेश कर सकते हैं। इसमें क्लाइंट `ProductInformationClient` और शामिल हैं
संबंधित माइक्रोसर्विसेज़ को कॉल करने के लिए `ProductInventoryClient`।

```java
@RestController
public class Aggregator {

  @Resource
  private ProductInformationClient informationClient;

  @Resource
  private ProductInventoryClient inventoryClient;

  @RequestMapping(path = "/product", method = RequestMethod.GET)
  public Product getProduct() {

    var product = new Product();
    var productTitle = informationClient.getProductTitle();
    var productInventory = inventoryClient.getProductInventories();

    //Fallback to error message
    product.setTitle(requireNonNullElse(productTitle, "Error: Fetching Product Title Failed"));

    //Fallback to default error inventory
    product.setProductInventories(requireNonNullElse(productInventory, -1));

    return product;
  }
}
```

यहां सूचना माइक्रोसर्विस कार्यान्वयन का सार है। इन्वेंटरी माइक्रोसर्विस समान है, यह सिर्फ रिटर्न देता है
इन्वेंट्री मायने रखती है।

```java
@RestController
public class InformationController {
  @RequestMapping(value = "/information", method = RequestMethod.GET)
  public String getProductTitle() {
    return "The Product Title.";
  }
}
```

अब हमारे `Aggregator` REST API को कॉल करने से उत्पाद की जानकारी मिलती है।

```bash
curl http://localhost:50004/product
{"title":"The Product Title.","productInventories":5}
```

## क्लास डायग्राम

![alt text](../../../aggregator-microservices/aggregator-service/etc/aggregator-service.png "एग्रीगेटर माइक्रोसर्विस")

## प्रयोज्यता

जब आपको क्लाइंट डिवाइस की परवाह किए बिना विभिन्न माइक्रोसर्विसेज के लिए एकीकृत एपीआई की आवश्यकता हो तो एग्रीगेटर माइक्रोसर्विसेज पैटर्न का उपयोग करें।

## श्रेय

* [Microservice Design Patterns](http://web.archive.org/web/20190705163602/http://blog.arungupta.me/microservice-design-patterns/)
* [Microservices Patterns: With examples in Java](https://www.amazon.com/gp/product/1617294543/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1617294543&linkId=8b4e570267bc5fb8b8189917b461dc60)
* [Architectural Patterns: Uncover essential patterns in the most indispensable realm of enterprise architecture](https://www.amazon.com/gp/product/B077T7V8RC/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=B077T7V8RC&linkId=c34d204bfe1b277914b420189f09c1a4)
