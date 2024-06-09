---
title: "Combinator Pattern in Java: Crafting Flexible Code Compositions"
shortTitle: Combinator
description: "Learn how to use the Combinator pattern in Java with real-world examples and comprehensive explanations. Enhance your Java design skills with this detailed guide."
category: Functional
language: en
tag:
  - Abstraction
  - Code simplification
  - Functional decomposition
  - Idiom
  - Reactive
---

## Also known as

* Function Composition
* Functional Combinator

## Intent of Combinator Design Pattern

The Combinator pattern, a functional programming technique widely used in Java, is essential for combining functions to build complex behaviors. This pattern allows developers to combine multiple smaller functions or operations into a single, more complex operation, promoting flexible and reusable code. By leveraging higher-order functions, the Combinator pattern enhances code reuse and maintainability in Java applications, making it a valuable tool in software design. This approach fosters the creation of modular, scalable solutions in Java development.

## Detailed Explanation of Combinator Pattern with Real-World Examples

Real-world example

> In the real world, the combinator design pattern can be likened to a meal preparation process in a kitchen. Imagine a chef who has a set of simple operations: chopping vegetables, boiling water, cooking rice, grilling chicken, and mixing ingredients. Each of these operations is a standalone function. The chef can combine these operations in various sequences to create different dishes. For example, to prepare a chicken rice bowl, the chef can combine the operations of cooking rice, grilling chicken, and mixing them with vegetables. By reusing these basic operations and combining them in different ways, the chef can efficiently prepare a wide variety of meals. Similarly, in software, the combinator pattern allows developers to combine simple, reusable functions to create more complex behaviors.

In plain words

> The combinator design pattern combines simple, reusable functions to create more complex and flexible operations.

Wikipedia says

> A combinator is a higher-order function that uses only function application and earlier defined combinators to define a result from its arguments.

## Programmatic Example of Combinator Pattern in Java

In software design, combinatory logic is pivotal for creating reusable and modular code components. By leveraging higher-order functions, the Combinator pattern promotes code reuse and maintainability in Java applications.

In this Java example, we demonstrate the implementation of combinators such as `contains`, `not`, `or`, and `and` to create complex finders.

```java
// Functional interface to find lines in text.
public interface Finder {

    // The function to find lines in text.
    List<String> find(String text);

    // Simple implementation of function {@link #find(String)}.
    static Finder contains(String word) {
        return txt -> Stream.of(txt.split("\n"))
                .filter(line -> line.toLowerCase().contains(word.toLowerCase()))
                .collect(Collectors.toList());
    }

    // combinator not.
    default Finder not(Finder notFinder) {
        return txt -> {
            List<String> res = this.find(txt);
            res.removeAll(notFinder.find(txt));
            return res;
        };
    }

    // combinator or.
    default Finder or(Finder orFinder) {
        return txt -> {
            List<String> res = this.find(txt);
            res.addAll(orFinder.find(txt));
            return res;
        };
    }

    // combinator and.
    default Finder and(Finder andFinder) {
        return
                txt -> this
                        .find(txt)
                        .stream()
                        .flatMap(line -> andFinder.find(line).stream())
                        .collect(Collectors.toList());
    }
    // Other properties and methods...
}
```

Then we have also another combinator for some complex finders `advancedFinder`, `filteredFinder`, `specializedFinder` and `expandedFinder`.

```java
// Complex finders consisting of simple finder.
public class Finders {

    private Finders() {
    }

    // Finder to find a complex query.
    public static Finder advancedFinder(String query, String orQuery, String notQuery) {
        return
                Finder.contains(query)
                        .or(Finder.contains(orQuery))
                        .not(Finder.contains(notQuery));
    }

    // Filtered finder looking a query with excluded queries as well.
    public static Finder filteredFinder(String query, String... excludeQueries) {
        var finder = Finder.contains(query);

        for (String q : excludeQueries) {
            finder = finder.not(Finder.contains(q));
        }
        return finder;
    }

    // Specialized query. Every next query is looked in previous result.
    public static Finder specializedFinder(String... queries) {
        var finder = identMult();

        for (String query : queries) {
            finder = finder.and(Finder.contains(query));
        }
        return finder;
    }

    // Expanded query. Looking for alternatives.
    public static Finder expandedFinder(String... queries) {
        var finder = identSum();

        for (String query : queries) {
            finder = finder.or(Finder.contains(query));
        }
        return finder;
    }
    // Other properties and methods...
}
```

Now that we have created the interface and methods for combinators, let's see how an application works with them.

```java
public class CombinatorApp {

    private static final String TEXT = """
            It was many and many a year ago,
            In a kingdom by the sea,
            That a maiden there lived whom you may know
            By the name of ANNABEL LEE;
            And this maiden she lived with no other thought
            Than to love and be loved by me.
            I was a child and she was a child,
            In this kingdom by the sea;
            But we loved with a love that was more than love-
            I and my Annabel Lee;
            With a love that the winged seraphs of heaven
            Coveted her and me.""";

    public static void main(String[] args) {
        var queriesOr = new String[]{"many", "Annabel"};
        var finder = Finders.expandedFinder(queriesOr);
        var res = finder.find(text());
        LOGGER.info("the result of expanded(or) query[{}] is {}", queriesOr, res);

        var queriesAnd = new String[]{"Annabel", "my"};
        finder = Finders.specializedFinder(queriesAnd);
        res = finder.find(text());
        LOGGER.info("the result of specialized(and) query[{}] is {}", queriesAnd, res);

        finder = Finders.advancedFinder("it was", "kingdom", "sea");
        res = finder.find(text());
        LOGGER.info("the result of advanced query is {}", res);

        res = Finders.filteredFinder(" was ", "many", "child").find(text());
        LOGGER.info("the result of filtered query is {}", res);
    }

    private static String text() {
        return TEXT;
    }
}
```

Program output:

```
20:03:52.746 [main] INFO com.iluwatar.combinator.CombinatorApp -- the result of expanded(or) query[[many, Annabel]] is [It was many and many a year ago,, By the name of ANNABEL LEE;, I and my Annabel Lee;]
20:03:52.749 [main] INFO com.iluwatar.combinator.CombinatorApp -- the result of specialized(and) query[[Annabel, my]] is [I and my Annabel Lee;]
20:03:52.750 [main] INFO com.iluwatar.combinator.CombinatorApp -- the result of advanced query is [It was many and many a year ago,]
20:03:52.750 [main] INFO com.iluwatar.combinator.CombinatorApp -- the result of filtered query is [But we loved with a love that was more than love-]
```

Now we can design our app to with the queries finding feature `expandedFinder`, `specializedFinder`, `advancedFinder`, `filteredFinder` which are all derived from `contains`, `or`, `not`, `and`.

## When to Use the Combinator Pattern in Java

The Combinator pattern is particularly useful in functional programming where complex values are built from simpler, reusable components.

The applicable scenarios include:

* The solution to a problem can be constructed from simple, reusable components.
* There is a need for high modularity and reusability of functions.
* The programming environment supports first-class functions and higher-order functions.

## Real-World Applications of Combinator Pattern in Java

* Functional programming languages like Haskell and Scala extensively use combinators for tasks ranging from parsing to UI construction.
* In domain-specific languages, particularly those involved in parsing, such as parsing expression grammars.
* In libraries for functional programming in languages like JavaScript, Python, and Ruby.
* java.util.function.Function#compose
* java.util.function.Function#andThen

## Benefits and Trade-offs of Combinator Pattern

Benefits:

* Enhances developer productivity by using domain-specific terms and facilitates parallel execution in Java applications.
* Enhances modularity and reusability by breaking down complex tasks into simpler, composable functions.
* Promotes readability and maintainability by using a declarative style of programming.
* Facilitates lazy evaluation and potentially more efficient execution through function composition.

Trade-offs:

* Can lead to a steep learning curve for those unfamiliar with functional programming principles.
* May result in performance overhead due to the creation of intermediate functions.
* Debugging can be challenging due to the abstract nature of function compositions.

## Related Java Design Patterns

* [Chain of Responsibility](https://java-design-patterns.com/patterns/chain-of-responsibility/): Relies on chaining objects, whereas Combinator chains functions.
* [Decorator](https://java-design-patterns.com/patterns/decorator/): Similar to Combinator in enhancing functionality, but Decorator focuses on object augmentation.
* [Strategy](https://java-design-patterns.com/patterns/strategy/): Both involve selecting an algorithm at runtime, but Combinator uses composition of functions.

## References and Credits

* [Functional Programming in Scala](https://amzn.to/4cEo6K2)
* [Haskell: The Craft of Functional Programming](https://amzn.to/4axxtcF)
* [Structure and Interpretation of Computer Programs](https://amzn.to/3PJwVsf)
* [Combinator Pattern with Java 8 (Gregor Trefs)](https://gtrefs.github.io/code/combinator-pattern/)
