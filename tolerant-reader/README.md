---
title: "Tolerant Reader Pattern in Java: Enhancing API Resilience and Compatibility"
shortTitle: Tolerant Reader
description: "Discover how the Tolerant Reader pattern can boost your API's resilience by ignoring unrecognized data, ensuring backward compatibility and seamless integration. Learn through examples and best practices on implementing this robust communication mechanism."
category: Resilience
language: en
tag:
  - API design
  - Decoupling
  - Fault tolerance
  - Integration
---

## Also known as

* Lenient Consumer

## Intent of Tolerant Reader Design Pattern

The Tolerant Reader pattern enhances system resilience to changes in data structures by strategically ignoring unrecognized elements, promoting robust API design.

## Detailed Explanation of Tolerant Reader Pattern with Real-World Examples

Real-world example

> Imagine a postal system that delivers letters and packages to recipients. In this system, postal workers deliver mail regardless of additional information or stickers that might be present on the envelopes or packages. If a package has extra labels or instructions that the postal system does not recognize, the postal worker ignores these and focuses only on the essential information like the address. This approach ensures that the delivery process remains functional even when senders use different formats or include unnecessary details, similar to how the Tolerant Reader pattern works in software by ignoring unrecognized data elements to maintain functionality and compatibility.

In plain words

> Utilize the Tolerant Reader pattern to establish robust and resilient communication between services, ensuring data compatibility and integration.

[Robustness Principle](https://java-design-patterns.com/principles/#robustness-principle) says

> Be conservative in what you do, be liberal in what you accept from others.

## Programmatic Example of Tolerant Reader Pattern in Java

We are persisting `RainbowFish` objects to file. Later on they need to be restored. What makes it problematic is that `RainbowFish` data structure is versioned and evolves over time. New version of `RainbowFish` needs to be able to restore old versions as well.

Here's the versioned `RainbowFish`. Notice how the second version introduces additional properties.

```java
@Getter
@RequiredArgsConstructor
public class RainbowFish implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final int age;
    private final int lengthMeters;
    private final int weightTons;
}
```

```java
@Getter
public class RainbowFishV2 extends RainbowFish {

    @Serial
    private static final long serialVersionUID = 1L;

    private boolean sleeping;
    private boolean hungry;
    private boolean angry;

    public RainbowFishV2(String name, int age, int lengthMeters, int weightTons) {
        super(name, age, lengthMeters, weightTons);
    }

    public RainbowFishV2(String name, int age, int lengthMeters, int weightTons, boolean sleeping,
                         boolean hungry, boolean angry) {
        this(name, age, lengthMeters, weightTons);
        this.sleeping = sleeping;
        this.hungry = hungry;
        this.angry = angry;
    }
}
```

Next we introduce the `RainbowFishSerializer`. This is the class that implements the Tolerant Reader pattern.

```java
@NoArgsConstructor
public final class RainbowFishSerializer {

    public static void writeV1(RainbowFish rainbowFish, String filename) throws IOException {
        var map = Map.of(
                "name", rainbowFish.getName(),
                "age", String.format("%d", rainbowFish.getAge()),
                "lengthMeters", String.format("%d", rainbowFish.getLengthMeters()),
                "weightTons", String.format("%d", rainbowFish.getWeightTons())
        );

        try (var fileOut = new FileOutputStream(filename);
             var objOut = new ObjectOutputStream(fileOut)) {
            objOut.writeObject(map);
        }
    }

    public static void writeV2(RainbowFishV2 rainbowFish, String filename) throws IOException {
        var map = Map.of(
                "name", rainbowFish.getName(),
                "age", String.format("%d", rainbowFish.getAge()),
                "lengthMeters", String.format("%d", rainbowFish.getLengthMeters()),
                "weightTons", String.format("%d", rainbowFish.getWeightTons()),
                "angry", Boolean.toString(rainbowFish.getAngry()),
                "hungry", Boolean.toString(rainbowFish.getHungry()),
                "sleeping", Boolean.toString(rainbowFish.getSleeping())
        );

        try (var fileOut = new FileOutputStream(filename);
             var objOut = new ObjectOutputStream(fileOut)) {
            objOut.writeObject(map);
        }
    }

    public static RainbowFish readV1(String filename) throws IOException, ClassNotFoundException {
        Map<String, String> map;

        try (var fileIn = new FileInputStream(filename);
             var objIn = new ObjectInputStream(fileIn)) {
            map = (Map<String, String>) objIn.readObject();
        }

        return new RainbowFish(
                map.get("name"),
                Integer.parseInt(map.get("age")),
                Integer.parseInt(map.get("lengthMeters")),
                Integer.parseInt(map.get("weightTons"))
        );
    }
}
```

And finally, here's the full example in action.

```java
public static void main(String[] args) throws IOException, ClassNotFoundException {
    // Write V1
    var fishV1 = new RainbowFish("Zed", 10, 11, 12);
    LOGGER.info("fishV1 name={} age={} length={} weight={}", fishV1.getName(),
            fishV1.getAge(), fishV1.getLengthMeters(), fishV1.getWeightTons());
    RainbowFishSerializer.writeV1(fishV1, "fish1.out");
    // Read V1
    var deserializedRainbowFishV1 = RainbowFishSerializer.readV1("fish1.out");
    LOGGER.info("deserializedFishV1 name={} age={} length={} weight={}",
            deserializedRainbowFishV1.getName(), deserializedRainbowFishV1.getAge(),
            deserializedRainbowFishV1.getLengthMeters(), deserializedRainbowFishV1.getWeightTons());
    // Write V2
    var fishV2 = new RainbowFishV2("Scar", 5, 12, 15, true, true, true);
    LOGGER.info(
            "fishV2 name={} age={} length={} weight={} sleeping={} hungry={} angry={}",
            fishV2.getName(), fishV2.getAge(), fishV2.getLengthMeters(), fishV2.getWeightTons(),
            fishV2.isHungry(), fishV2.isAngry(), fishV2.isSleeping());
    RainbowFishSerializer.writeV2(fishV2, "fish2.out");
    // Read V2 with V1 method
    var deserializedFishV2 = RainbowFishSerializer.readV1("fish2.out");
    LOGGER.info("deserializedFishV2 name={} age={} length={} weight={}",
            deserializedFishV2.getName(), deserializedFishV2.getAge(),
            deserializedFishV2.getLengthMeters(), deserializedFishV2.getWeightTons());
}
```

Program output:

```
15:38:00.602 [main] INFO com.iluwatar.tolerantreader.App -- fishV1 name=Zed age=10 length=11 weight=12
15:38:00.618 [main] INFO com.iluwatar.tolerantreader.App -- deserializedFishV1 name=Zed age=10 length=11 weight=12
15:38:00.618 [main] INFO com.iluwatar.tolerantreader.App -- fishV2 name=Scar age=5 length=12 weight=15 sleeping=true hungry=true angry=true
15:38:00.619 [main] INFO com.iluwatar.tolerantreader.App -- deserializedFishV2 name=Scar age=5 length=12 weight=15
```

## When to Use the Tolerant Reader Pattern in Java

* Apply the Tolerant Reader pattern when your system consumes data from evolving external sources, maintaining efficiency and data integrity.
* Applicable when backward compatibility is required in API design.
* Suitable for integration scenarios where different systems exchange data and evolve independently.

## Real-World Applications of Tolerant Reader Pattern in Java

* JSON or XML parsers that skip unknown elements.
* API clients in microservices architectures that interact with multiple versions of a service.

## Benefits and Trade-offs of Tolerant Reader Pattern

Benefits:

* Increases the robustness and flexibility of the system.
* Allows independent evolution of producers and consumers in a distributed system.
* Simplifies versioning by enabling backward compatibility.

Trade-offs:

* May result in silent failures if important data is ignored.
* Can complicate debugging and tracing of issues due to missing or unrecognized data.

## Related Java Design Patterns

* [Adapter](https://java-design-patterns.com/patterns/adapter/): Both patterns deal with data transformation and integration, but the Adapter Pattern focuses on converting interfaces, while Tolerant Reader focuses on ignoring unrecognized data.
* [Facade](https://java-design-patterns.com/patterns/facade/): Simplifies interactions with complex systems, similar to how Tolerant Reader simplifies data consumption by ignoring irrelevant data.
* [Strategy](https://java-design-patterns.com/patterns/strategy/): Can be used in conjunction with Tolerant Reader to dynamically switch between different data handling strategies.

## References and Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
* [Service Design Patterns: Fundamental Design Solutions for SOAP/WSDL and RESTful Web Services](https://amzn.to/4dNIfOx)
* [Tolerant Reader (Martin Fowler)](http://martinfowler.com/bliki/TolerantReader.html)
