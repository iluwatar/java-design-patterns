---
title: Collection Pipeline
category: Functional
language: en
tag:
 - Reactive
---

## Intent
Collection Pipeline introduces Function Composition and Collection Pipeline, two functional-style patterns that you can combine to iterate collections in your code. 
In functional programming, it's common to sequence complex operations through a series of smaller modular functions or operations. The series is called a composition of functions, or a function composition. When a collection of data flows through a function composition, it becomes a collection pipeline. Function Composition and Collection Pipeline are two design patterns frequently used in functional-style programming.

## Explanation 
Real-world example:

Imagine you're managing a library with a vast collection of books. You need to find all the books published in the last year, sort them by author's last name, and then display their titles. This is a real-world scenario where the Collection Pipeline pattern can be applied. Each step in the process (filtering, sorting, and displaying) can be seen as a stage in the pipeline.

In plain words:

The Collection Pipeline pattern is like an assembly line for data processing. It breaks down a complex operation into a series of smaller, easier-to-handle steps. Imagine an assembly line in a car factory; each station does a specific job before passing the product to the next station. Similarly, in code, data flows through a sequence of operations, with each operation transforming or filtering it, creating a "pipeline" of data processing.

Wikipedia says:

The [Collection Pipeline pattern](https://en.wikipedia.org/wiki/Pipeline_(software)) is a software design pattern that allows you to process data in a sequence of stages. Each stage performs a specific operation on the data and passes it to the next stage. This pattern is commonly used in functional programming to create readable and maintainable code for data manipulation.

Programmatic example:

```python
# Sample list of books with title, author, and publication year
books = [
    {"title": "Book A", "author": "John Smith", "year": 2022},
    {"title": "Book B", "author": "Alice Johnson", "year": 2021},
    {"title": "Book C", "author": "David Brown", "year": 2022},
    # ... (more books)
]

# Collection Pipeline: Find books published in 2022, sort by author's last name, and display titles
result = (
    filter(lambda book: book["year"] == 2022, books)
    |> sorted(key=lambda book: book["author"].split()[-1])
    |> map(lambda book: book["title"])
)

# Display the result
print(list(result))

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
