---
title: API Gateway
shortTitle: API Gateway
category: Architectural
language: zh
tag:
  - Cloud distributed
  - Decoupling
  - Microservices
---

## 目的

API网关将所有对微服务的调用聚合到一起。用户对API网关进行一次调用，然后API网关调用每个相关的微服务。

## 解释

使用微服务模式，客户端可能需要来自多个不同微服务的数据。 如果客户端直接调用每个微服务，则可能会导致更长的加载时间，因为客户端将不得不为每个调用的微服务发出网络请求。此外，让客户端调用每个微服务会直接将客户端与该微服务相关联-如果微服务的内部实现发生了变化（例如，如果将来某个时候合并了两个微服务），或者微服务的位置（主机和端口） 更改，则必须更新使用这些微服务的每个客户端。

API网关模式的目的是缓解其中的一些问题。 在API网关模式中，在客户端和微服务之间放置了一个附加实体（API网关）。API网关的工作是将对微服务的调用进行聚合。 客户端不是一次单独调用每个微服务，而是一次调用API网关。 然后，API网关调用客户端所需的每个微服务。

真实世界例子

> 我们正在为电子商务站点实现微服务和API网关模式。 在此系统中，API网关调用Image和Price微服务。

通俗地说

> 对于使用微服务架构实现的系统，API是聚合微服务调用的入口点。 

维基百科说

> API网关是充当API前置，接收API请求，执行限制和安全策略，将请求传递到后端服务，然后将响应传递回请求者的服务器。网关通常包括一个转换引擎，以实时地编排和修改请求和响应。 网关可以提供收集分析数据和提供缓存等功能。网关还可以提供支持身份验证，授权，安全性，审计和法规遵从性的功能。

**程序示例**

此实现展示了电子商务站点的API网关模式。` ApiGateway`分别使用` ImageClientImpl`和` PriceClientImpl`来调用Image和Price微服务。 在桌面设备上查看该网站的客户可以看到价格信息和产品图片，因此` ApiGateway`会调用这两种微服务并在`DesktopProduct`模型中汇总数据。 但是，移动用户只能看到价格信息。 他们看不到产品图片。 对于移动用户，`ApiGateway`仅检索价格信息，并将其用于填充`MobileProduct`模型。

这个是图像微服务的实现。

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

这里是价格服务的实现。

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

在这里，我们可以看到API网关如何将请求映射到微服务。

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

## 类图
![alt text](./etc/api-gateway.png "API Gateway")

## 适用性

在以下情况下使用API网关模式

* 你正在使用微服务架构，并且需要聚合单点来进行微服务调用。

## 鸣谢

* [microservices.io - API Gateway](http://microservices.io/patterns/apigateway.html)
* [NGINX - Building Microservices: Using an API Gateway](https://www.nginx.com/blog/building-microservices-using-an-api-gateway/)
* [Microservices Patterns: With examples in Java](https://www.amazon.com/gp/product/1617294543/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1617294543&linkId=ac7b6a57f866ac006a309d9086e8cfbd)
* [Building Microservices: Designing Fine-Grained Systems](https://www.amazon.com/gp/product/1491950358/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1491950358&linkId=4c95ca9831e05e3f0dadb08841d77bf1)
