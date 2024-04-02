---
title: Combinator
category: Functional
language: en
tag:
    - Idiom
    - Reactive
---

## Also known as

* Function Composition
* Functional Combinator

## Intent

The Combinator pattern is intended to enable complex functionalities by combining simple functions into more complex ones. It aims to achieve modularization and reusability by breaking down a task into simpler, interchangeable components that can be composed in various ways.

## Explanation

Real world example

> In computer science, combinatory logic is used as a simplified model of computation, used in computability theory and proof theory. Despite its simplicity, combinatory logic captures many essential features of computation.

In plain words

> The combinator allows you to create new "things" from previously defined "things"

Wikipedia says

> A combinator is a higher-order function that uses only function application and earlier defined combinators to define a result from its arguments.

**Programmatic Example**

Translating the combinator example above. First of all, we have an interface consist of several methods `contains`, `not`, `or`, `and` .

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
	...
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
	...
}
```

Now we have created the interface and methods for combinators. Now we have an application working on these combinators.

```java
var queriesOr=new String[]{"many","Annabel"};
        var finder=Finders.expandedFinder(queriesOr);
        var res=finder.find(text());
        LOGGER.info("the result of expanded(or) query[{}] is {}",queriesOr,res);

        var queriesAnd=new String[]{"Annabel","my"};
        finder=Finders.specializedFinder(queriesAnd);
        res=finder.find(text());
        LOGGER.info("the result of specialized(and) query[{}] is {}",queriesAnd,res);

        finder=Finders.advancedFinder("it was","kingdom","sea");
        res=finder.find(text());
        LOGGER.info("the result of advanced query is {}",res);

        res=Finders.filteredFinder(" was ","many","child").find(text());
        LOGGER.info("the result of filtered query is {}",res);

private static String text(){
        return
        "It was many and many a year ago,\n"
        +"In a kingdom by the sea,\n"
        +"That a maiden there lived whom you may know\n"
        +"By the name of ANNABEL LEE;\n"
        +"And this maiden she lived with no other thought\n"
        +"Than to love and be loved by me.\n"
        +"I was a child and she was a child,\n"
        +"In this kingdom by the sea;\n"
        +"But we loved with a love that was more than love-\n"
        +"I and my Annabel Lee;\n"
        +"With a love that the winged seraphs of heaven\n"
        +"Coveted her and me.";
        }
```

**Program output:**

```java
the result of expanded(or)query[[many,Annabel]]is[It was many and many a year ago,,By the name of ANNABEL LEE;,I and my Annabel Lee;]
        the result of specialized(and)query[[Annabel,my]]is[I and my Annabel Lee;]
        the result of advanced query is[It was many and many a year ago,]
        the result of filtered query is[But we loved with a love that was more than love-]
```

Now we can design our app to with the queries finding feature `expandedFinder`, `specializedFinder`, `advancedFinder`, `filteredFinder` which are all derived from `contains`, `or`, `not`, `and`.

## Class diagram

![alt text](./etc/combinator.urm.png "Combinator class diagram")

## Applicability

This pattern is applicable in scenarios where:

* The solution to a problem can be constructed from simple, reusable components.
* There is a need for high modularity and reusability of functions.
* The programming environment supports first-class functions and higher-order functions.

## Known Uses

* Functional programming languages like Haskell and Scala extensively use combinators for tasks ranging from parsing to UI construction.
* In domain-specific languages, particularly those involved in parsing, such as parsing expression grammars.
* In libraries for functional programming in languages like JavaScript, Python, and Ruby.
* java.util.function.Function#compose
* java.util.function.Function#andThen

## Consequences

Benefits:

* Enhances modularity and reusability by breaking down complex tasks into simpler, composable functions.
* Promotes readability and maintainability by using a declarative style of programming.
* Facilitates lazy evaluation and potentially more efficient execution through function composition.

Trade-offs:

* Can lead to a steep learning curve for those unfamiliar with functional programming principles.
* May result in performance overhead due to the creation of intermediate functions.
* Debugging can be challenging due to the abstract nature of function compositions.

## Related Patterns

* [Strategy](https://java-design-patterns.com/patterns/strategy/): Both involve selecting an algorithm at runtime, but Combinator uses composition of functions.
* [Decorator](https://java-design-patterns.com/patterns/decorator/): Similar to Combinator in enhancing functionality, but Decorator focuses on object augmentation.
* [Chain of Responsibility](https://java-design-patterns.com/patterns/chain-of-responsibility/): Relies on chaining objects, whereas Combinator chains functions.

## Credits

- [Example for Java](https://gtrefs.github.io/code/combinator-pattern/)
- [Combinator pattern](https://wiki.haskell.org/Combinator_pattern)
- [Combinatory logic](https://wiki.haskell.org/Combinatory_logic)
- [Structure and Interpretation of Computer Programs](https://amzn.to/3PJwVsf)
- [Functional Programming in Scala](https://amzn.to/4cEo6K2)
- [Haskell: The Craft of Functional Programming](https://amzn.to/4axxtcF)
