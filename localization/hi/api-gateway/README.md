---
title: API Gateway
category: Architectural
language: hi
tag:
  - Cloud distributed
  - Decoupling
  - Microservices
---

## हेतु

एक ही स्थान, एपीआई गेटवे पर माइक्रोसर्विसेज के लिए एकत्रित कॉल। उपयोगकर्ता एक ही कॉल करता है
एपीआई गेटवे पर, और एपीआई गेटवे फिर प्रत्येक प्रासंगिक माइक्रोसर्विस को कॉल करता है।

## व्याख्या

माइक्रोसर्विसेज पैटर्न के साथ, एक क्लाइंट को कई अलग-अलग माइक्रोसर्विसेज से डेटा की आवश्यकता हो सकती है। यदि
क्लाइंट ने प्रत्येक माइक्रोसर्विस को सीधे कॉल किया, जो लंबे समय तक लोड करने में योगदान दे सकता है
क्लाइंट को कॉल की गई प्रत्येक माइक्रोसर्विस के लिए नेटवर्क अनुरोध करना होगा। इसके अलावा, होने
क्लाइंट कॉल प्रत्येक माइक्रोसर्विस क्लाइंट को सीधे उस माइक्रोसर्विस से जोड़ती है - यदि आंतरिक है
माइक्रोसर्विसेज का कार्यान्वयन बदल जाता है (उदाहरण के लिए, यदि दो माइक्रोसर्विसेज को कभी-कभी संयोजित किया जाता है
भविष्य में) या यदि किसी माइक्रोसर्विस का स्थान (होस्ट और पोर्ट) बदलता है, तो प्रत्येक ग्राहक
उन माइक्रोसर्विसेज का उपयोग अद्यतन किया जाना चाहिए।

एपीआई गेटवे पैटर्न का इरादा इनमें से कुछ मुद्दों को कम करना है। एपीआई गेटवे में
पैटर्न, एक अतिरिक्त इकाई (एपीआई गेटवे) क्लाइंट और माइक्रोसर्विसेज के बीच रखी गई है।
एपीआई गेटवे का काम माइक्रोसर्विसेज पर कॉल को एकत्रित करना है। ग्राहक के बजाय
प्रत्येक माइक्रोसर्विस को व्यक्तिगत रूप से कॉल करने पर, क्लाइंट एपीआई गेटवे को एक बार कॉल करता है। एपीआई
गेटवे फिर प्रत्येक माइक्रोसर्विसेज को कॉल करता है जिनकी ग्राहक को आवश्यकता होती है।

वास्तविक दुनिया का उदाहरण

> हम एक ई-कॉमर्स साइट के लिए माइक्रोसर्विसेज और एपीआई गेटवे पैटर्न लागू कर रहे हैं। इस व्यवस्था में
> एपीआई गेटवे इमेज और प्राइस माइक्रोसर्विसेज को कॉल करता है।

साफ़ शब्दों में

> माइक्रोसर्विसेज आर्किटेक्चर का उपयोग करके कार्यान्वित सिस्टम के लिए, एपीआई गेटवे एकल प्रवेश बिंदु है
> जो व्यक्तिगत माइक्रोसर्विसेज के लिए कॉलों को एकत्रित करता है।

विकिपीडिया कहता है

> एपीआई गेटवे एक सर्वर है जो एपीआई फ्रंट-एंड के रूप में कार्य करता है, एपीआई अनुरोध प्राप्त करता है, थ्रॉटलिंग लागू करता है
> और सुरक्षा नीतियां, अनुरोधों को बैक-एंड सेवा तक भेजती हैं और फिर प्रतिक्रिया को वापस भेजती हैं
> अनुरोधकर्ता को. एक गेटवे में अक्सर व्यवस्थित करने और संशोधित करने के लिए एक परिवर्तन इंजन शामिल होता है
> तुरंत अनुरोध और प्रतिक्रियाएँ। एक गेटवे संग्रहण जैसी कार्यक्षमता भी प्रदान कर सकता है
> एनालिटिक्स डेटा और कैशिंग प्रदान करना। गेटवे समर्थन के लिए कार्यक्षमता प्रदान कर सकता है
> प्रमाणीकरण, प्राधिकरण, सुरक्षा, ऑडिट और नियामक अनुपालन।

**प्रोग्रामेटिक उदाहरण**

यह कार्यान्वयन दिखाता है कि ई-कॉमर्स साइट के लिए एपीआई गेटवे पैटर्न कैसा दिख सकता है।
`ApiGateway` `ImageClientImpl` का उपयोग करके इमेज और प्राइस माइक्रोसर्विसेज को कॉल करता है और
क्रमशः `PriceClientImpl`। डेस्कटॉप डिवाइस पर साइट देखने वाले ग्राहक दोनों कीमतें देख सकते हैं
जानकारी और उत्पाद की एक छवि, इसलिए `ApiGateway` दोनों माइक्रोसर्विसेज को कॉल करता है
डेटा को `DesktopProduct` मॉडल में एकत्रित करता है। हालाँकि, मोबाइल उपयोगकर्ता केवल कीमत की जानकारी देखते हैं;
उन्हें उत्पाद की छवि नहीं दिखती. मोबाइल उपयोगकर्ताओं के लिए, `ApiGateway` केवल मूल्य प्राप्त करता है
जानकारी, जिसका उपयोग यह `MobileProduct` को पॉप्युलेट करने के लिए करता है।

यहां इमेज माइक्रोसर्विस कार्यान्वयन है।

```java
public interface ImageClient {
  String getImagePath();
}

public class ImageClientImpl implements ImageClient {
  @Override
  public String getImagePath() {
    var httpClient = HttpClient.newHttpClient();
    var httpGet = HttpRequest.newBuilder()
        .GET()
        .uri(URI.create("http://localhost:50005/image-path"))
        .build();

    try {
      var httpResponse = httpClient.send(httpGet, BodyHandlers.ofString());
      return httpResponse.body();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }

    return null;
  }
}
```

यहां प्राइस माइक्रोसर्विस कार्यान्वयन है।

```java
public interface PriceClient {
  String getPrice();
}

public class PriceClientImpl implements PriceClient {

  @Override
  public String getPrice() {
    var httpClient = HttpClient.newHttpClient();
    var httpGet = HttpRequest.newBuilder()
        .GET()
        .uri(URI.create("http://localhost:50006/price"))
        .build();

    try {
      var httpResponse = httpClient.send(httpGet, BodyHandlers.ofString());
      return httpResponse.body();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }

    return null;
  }
}
```

यहां हम देख सकते हैं कि एपीआई गेटवे माइक्रोसर्विसेज के अनुरोधों को कैसे मैप करता है।

```java
public class ApiGateway {

  @Resource
  private ImageClient imageClient;

  @Resource
  private PriceClient priceClient;

  @RequestMapping(path = "/desktop", method = RequestMethod.GET)
  public DesktopProduct getProductDesktop() {
    var desktopProduct = new DesktopProduct();
    desktopProduct.setImagePath(imageClient.getImagePath());
    desktopProduct.setPrice(priceClient.getPrice());
    return desktopProduct;
  }

  @RequestMapping(path = "/mobile", method = RequestMethod.GET)
  public MobileProduct getProductMobile() {
    var mobileProduct = new MobileProduct();
    mobileProduct.setPrice(priceClient.getPrice());
    return mobileProduct;
  }
}
```

## क्लास डायग्राम
![alt text](../../../api-gateway/etc/api-gateway.png "API Gateway")

## प्रयोज्यता

 एपीआई गेटवे पैटर्न का उपयोग करें जब

* आप माइक्रोसर्विसेज आर्किटेक्चर का उपयोग कर रहे हैं और आपको अपने माइक्रोसर्विसेज कॉल के लिए एकत्रीकरण के एक बिंदु की आवश्यकता है।

## ट्यूटोरियल

* [Exploring the New Spring Cloud Gateway](https://www.baeldung.com/spring-cloud-gateway)
* [Spring Cloud - Gateway](https://www.tutorialspoint.com/spring_cloud/spring_cloud_gateway.htm)
* [Getting Started With Spring Cloud Gateway](https://dzone.com/articles/getting-started-with-spring-cloud-gateway)

## श्रेय

* [microservices.io - API Gateway](http://microservices.io/patterns/apigateway.html)
* [NGINX - Building Microservices: Using an API Gateway](https://www.nginx.com/blog/building-microservices-using-an-api-gateway/)
* [Microservices Patterns: With examples in Java](https://www.amazon.com/gp/product/1617294543/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1617294543&linkId=ac7b6a57f866ac006a309d9086e8cfbd)
* [Building Microservices: Designing Fine-Grained Systems](https://www.amazon.com/gp/product/1491950358/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1491950358&linkId=4c95ca9831e05e3f0dadb08841d77bf1)
