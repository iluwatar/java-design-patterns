---
title: Business Delegate
shortTitle: Business Delegate
category: Structural
language: es
tag:
  - Decoupling
---

## Propósito

El patrón Business Delegate añade una capa de abstracción entre los niveles de presentación y de negocio. Al utilizar
este patrón, conseguimos un acoplamiento flexible entre los niveles y encapsulamos el conocimiento sobre cómo localizar,
conectar e interactuar con los objetos de negocio que componen la aplicación.

## También conocido como

Service Representative

## Explicación

Ejemplo del mundo real

> Una aplicación para teléfonos móviles promete transmitir a tu dispositivo cualquier película existente. Captura la
> cadena de búsqueda del usuario y se la pasa al Delegado de Negocio. El Delegado de Negocio selecciona el
> servicio de streaming de vídeo más adecuado y reproduce el vídeo.

En pocas palabras

> Business Delegate añade una capa de abstracción entre los niveles de presentación y de negocio.

Wikipedia dice

> Business Delegate es un patrón de diseño de Java EE. Este patrón está dirigido a reducir el acoplamiento entre los
> servicios de negocio y el nivel de presentación conectado, y para ocultar los detalles de implementación de los
> servicios (incluyendo la búsqueda y la accesibilidad de la arquitectura EJB). Los delegados de negocio actúan como un
> adaptador para invocar objetos de negocio desde la capa de presentación.

**Ejemplo programático**

En primer lugar, tenemos una abstracción para los servicios de streaming de vídeo `VideoStreamingService` y un par de
implementaciones `NetflixService` y `YouTubeService`.

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

A continuación, tenemos un servicio de búsqueda `BusinessLookup` que decide qué servicio de transmisión de vídeo
utilizar.

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

El Delegado de Negocio `BusinessDelegate` utiliza una búsqueda de negocio para dirigir las solicitudes de reproducción
de películas a un servicio de streaming de vídeo adecuado.

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

El cliente móvil `MobileClient` utiliza Business Delegate para llamar al nivel de negocio.

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

Por último, podemos demostrar el ejemplo completo en acción.

```java
  public static void main(String[]args){

        // preparar los objetos
        var businessDelegate=new BusinessDelegate();
        var businessLookup=new BusinessLookup();
        businessLookup.setNetflixService(new NetflixService());
        businessLookup.setYouTubeService(new YouTubeService());
        businessDelegate.setLookupService(businessLookup);

        // crear el cliente y utilizar el Business Delegate
        var client=new MobileClient(businessDelegate);
        client.playbackMovie("Die Hard 2");
        client.playbackMovie("Maradona: The Greatest Ever");
        }
```

Aquí está la salida de la consola.

```
21:15:33.790 [main] INFO com.iluwatar.business.delegate.NetflixService - NetflixService is now processing
21:15:33.794 [main] INFO com.iluwatar.business.delegate.YouTubeService - YouTubeService is now processing
```

## Diagrama de clases

![Diagrama de clases](./etc/business-delegate.urm.png "Business Delegate")

## Patrones relacionados

* [Patrón de localización de servicios](https://java-design-patterns.com/patterns/service-locator/)

## Aplicabilidad

Utilice el patrón Business Delegate cuando

* Desea un acoplamiento flexible entre los niveles de presentación y de negocio.
* Quieres orquestar llamadas a múltiples servicios de negocio
* Se desea encapsular las búsquedas y llamadas a servicios.
* Es necesario abstraer y encapsular la comunicación entre la capa cliente y los servicios de negocio.

## Tutoriales

* [Patrón Delegado de Negocio en TutorialsPoint](https://www.tutorialspoint.com/design_pattern/business_delegate_pattern.htm)

## Usos conocidos

* Aplicaciones empresariales que utilicen Java EE (Java Platform, Enterprise Edition)
* Aplicaciones que requieren acceso remoto a servicios empresariales

## Consecuencias

Ventajas:

* Desacoplamiento de los niveles de presentación y de negocio: Permite que el nivel de cliente y los servicios
  empresariales evolucionen de forma independiente.
* Transparencia de ubicación: Los clientes no se ven afectados por cambios en la ubicación o la instanciación de los
  servicios de negocio.
* Reutilización y escalabilidad: Los objetos Business Delegate pueden ser reutilizados por múltiples clientes, y el
  patrón soporta el equilibrio de carga y la escalabilidad.
  carga y escalabilidad.

Contrapartidas:

* Complejidad: Introduce capas y abstracciones adicionales que pueden aumentar la complejidad.
* Sobrecarga de rendimiento: La indirección adicional puede suponer una ligera penalización en el rendimiento.

## Patrones relacionados

* [Localizador de servicios](https://java-design-patterns.com/patterns/service-locator/): El Delegado de Negocio (
  Business Delegate) utiliza el Localizador de Servicios (Service Locator) para localizar servicios de negocio.
* [Fachada de Sesión](https://java-design-patterns.com/patterns/session-facade/): El Delegado de Negocio (Business
  Delegate) puede utilizar la Fachada de Sesión (Session Facade) para proporcionar una interfaz unificada a un conjunto
  de servicios de negocio.
* [Entidad Compuesta](https://java-design-patterns.com/patterns/composite-entity/): El Delegado (Business Delegate) de
  Negocio puede utilizar Entidad Compuesta (Composite Entity) para gestionar el estado de los servicios de negocio.

## Créditos

* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
* [Core J2EE Patterns: Best Practices and Design Strategies](https://www.amazon.com/gp/product/0130648841/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0130648841&linkId=a0100de2b28c71ede8db1757fb2b5947)
