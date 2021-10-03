---
layout: pattern
title: Collection Pipeline
folder: collection-pipeline
permalink: /patterns/collection-pipeline/
categories: Functional
language: en
tags:
 - Reactive
---

## Intent
Collection Pipeline introduces Function Composition and Collection Pipeline, two functional-style patterns that you can combine to iterate collections in your code. 
In functional programming, it's common to sequence complex operations through a series of smaller modular functions or operations. The series is called a composition of functions, or a function composition. When a collection of data flows through a function composition, it becomes a collection pipeline. Function Composition and Collection Pipeline are two design patterns frequently used in functional-style programming.

## Also Known As

Map Filter Reduce Pattern

## Explanation

### Real world example <a name = "real-world-example"></a>

Imagine you have list of persons of ages from 15 years old to 60 years old. You wants to find the average age of persons whose age is in between
25 and 45.

In traditional way you have to cut down the problem statements in to 2 steps.
First you have to find the count of persons in between the given age range
Then you have to find the sum of age of the persons in between the given age range.
Then you have to find the average of age persons in the given range.

This process is very costly in terms code readability, maintainability as well as in terms of CPU and memory usage.
Because you have to create couple of list to process the persons data to solve the above simple problem.

Here is the collection pipeline design pattern comes into the picture. Which help us to solve the problem in single line of code with minimal CPU and memory consumption. Also help us to utilize the cores of CPU effectively in multi core devices by parallelism with the help of stream API.


### In  Plain Words

Through the collection pipeline design pattern you can organize some computation as a sequence of operations which compose by taking a collection/stream and output to the input of next operation and so on.

The name itself reveals what it does!
Collection Pipeline - Passing collection over a pipeline to process.
The pipeline concept is already there in unix type system there we pass a data to multiple
commands to process over a pipe or simply we are chaining multiple process in a certain order to process sequentially
through pipes.

Let's take a look at simple unix pipe example.
Here we are searching for the occurrence of a word '*foo*' in '*MultipleFoo.txt*'

```bash
    $ grep 'MultipleFoo.txt' foo | xargs wc -l
```
Here first we are finding the occurrence of the word '*foo*' using `grep` command. Then the output of the `grep` command is passed through the input of the `wc -l` command through pipe ('|') and get the count in the console.

### Wikipedia Says

> In software engineering, a pipeline consists of a chain of processing elements (processes, threads, coroutines, functions, etc.),
> arranged so that the output of each element is the input of the next


## Programmatic Example

Let's solve the [real world example](#real-world-example) using java code here.
PS: Here we are using streams to process the data in collection. So some knowledge in Stream API and
lambda expression is preferred.

The stream API provides rich functionalities to process and reduce the collections.
Also as we know stream object is empty it doesn't cribble system memory.

Let's create a class of person.
```java
public class Person
{
    private String name;
    private int age;
    // Constructor
    // Getters & Setters
}
```

Let's create a List of persons.
```java
public class Main
{
    public static void main(String... args)
    {
        Person john = new Person("John", 25);
        Person danny = new Person("Danny", 38);
        Person lakshmi = new Person("Lakshmi", 45);
        Person kevin = new Person("Kevin",32);
        Person mary = new Person("30", "Mary");
        Person sara = new Person("Sara", 18);
        Person angel = new Person("Angel",29);

        List<Person> people = List.of(john, danny, lakshmi, kevin, mary, sara, angel);
    }
}
```

Let's get the List of ages from people list.
```java
public class Main
{
    public static void main(String... args)
    {
        Person john = new Person("John", 25);
        Person danny = new Person("Danny", 38);
        Person lakshmi = new Person("Lakshmi", 45);
        Person kevin = new Person("Kevin",32);
        Person mary = new Person("30", "Mary");
        Person sara = new Person("Sara", 18);
        Person angel = new Person("Angel",29);

        List<Person> people = List.of(john, danny, lakshmi, kevin, mary, sara, angel);

        List<Integer> ages = people.stream()
                                .map(person -> person.getAge())
                                .collect(Collectors.toList());

    }
}
```

Let's filter the ages list by removing the ages which is not in the range 25 - 45.
```java
public class Main
{
    public static void main(String... args)
    {
        Person john = new Person("John", 25);
        Person danny = new Person("Danny", 38);
        Person lakshmi = new Person("Lakshmi", 45);
        Person kevin = new Person("Kevin",32);
        Person mary = new Person("30", "Mary");
        Person sara = new Person("Sara", 18);
        Person angel = new Person("Angel",29);

        List<Person> people = List.of(john, danny, lakshmi, kevin, mary, sara, angel);

        List<Integer> ages = people.stream()
                                .map(person -> person.getAge())
                                .collect(Collectors.toList());

        List<Integer> agesAfterFilter = ages.stream()
                                .filter(age -> age > 25 && age < 45)
                                .collect(Collectors.toList());
    }
}
```

Let's calculate the average age of people whose age is between 25 and 45.
```java
public class Main
{
    public static void main(String... args)
    {
        Person john = new Person("John", 25);
        Person danny = new Person("Danny", 38);
        Person lakshmi = new Person("Lakshmi", 45);
        Person kevin = new Person("Kevin",32);
        Person mary = new Person("30", "Mary");
        Person sara = new Person("Sara", 18);
        Person angel = new Person("Angel",29);

        List<Person> people = List.of(john, danny, lakshmi, kevin, mary, sara, angel);

        List<Integer> ages = people.stream()
                                .map(person -> person.getAge())
                                .collect(Collectors.toList());

        List<Integer> agesAfterFilter = ages.stream()
                                    .filter(age -> age > 25 && age < 45)
                                    .collect(Collectors.toList());

        Integer average = agesAfterFilter.stream()
                                .average();
    }
}
```

Let's chain the all the intermediate map, filter, reduce process.
This chaining is called collection pipeline programming pattern
```java
public class Main
{
    public static void main(String... args)
    {
        Person john = new Person("John", 25);
        Person danny = new Person("Danny", 38);
        Person lakshmi = new Person("Lakshmi", 45);
        Person kevin = new Person("Kevin",32);
        Person mary = new Person("30", "Mary");
        Person sara = new Person("Sara", 18);
        Person angel = new Person("Angel",29);

        List<Person> people = List.of(john, danny, lakshmi, kevin, mary, sara, angel);

        Integer average = people.stream()
                            .map(person -> person.getAge())  
                            .filter(age -> age > 25 && age < 45)
                            .average();

    }
}
```
## Class diagram
![alt text](./etc/collection-pipeline.png "Collection Pipeline")

## Applicability
Use the Collection Pipeline pattern when

* When you want to perform a sequence of operations where one operation's collected output is fed into the next
* When you use a lot of statements in your code
* When you use a lot of loops in your code

## Credits

* [Function composition and the Collection Pipeline pattern](https://www.ibm.com/developerworks/library/j-java8idioms2/index.html)
* [Martin Fowler](https://martinfowler.com/articles/collection-pipeline/)
* [Java8 Streams](https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html)
