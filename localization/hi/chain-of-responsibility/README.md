---
title: Chain of responsibility
category: Behavioral
language: hi
tag:
 - Gang of Four
---

## हेतु

एक से अधिक ऑब्जेक्ट को मौका देकर अनुरोध भेजने वाले को उसके प्राप्तकर्ता से जोड़ने से बचें
अनुरोध संभालें. प्राप्त वस्तुओं को श्रृंखलाबद्ध करें और किसी वस्तु तक श्रृंखला के साथ अनुरोध को पास करें
इसे संभालता है.

## व्याख्या

वास्तविक दुनिया का उदाहरण

> ऑर्क किंग अपनी सेना को ऊंचे स्वर में आदेश देता है। फिर, प्रतिक्रिया देने वाला सबसे करीबी व्यक्ति कमांडर होता है
> एक अधिकारी, और फिर एक सैनिक। कमांडर, अधिकारी और सैनिक जिम्मेदारी की एक श्रृंखला बनाते हैं।

साफ़ शब्दों में

> यह वस्तुओं की श्रृंखला बनाने में मदद करता है। एक अनुरोध एक छोर से प्रवेश करता है और एक वस्तु से जाता रहता है
> दूसरे को तब तक भेजें जब तक उसे कोई उपयुक्त हैंडलर न मिल जाए।

विकिपीडिया कहता है

> ऑब्जेक्ट-ओरिएंटेड डिज़ाइन में, श्रृंखला-की-जिम्मेदारी पैटर्न एक डिज़ाइन पैटर्न है जिसमें शामिल है
> कमांड ऑब्जेक्ट का एक स्रोत और प्रोसेसिंग ऑब्जेक्ट की एक श्रृंखला। प्रत्येक प्रोसेसिंग ऑब्जेक्ट में शामिल है
> तर्क जो कमांड ऑब्जेक्ट के प्रकार को परिभाषित करता है जिसे वह संभाल सकता है; बाकी को पास कर दिया गया है
> श्रृंखला में अगली प्रसंस्करण वस्तु।

**प्रोग्रामेटिक उदाहरण**

ऊपर से ओर्क्स के साथ हमारे उदाहरण का अनुवाद करना। सबसे पहले, हमारे पास `Request` वर्ग है:

```java
public class Request {

  private final RequestType requestType;
  private final String requestDescription;
  private boolean handled;

  public Request(final RequestType requestType, final String requestDescription) {
    this.requestType = Objects.requireNonNull(requestType);
    this.requestDescription = Objects.requireNonNull(requestDescription);
  }

  public String getRequestDescription() { return requestDescription; }

  public RequestType getRequestType() { return requestType; }

  public void markHandled() { this.handled = true; }

  public boolean isHandled() { return this.handled; }

  @Override
  public String toString() { return getRequestDescription(); }
}

public enum RequestType {
  DEFEND_CASTLE, TORTURE_PRISONER, COLLECT_TAX
}
```

इसके बाद, हम अनुरोध हैंडलर पदानुक्रम दिखाते हैं।

```java
public interface RequestHandler {

    boolean canHandleRequest(Request req);

    int getPriority();

    void handle(Request req);

    String name();
}

@Slf4j
public class OrcCommander implements RequestHandler {
    @Override
    public boolean canHandleRequest(Request req) {
        return req.getRequestType() == RequestType.DEFEND_CASTLE;
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public void handle(Request req) {
        req.markHandled();
        LOGGER.info("{} handling request \"{}\"", name(), req);
    }

    @Override
    public String name() {
        return "Orc commander";
    }
}

// OrcOfficer and OrcSoldier are defined similarly as OrcCommander

```

ऑर्क किंग आदेश देता है और श्रृंखला बनाता है।

```java
public class OrcKing {

  private List<RequestHandler> handlers;

  public OrcKing() {
    buildChain();
  }

  private void buildChain() {
    handlers = Arrays.asList(new OrcCommander(), new OrcOfficer(), new OrcSoldier());
  }

  public void makeRequest(Request req) {
    handlers
        .stream()
        .sorted(Comparator.comparing(RequestHandler::getPriority))
        .filter(handler -> handler.canHandleRequest(req))
        .findFirst()
        .ifPresent(handler -> handler.handle(req));
  }
}
```

कार्रवाई में जिम्मेदारी की श्रृंखला.

```java
var king = new OrcKing();
king.makeRequest(new Request(RequestType.DEFEND_CASTLE, "defend castle"));
king.makeRequest(new Request(RequestType.TORTURE_PRISONER, "torture prisoner"));
king.makeRequest(new Request(RequestType.COLLECT_TAX, "collect tax"));
```

कंसोल आउटपुट.

```
Orc commander handling request "defend castle"
Orc officer handling request "torture prisoner"
Orc soldier handling request "collect tax"
```

## क्लास डायग्राम

![alt text](../../../chain-of-responsibility/etc/chain-of-responsibility.urm.png "Chain of Responsibility class diagram")

## प्रयोज्यता

जिम्मेदारी की श्रृंखला का प्रयोग कब करें

* एक से अधिक ऑब्जेक्ट एक अनुरोध को संभाल सकते हैं, और हैंडलर को पहले से ज्ञात नहीं है। हैंडलर का स्वचालित रूप से पता लगाया जाना चाहिए।
* आप रिसीवर को स्पष्ट रूप से निर्दिष्ट किए बिना कई ऑब्जेक्ट में से एक के लिए अनुरोध जारी करना चाहते हैं।
* ऑब्जेक्ट का सेट जो अनुरोध को संभाल सकता है उसे गतिशील रूप से निर्दिष्ट किया जाना चाहिए।

## ज्ञात उपयोग

* [java.util.logging.Logger#log()](http://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html#log%28java.util.logging.Level,%20java.lang.String%29)
* [Apache Commons Chain](https://commons.apache.org/proper/commons-chain/index.html)
* [javax.servlet.Filter#doFilter()](http://docs.oracle.com/javaee/7/api/javax/servlet/Filter.html#doFilter-javax.servlet.ServletRequest-javax.servlet.ServletResponse-javax.servlet.FilterChain-)

## श्रेय

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
