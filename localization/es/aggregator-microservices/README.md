---
title: Aggregator Microservices
category: Architectural
language: es
tag:
- Cloud distributed
- Decoupling
- Microservices
---

## Propósito

El usuario realiza una sola llamada al servicio del agregador y, a continuación, el agregador llama a cada microservicio relevante.

## Explicación

Ejemplo del mundo real

> Nuestro mercado web necesita información sobre los productos y su inventario actual. Hace una llamada a un agregador
> servicio que a su vez llama al microservicio de información del producto y al microservicio de inventario del producto que devuelve la
> información combinada.

En palabras sencillas

> Aggregator Microservice recopila datos de varios microservicios y devuelve un agregado para su procesamiento.

StackOverflow dice

> Aggregator Microservice invoca múltiples servicios para lograr la funcionalidad requerida por la aplicación.

**Ejemplo programático**

Empecemos por el modelo de datos. Aquí está nuestro `Product`.

```java
public class Product {
  private String title;
  private int productInventories;
  // getters and setters ->
  ...
}
```

A continuación, podemos presentar nuestro microservicio `Aggregator` (Agregador de microservicios). Contiene él `ProductInformationClient` (Información del producto del cliente) y él
`ProductInventoryClient` (Inventario del producto del cliente) de los clientes para llamar a los respectivos microservicios.

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

Esta es la esencia de la implementación de microservicios de información. El microservicio de inventario es similar, simplemente regresa
recuentos de inventario.

```java
@RestController
public class InformationController {
  @RequestMapping(value = "/information", method = RequestMethod.GET)
  public String getProductTitle() {
    return "The Product Title.";
  }
}
```

Ahora llamando a nuestra REST API `Aggregator` devuelve la información del producto.

```bash
curl http://localhost:50004/product
{"title":"The Product Title.","productInventories":5}
```

## Diagrama de clase

![alt text](./aggregator-service/etc/aggregator-service.png "Aggregator Microservice")

## Aplicabilidad

Utilice el patrón Agregador de microservicios (Aggregator Microservices) cuando necesite una API unificada para varios microservicios, independientemente del dispositivo cliente.

## Créditos

* [Patrones de diseño de microservicios] (http://web.archive.org/web/20190705163602/http://blog.arungupta.me/microservice-design-patterns/)
* [Patrones de microservicios: Con ejemplos en Java](https://www.amazon.com/gp/product/1617294543/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1617294543&linkId=8b4e570267bc5fb8b8189917b461dc60)
* [Patrones de arquitectura: Descubra patrones esenciales en el ámbito más indispensable de la arquitectura empresarial](https://www.amazon.com/gp/product/B077T7V8RC/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=B077T7V8RC&linkId=c34d204bfe1b277914b420189f09c1a4)
