---
title: API Gateway
shortTitle: API Gateway
category: Architectural
language: es
tag:
  - Cloud distributed
  - Decoupling
  - Microservices
---

## Propósito

Agregar llamadas a los microservicios en un mismo lugar, la puerta de enlace API (API Gateway). El usuario 
hace una llamada simple a la API Gateway, y la API Gateway hace la llamada a cada microservicio relevante.

## Explicación

Con el patrón de microservicios, el cliente puede necesitar datos de múltiples microservicios. Si el 
cliente llamara a cada microservicio de forma directe, podría ocasionar tiempos de carga largos, ya que
el cliente tendría que hacer una solicitud de red para cada microservicio llamado. Además, tener
la llamada del cliente a cada microservicio vincula directamente al cliente con ese microservicio - si la
implementacion interna del cambio de microservicios (por ejemplo, si dos microservicios se combinan en 
algún momento en el futuro) o si la ubicación (host y puerto) de un microservicio cambia, entonces cada 
cliente que hace uso de esos microservicios debe ser actualizado.

La intención del patrón API Gateway es aliviar algunos de estos problemas. En el patrón API Gateway, 
se coloca una entidad adicional (la API Gateway) entre el cliente y los microservicios.
El trabajo de API Gateway es agregar las llamadas a los microservicios. En lugar de que el cliente
llame a cada microservicio individualmente, el cliente llama a la API Gateway una sola vez. La API
Gateway luego llama a cada uno de los microservicios que necesita el cliente.

Ejemplo real

> Estamos implementando un sistema de microservicios y API Gateway para un sitio e-commerce. En este
> sistema API Gateway realiza llamadas a los microservicios Image y Price. (Imagen y Precio)

En otras palabras

> Para un sistema implementado utilizando una arquitectura de microservicios, API Gateway es el único
> punto de entrada que agrega las llamadas a los microservicios individuales.

Wikipedia dice

> API Gateway es un servidor que actúa como un front-end de API, recibe solicitudes de API, aplica la 
> limitación y políticas de seguridad, pasa las solicitudes al servicio back-end y luego devuelve la 
> respuesta al solicitante. Una puerta de enlace a menudo incluye un motor de transformación para 
> orquestar y modificar las solicitudes y respuestas sobre la marcha. Una puerta de enlace también 
> puede proporcionar funciones como recopilar análisis de datos y almacenamiento en caché. La puerta 
> de enlace puede proporcionar funcionalidad para soportar autenticación, autorización, seguridad, 
> auditoría y cumplimiento normativo.

**Código de ejemplo**

Esta implementación muestra cómo podría verse el patrón API Gateway para un sitio de e-commerce. El
`ApiGateway` hace llamadas a los microservicios Image y Price usando `ImageClientImpl` y`PriceClientImpl` 
respectivamente. Los clientes que ven el sitio en un dispositivo de escritorio pueden ver la información
de precio y una imagen de un producto, entonces `ApiGateway` llama a los microservicios y
agrega los datos en el modelo `DesktopProduct`. Sin embargo, los usuarios de dispositivos móviles solo 
ven información de precios, no ven una imagen del producto. Para usuarios móviles, `ApiGateway` solo 
recupera el precio información, que utiliza para completar el `MobileProduct`.

Aquí está la implementación del microservicio de imagen (Image).

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

Aquí está la implementación del microservicio de precio (Price).

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

Aquí podemos ver cómo API Gateway asigna las solicitudes a los microservicios.

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

## Diagrama de clase

![alt text](./etc/api-gateway.png "API Gateway")

## Aplicaciones

Usa el patrón de API Gateway cuando

* Estás usando una arquitectura de microservicios y necesites un único punto de agregación para las llamadas de microservicios.

## Tutoriales

* [Exploring the New Spring Cloud Gateway](https://www.baeldung.com/spring-cloud-gateway)
* [Spring Cloud - Gateway](https://www.tutorialspoint.com/spring_cloud/spring_cloud_gateway.htm)
* [Getting Started With Spring Cloud Gateway](https://dzone.com/articles/getting-started-with-spring-cloud-gateway)

## Créditos

* [microservices.io - API Gateway](http://microservices.io/patterns/apigateway.html)
* [NGINX - Building Microservices: Using an API Gateway](https://www.nginx.com/blog/building-microservices-using-an-api-gateway/)
* [Microservices Patterns: With examples in Java](https://www.amazon.com/gp/product/1617294543/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1617294543&linkId=ac7b6a57f866ac006a309d9086e8cfbd)
* [Building Microservices: Designing Fine-Grained Systems](https://www.amazon.com/gp/product/1491950358/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1491950358&linkId=4c95ca9831e05e3f0dadb08841d77bf1)
