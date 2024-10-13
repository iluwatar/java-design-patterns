---
title: "MapReduce Pattern in Java"
shortTitle: MapReduce
description: "Learn the MapReduce pattern in Java with real-world examples, class diagrams, and tutorials. Understand its intent, applicability, benefits, and known uses to enhance your design pattern knowledge."
category: Performance optimization
language: en
tag:
  - Data processing
  - Code simplification
  - Delegation
  - Performance
---

# MapReduce in Java

## Intent of Map Reduce Pattern

The MapReduce design pattern is intended to simplify the processing of large-scale data sets by breaking down complex tasks into smaller, manageable units of work. This pattern leverages parallel processing to increase efficiency, scalability, and fault tolerance across distributed systems. 

## Detailed Explanation of Map Reduce with Real-World Examples

1. Map Phase
   The Map phase is the first step in the process. It takes the input data (often unstructured or semi-structured) and transforms it into intermediate key-value pairs. Each data element is processed independently and converted into one or more key-value pairs.
2.  Reduce Phase
    The Reduce phase aggregates the intermediate key-value pairs produced in the Map phase. All values associated with the same key are passed to the Reduce function, which processes them to produce a final output.

Real-world example

> Imagine you work for a company that processes millions of customer reviews, and you want to find out how often each word is used across all the reviews. Doing this on one machine would take a lot of time, so you want to use a MapReduce process to speed it up by distributing the work across multiple computers.

In plain words

> By abstracting the distribution and coordination of these tasks, MapReduce enables developers to focus on the processing logic, rather than the complexities of managing concurrency

Wikipedia say
> MapReduce is a programming model and an associated implementation for processing and generating big data sets with a parallel, distributed algorithm on a cluster.

## Programmatic Example of DTO Pattern in Java

Let's first start with our map method

```java
public static List<Map.Entry<String, Integer>> map(List<String> sentences) {
    List<Map.Entry<String, Integer>> mapped = new ArrayList<>();
    for (String sentence : sentences) {
        String[] words = sentence.split("\\s+");
        for (String word : words) {
            mapped.add(new AbstractMap.SimpleEntry<>(word.toLowerCase(), 1));
        }
    }
    return mapped;
}
```
The purpose of the Map function is to process the input data and convert it into key-value pairs.

Now lets go with the reduce function:
```java
  public static Map<String, Integer> reduce(List<Map.Entry<String, Integer>> mappedWords) {
    Map<String, Integer> reduced = new HashMap<>();
    for (Map.Entry<String, Integer> entry : mappedWords) {
      reduced.merge(entry.getKey(), entry.getValue(), Integer::sum);
    }
    return reduced;
  }
```
In the Reduce phase, the grouped key-value pairs are processed to combine or summarize the data. The idea is to aggregate all the values for each key to produce the final result.



An example of the console output after running the algorithm with the following input:
input
```java
List<String> sentences = Arrays.asList(
"hello world",
"hello java java",
"map reduce pattern in java",
"world of java map reduce"
);
```
Output
```
reduce: 2
java: 4
world: 2
in: 1
of: 1
pattern: 1
hello: 2
map: 2
```

## When to Use the MapReduce Pattern in Java

Use the MapReduce pattern when:

* You are working with massive datasets that can't fit on a single machine.
* You need a batch-processing solution to analyze or transform large volumes of data.
* Fault tolerance and high availability are important for your use case.

## MapReduce Pattern Java Tutorials

* [MapReduce Algorithm (Baeldung)](https://www.baeldung.com/cs/mapreduce-algorithm)
* [MapReduce Tutorial (javatpoint)](https://www.javatpoint.com/mapreduce)


## Real-World Applications of MapReduce Pattern in Java
MapReduce is a powerful tool for processing large-scale data because it breaks down complex tasks into smaller, manageable parts, allowing for efficient parallel computation across distributed systems

## Benefits of MapReduce pattern

1. Developers only need to define two simple functions: Map and Reduce. They don't have to worry about how tasks are distributed, how failures are handled, or how the data is split.
2. MapReduce scales horizontally. You can add more machines (nodes) to the cluster to handle larger data sets or process more tasks in parallel. This makes it perfect for large datasets in the order of terabytes or petabytes.
3. The system is designed to handle failures. If a worker node crashes or a task fails, it automatically retries the task on another available node without developer intervention.

## Trade-offs of Map Reduce pattern 

1. MapReduce is designed for batch processing, meaning it’s not suitable for real-time data processing or low-latency jobs. The process of splitting data, shuffling it between nodes, and reducing it can introduce significant delays.
2. If the dataset is small or can fit in memory on a single machine, MapReduce can be overkill. The overhead of distributing tasks across nodes outweighs the benefits for small-scale tasks. Single-node solutions like a relational database or in-memory processing tools.
3. MapReduce frequently reads from and writes to disk (between the Map and Reduce phases), leading to performance bottlenecks. This is particularly problematic for jobs with lots of intermediate data.

## References and Credits

* [Designing Data-intensive Applications](https://www.amazon.com/Designing-Data-Intensive-Applications-Reliable-Maintainable/dp/1449373321/ref=sr_1_1?s=books&sr=1-1)
* [MapReduce Design Patterns](https://www.amazon.com/MapReduce-Design-Patterns-Effective-Algorithms/dp/1449327176/ref=sr_1_1?crid=3N6I3219DQBM&dib=eyJ2IjoiMSJ9.v6J5LaH30wtWyGQ7t20oSWIhd3rZs9GOaU3r-fSfZbd11rwjP0d0lL4tdcsD_yMt-WY6-XDWWakgkvMv38W9YD7CZDIgJ1G-LuazC8rNILObJBIRg09-7-ugQHZbtkqZFEt1ZCyFiDV4E3Iq2Db41vOpjbrU_B-phwzNQoRU175m1i-WvzTdcWL5GwVcbIWClmYB99kszZ1wX76nfjfq9YUHAFZtlpvLNMavBY4KTjI.QhcDrdrN5Bdd5ZVRTf9cZw0lAXNX83ncVVws8UbVDKU&dib_tag=se&keywords=MapReduce+Design+Patterns&qid=1728522338&s=books&sprefix=mapreduce+design+patterns%2Cstripbooks-intl-ship%2C198&sr=1-1)