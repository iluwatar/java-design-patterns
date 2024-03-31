---
title: Business Delegate
category: Structural
language: en
tag:
    - Decoupling
---

## Intent

The Business Delegate pattern adds an abstraction layer between presentation and business tiers. By using the pattern we gain loose coupling between the tiers and encapsulate knowledge about how to locate, connect to, and interact with the business objects that make up the application.

## Also known as

Service Representative

## Explanation

Real world example

> A mobile phone application promises to stream any movie in existence to your device. It captures the user's search string and passes this on to the Business Delegate. The Business Delegate selects the most suitable video streaming service and plays the video from there.

In Plain Words

> Business Delegate adds an abstraction layer between the presentation and business tiers.

Wikipedia says

> Business Delegate is a Java EE design pattern. This pattern is directing to reduce the coupling in between business services and the connected presentation tier, and to hide the implementation details of services (including lookup and accessibility of EJB architecture). Business Delegates acts as an adaptor to invoke business objects from the presentation tier.

**Programmatic Example**

First, we have an abstraction for video streaming services and a couple of implementations.

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

Then, we have a lookup service that decides which video streaming service to use.

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

The Business Delegate uses a business lookup to route movie playback requests to a suitable video streaming service.

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

The mobile client utilizes Business Delegate to call the business tier.

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

Finally, we can demonstrate the complete example in action.

```java
  public static void main(String[]args){

        // prepare the objects
        var businessDelegate=new BusinessDelegate();
        var businessLookup=new BusinessLookup();
        businessLookup.setNetflixService(new NetflixService());
        businessLookup.setYouTubeService(new YouTubeService());
        businessDelegate.setLookupService(businessLookup);

        // create the client and use the Business Delegate
        var client=new MobileClient(businessDelegate);
        client.playbackMovie("Die Hard 2");
        client.playbackMovie("Maradona: The Greatest Ever");
        }
```

Here is the console output.

```
21:15:33.790 [main] INFO com.iluwatar.business.delegate.NetflixService - NetflixService is now processing
21:15:33.794 [main] INFO com.iluwatar.business.delegate.YouTubeService - YouTubeService is now processing
```

## Class diagram

![alt text](./etc/business-delegate.urm.png "Business Delegate")

## Related patterns

* [Service locator pattern](https://java-design-patterns.com/patterns/service-locator/)

## Applicability

Use the Business Delegate pattern when

* You want loose coupling between presentation and business tiers
* You want to orchestrate calls to multiple business services
* You want to encapsulate service lookups and service calls
* There is a need to abstract and encapsulate the communication between the client tier and business services

## Tutorials

* [Business Delegate Pattern at TutorialsPoint](https://www.tutorialspoint.com/design_pattern/business_delegate_pattern.htm)

## Known Uses

* Enterprise applications using Java EE (Java Platform, Enterprise Edition)
* Applications requiring remote access to business services

## Consequences

Benefits:

* Decoupling of Presentation and Business Tiers: Allows the client tier and business services to evolve independently.
* Location Transparency: Clients remain unaffected by changes in the location or the instantiation of business services.
* Reuse and Scalability: Business Delegate objects can be reused by multiple clients, and the pattern supports load balancing and scalability.

Trade-offs:

* Complexity: Introduces additional layers and abstractions, which may increase complexity.
* Performance Overhead: The additional indirection may incur a slight performance penalty.

## Related patterns

* [Service Locator](https://java-design-patterns.com/patterns/service-locator/): Business Delegate uses Service Locator to locate business services.
* [Session Facade](https://java-design-patterns.com/patterns/session-facade/): Business Delegate may use Session Facade to provide a unified interface to a set of business services.
* [Composite Entity](https://java-design-patterns.com/patterns/composite-entity/): Business Delegate may use Composite Entity to manage the state of business services.

## Credits

* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
* [Core J2EE Patterns: Best Practices and Design Strategies](https://www.amazon.com/gp/product/0130648841/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0130648841&linkId=a0100de2b28c71ede8db1757fb2b5947)
