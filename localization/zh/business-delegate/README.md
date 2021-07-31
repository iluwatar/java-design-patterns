---
layout: pattern
title: Business Delegate
folder: business-delegate
permalink: /patterns/business-delegate/
categories: Structural
tags:
 - Decoupling
---

## 含义

业务委托模式（译者：国内也有翻译成业务代表模式）在表现层和业务层之间增加了一个抽象层。通过使用该模式，我们获得了各层之间的松散耦合，并封装了关于如何定位、连接和与构成应用程序的业务对象进行交互的知识。

## 解释

真实世界的案例

> 一个手机应用程序承诺将现有的任何电影传输到你的手机上。它捕获了用户的搜索关键字内容，并将其传递给业务委托层。业务委托层选择最合适的视频流服务，并从该服务进行视频播放。

简而言之

> 业务委托模式在表现层和业务层之间增加了一个抽象层。

维基百科的解释

> Business delegate is a Java EE design pattern. This pattern is directing to reduce the coupling 
> in between business services and the connected presentation tier, and to hide the implementation 
> details of services (including lookup and accessibility of EJB architecture). Business delegates 
> acts as an adaptor to invoke business objects from the presentation tier.
>
> 业务委托模式是一种 Java EE 设计模式。这种模式旨在减少业务服务和所连接的表现层之间的耦合度，并隐藏服务的实现细节（包括 EJB 架构的查询和可访问性）。业务代表作为一个适配器，从表现层调用业务对象。

**编程示例**

首先，我们实现了一个视频流服务的抽象，和几个具体实现。

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

接下来，我们实现一个查询服务，用于决定使用哪个视频流服务。

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

业务委托层使用业务查询，将电影播放请求路由到合适的视频流服务。

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

移动客户端利用业务委托来调用业务层。

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

最后，我们展示一下这个示例完整的操作。

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

以下是终端输出的内容。

```
21:15:33.790 [main] INFO com.iluwatar.business.delegate.NetflixService - NetflixService is now processing
21:15:33.794 [main] INFO com.iluwatar.business.delegate.YouTubeService - YouTubeService is now processing
```

## 类图

![alt text](../../business-delegate/etc/business-delegate.urm.png "Business Delegate")

## 相关模式

* [Service locator pattern](https://java-design-patterns.com/patterns/service-locator/)

## 适用场景

业务委托模式的适用场景：

* 你希望表现层和业务层之间是松耦合的。
* 你想要协调对多个业务服务的调用。
* 你想要对服务查询、服务调用进行封装。

## 教程

* [Business Delegate Pattern at TutorialsPoint](https://www.tutorialspoint.com/design_pattern/business_delegate_pattern.htm)

## 引用

* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
* [Core J2EE Patterns: Best Practices and Design Strategies](https://www.amazon.com/gp/product/0130648841/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0130648841&linkId=a0100de2b28c71ede8db1757fb2b5947)
