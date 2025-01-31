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

## Also known as

* Split-Apply-Combine Strategy
* Scatter-Gather Pattern

## Intent of Map Reduce Design Pattern

MapReduce aims to process and generate large datasets with a parallel, distributed algorithm on a cluster. It divides the workload into two main phases: Map and Reduce, allowing for efficient parallel processing of data.

## Detailed Explanation of Map Reduce Pattern with Real-World Examples

Real-world example

> Imagine a large e-commerce company that wants to analyze its sales data across multiple regions. They have terabytes of transaction data stored across hundreds of servers. Using MapReduce, they can efficiently process this data to calculate total sales by product category. The Map function would process individual sales records, emitting key-value pairs of (category, sale amount). The Reduce function would then sum up all sale amounts for each category, producing the final result.

In plain words

> MapReduce splits a large problem into smaller parts, processes them in parallel, and then combines the results.

Wikipedia says

> "MapReduce is a programming model and associated implementation for processing and generating big data sets with a parallel, distributed algorithm on a cluster".
MapReduce consists of two main steps:
The "Map" step: The master node takes the input, divides it into smaller sub-problems, and distributes them to worker nodes. A worker node may do this again in turn, leading to a multi-level tree structure. The worker node processes the smaller problem, and passes the answer back to its master node.
The "Reduce" step: The master node then collects the answers to all the sub-problems and combines them in some way to form the output – the answer to the problem it was originally trying to solve.
This approach allows for efficient processing of vast amounts of data across multiple machines, making it a fundamental technique in big data analytics and distributed computing.

## Programmatic Example of Map Reduce in Java

### 1. Map Phase (Splitting & Processing Data)

* The Mapper takes an input string, splits it into words, and counts occurrences.
* Output: A map {word → count} for each input line.
#### `Mapper.java`
```java
public class Mapper {
    public static Map<String, Integer> map(String input) {
        Map<String, Integer> wordCount = new HashMap<>();
        String[] words = input.split("\\s+");
        for (String word : words) {
            word = word.toLowerCase().replaceAll("[^a-z]", "");
            if (!word.isEmpty()) {
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        }
        return wordCount;
    }
}
```
Example Input: ```"Hello world hello"```
Output: ```{hello=2, world=1}```

### 2. Shuffle Phase (Grouping Data by Key)

* The Shuffler collects key-value pairs from multiple mappers and groups values by key.
#### `Shuffler.java`
```java
public class Shuffler {
    public static Map<String, List<Integer>> shuffleAndSort(List<Map<String, Integer>> mapped) {
        Map<String, List<Integer>> grouped = new HashMap<>();
        for (Map<String, Integer> map : mapped) {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                grouped.putIfAbsent(entry.getKey(), new ArrayList<>());
                grouped.get(entry.getKey()).add(entry.getValue());
            }
        }
        return grouped;
    }
}
```
Example Input: 
```
[
    {"hello": 2, "world": 1},
    {"hello": 1, "java": 1}
]
```
Output: 
```
{
    "hello": [2, 1],
    "world": [1],
    "java": [1]
}
```

### 3. Reduce Phase (Aggregating Results)

* The Reducer sums up occurrences of each word.
#### `Reducer.java`
```java
public class Reducer {
    public static List<Map.Entry<String, Integer>> reduce(Map<String, List<Integer>> grouped) {
        Map<String, Integer> reduced = new HashMap<>();
        for (Map.Entry<String, List<Integer>> entry : grouped.entrySet()) {
            reduced.put(entry.getKey(), entry.getValue().stream().mapToInt(Integer::intValue).sum());
        }

        List<Map.Entry<String, Integer>> result = new ArrayList<>(reduced.entrySet());
        result.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return result;
    }
}
```
Example Input:
```
{
    "hello": [2, 1],
    "world": [1],
    "java": [1]
}
```
Output:
```
[
    {"hello": 3},
    {"world": 1},
    {"java": 1}
]
```

### 4. Running the Full MapReduce Process

* The MapReduce class coordinates the three steps.
#### `MapReduce.java`
```java
public class MapReduce {
    public static List<Map.Entry<String, Integer>> mapReduce(List<String> inputs) {
        List<Map<String, Integer>> mapped = new ArrayList<>();
        for (String input : inputs) {
            mapped.add(Mapper.map(input));
        }

        Map<String, List<Integer>> grouped = Shuffler.shuffleAndSort(mapped);

        return Reducer.reduce(grouped);
    }
}
```

### 4. Main Execution (Calling MapReduce)

* The Main class executes the MapReduce pipeline and prints the final word count.
#### `Main.java`
```java
  public static void main(String[] args) {
    List<String> inputs = Arrays.asList(
            "Hello world hello",
            "MapReduce is fun",
            "Hello from the other side",
            "Hello world"
    );
    List<Map.Entry<String, Integer>> result = MapReduce.mapReduce(inputs);
    for (Map.Entry<String, Integer> entry : result) {
        System.out.println(entry.getKey() + ": " + entry.getValue());
    }
}
```

Output:
```
hello: 4
world: 2
the: 1
other: 1
side: 1
mapreduce: 1
is: 1
from: 1
fun: 1
```

## When to Use the Map Reduce Pattern in Java

Use MapReduce when:
* Processing large datasets that don't fit into a single machine's memory
* Performing computations that can be parallelized
* Dealing with fault-tolerant and distributed computing scenarios
* Analyzing log files, web crawl data, or scientific data

## Map Reduce Pattern Java Tutorials

* [MapReduce Tutorial(Apache Hadoop)](https://hadoop.apache.org/docs/stable/hadoop-mapreduce-client/hadoop-mapreduce-client-core/MapReduceTutorial.html)
* [MapReduce Example(Simplilearn)](https://www.youtube.com/watch?v=l2clwKnrtO8)

## Benefits and Trade-offs of Map Reduce Pattern

Benefits:

* Scalability: Can process vast amounts of data across multiple machines
* Fault-tolerance: Handles machine failures gracefully
* Simplicity: Abstracts complex distributed computing details

Trade-offs:

* Overhead: Not efficient for small datasets due to setup and coordination costs
* Limited flexibility: Not suitable for all types of computations or algorithms
* Latency: Batch-oriented nature may not be suitable for real-time processing needs

## Real-World Applications of Map Reduce Pattern in Java

* Google's original implementation for indexing web pages
* Hadoop MapReduce for big data processing
* Log analysis in large-scale systems
* Genomic sequence analysis in bioinformatics

## Related Java Design Patterns

* Chaining Pattern
* Master-Worker Pattern
* Pipeline Pattern

## References and Credits

* [What is MapReduce](https://www.ibm.com/think/topics/mapreduce)
* [Wy MapReduce is not dead](https://www.codemotion.com/magazine/ai-ml/big-data/mapreduce-not-dead-heres-why-its-still-ruling-in-the-cloud/)
* [Scalabe Distributed Data Processing Solutions](https://tcpp.cs.gsu.edu/curriculum/?q=system%2Ffiles%2Fch07.pdf)
* [Java Design Patterns: A Hands-On Experience with Real-World Examples](https://amzn.to/3HWNf4U)
