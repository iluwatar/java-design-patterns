---
title: Combinator
category: Idiom
language: en
tags:
 - Reactive
---

## Also known as

Composition pattern

## Intent

The functional pattern representing a style of organizing libraries centered around the idea of combining functions.
Putting it simply, there is some type T, some functions for constructing “primitive” values of type T, and some “combinators” which can combine values of type T in various ways to build up more complex values of type T.

## Explanation

Real world example

> In computer science, combinatory logic is used as a simplified model of computation, used in computability theory and proof theory. Despite its simplicity, combinatory logic captures many essential features of computation.
> 

In plain words

> The combinator allows you to create new "things" from previously defined "things".
> 

Wikipedia says

> A combinator is a higher-order function that uses only function application and earlier defined combinators to define a result from its arguments.
> 

**Programmatic Example**

Translating the combinator example above. First of all, we have a interface consist of several methods `contains`, `not`, `or`, `and` .

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

private static String text() {
    return
        "It was many and many a year ago,\n"
            + "In a kingdom by the sea,\n"
            + "That a maiden there lived whom you may know\n"
            + "By the name of ANNABEL LEE;\n"
            + "And this maiden she lived with no other thought\n"
            + "Than to love and be loved by me.\n"
            + "I was a child and she was a child,\n"
            + "In this kingdom by the sea;\n"
            + "But we loved with a love that was more than love-\n"
            + "I and my Annabel Lee;\n"
            + "With a love that the winged seraphs of heaven\n"
            + "Coveted her and me.";
  }
```

**Program output:**

```java
the result of expanded(or) query[[many, Annabel]] is [It was many and many a year ago,, By the name of ANNABEL LEE;, I and my Annabel Lee;]
the result of specialized(and) query[[Annabel, my]] is [I and my Annabel Lee;]
the result of advanced query is [It was many and many a year ago,]
the result of filtered query is [But we loved with a love that was more than love-]
```

Now we can design our app to with the queries finding feature `expandedFinder`, `specializedFinder`, `advancedFinder`, `filteredFinder` which are all derived from `contains`, `or`, `not`, `and`.


## Class diagram
![alt text](./etc/combinator.urm.png "Combinator class diagram")

## Applicability
Use the combinator pattern when:

- You are able to create a more complex value from more plain values but having the same type(a combination of them)

## Benefits

- From a developers perspective the API is made of terms from the domain.
- There is a clear distinction between combining and application phase.
- One first constructs an instance and then executes it.
- This makes the pattern applicable in a parallel environment.


## Real world examples

- java.util.function.Function#compose
- java.util.function.Function#andThen

## Credits

- [Example for java](https://gtrefs.github.io/code/combinator-pattern/)
- [Combinator pattern](https://wiki.haskell.org/Combinator_pattern)
- [Combinatory logic](https://wiki.haskell.org/Combinatory_logic)
