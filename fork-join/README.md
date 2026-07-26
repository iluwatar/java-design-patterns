---
title: "Fork/Join Pattern in Java: Parallel Divide-and-Conquer Processing"
shortTitle: Fork/Join
description: "Learn the Fork/Join design pattern in Java with real-world examples, class diagrams, and code samples. Understand how to split large tasks into parallel subtasks for improved performance."
category: Concurrency
language: en
tag:
  - Performance
  - Scalability
  - Concurrency
---

## Also known as

* Divide and Conquer Parallelism
* Work-Stealing Parallelism

## Intent of Fork/Join Design Pattern

The Fork/Join pattern recursively splits a large task into independent subtasks (fork),
processes them in parallel across multiple threads, and combines their results (join) to
produce a final outcome. It maximizes CPU utilization for computationally intensive problems.

## Detailed Explanation of Fork/Join Pattern with Real-World Examples

Real-world example

> Imagine a large warehouse that needs to count all its inventory items across 100 aisles.
> Instead of one person counting every aisle sequentially, the manager divides the warehouse
> into sections and assigns a team of workers to count each section simultaneously. Once
> every section is counted, the manager collects all partial counts and sums them into the
> total inventory. This is the Fork/Join pattern: split the work, do it in parallel, merge
> the results.

In plain words

> Fork/Join splits a big problem into smaller pieces, solves each piece in parallel on
> separate threads, then combines all results back together.

## Programmatic Example of Fork/Join Pattern in Java

We demonstrate the pattern by computing the sum of a large array in parallel using Java's
built-in `ForkJoinPool` and `RecursiveTask`.

The `SumTask` is a recursive task that splits the array when it's too large:

```java
public class SumTask extends RecursiveTask<Long> {

  private static final int THRESHOLD = 1000;
  private final long[] numbers;
  private final int start;
  private final int end;

  @Override
  protected Long compute() {
    int length = end - start;

    if (length <= THRESHOLD) {
      // Base case: sum directly
      long sum = 0;
      for (int i = start; i < end; i++) {
        sum += numbers[i];
      }
      return sum;
    }

    // Fork: split into two halves
    int mid = start + length / 2;
    SumTask leftTask = new SumTask(numbers, start, mid);
    SumTask rightTask = new SumTask(numbers, mid, end);

    leftTask.fork();                    // run left half asynchronously
    long rightResult = rightTask.compute();  // compute right half here
    long leftResult = leftTask.join();       // wait for left half

    // Join: combine results
    return leftResult + rightResult;
  }
}
```

The `ForkJoinSumCalculator` provides a clean API:

```java
public class ForkJoinSumCalculator {

  private final ForkJoinPool pool;

  public ForkJoinSumCalculator() {
    this.pool = ForkJoinPool.commonPool();
  }

  public long calculateSum(long[] numbers) {
    SumTask task = new SumTask(numbers, 0, numbers.length);
    return pool.invoke(task);
  }
}
```

Running the example in `App`:

```java
long[] numbers = LongStream.rangeClosed(1, 10_000_000).toArray();
ForkJoinSumCalculator calculator = new ForkJoinSumCalculator();
long result = calculator.calculateSum(numbers);
System.out.println("Fork/Join sum: " + result);
```

Program output:

```
Fork/Join sum: 50000005000000
Expected sum:  50000005000000
Correct: true
Time taken: 45 ms
Available processors: 8
```

## When to Use the Fork/Join Pattern in Java

* When you have a large, CPU-intensive task that can be divided into independent subtasks.
* When the subtasks are roughly the same size and don't depend on each other.
* When you want to utilize multiple CPU cores without manually managing threads.
* When the problem naturally fits a divide-and-conquer strategy (e.g., sorting, searching,
  numerical computation).

## When NOT to Use Fork/Join

* For I/O-bound tasks (network calls, file reads) — use virtual threads or async I/O instead.
* When subtasks are too small — the overhead of forking exceeds the benefit.
* When tasks have dependencies on each other and cannot run independently.

## Benefits and Trade-offs of Fork/Join Pattern

Benefits:

* Maximizes CPU utilization through work-stealing algorithm.
* Scales automatically with the number of available processors.
* Built into Java's standard library (`java.util.concurrent`) — no external dependencies.
* Clean recursive decomposition makes the code readable and maintainable.

Trade-offs:

* Overhead from task creation and thread management for very small problems.
* Requires tasks to be independent — shared mutable state introduces bugs.
* Choosing an appropriate threshold requires tuning for optimal performance.
* Debugging parallel code is inherently harder than sequential code.

## Related Java Design Patterns

* [Divide and Conquer](https://java-design-patterns.com/patterns/divide-and-conquer/):
  Fork/Join is the parallel execution variant of the classic divide-and-conquer strategy.
* [Thread Pool](https://java-design-patterns.com/patterns/thread-pool/): Fork/Join uses a
  specialized pool with work-stealing semantics.

## References

* [Java Documentation for ForkJoinPool](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/ForkJoinPool.html)
* [Java Concurrency in Practice — Brian Goetz](https://amzn.to/4aRMruW)
* [Java Documentation for RecursiveTask](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/RecursiveTask.html)
