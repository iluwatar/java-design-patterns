---
title: Master-Worker
category: Concurrency
language: en
tag:
 - Performance
---

## Also known as

> Master-slave or Map-reduce

## Intent

> Used for centralised parallel processing.

## Class diagram
![alt text](./etc/master-worker-pattern.urm.png "Master-Worker pattern class diagram")

## Explanation

Real World Example
>Imagine you have a large stack of papers to grade as a teacher. You decide to hire several teaching assistants to help you. You, as the "master," distribute different papers to each teaching assistant, they grade them independently, and return the graded papers to you. Finally, you collect all the graded papers, review them, and calculate the final grades. This process of dividing work among assistants, parallel processing, and aggregating results is similar to the Master-Worker pattern.

In Plain Words
>The Master-Worker pattern is like a boss (the master) assigning tasks to workers and then combining the results. It's a way to efficiently break down a big job into smaller pieces that can be done concurrently.

Wikipedia Says
>According to [Wikipedia](https://en.wikipedia.org/wiki/Master/slave_(technology)), the Master-Worker pattern, also known as Master-Slave or Map-Reduce, is a design pattern used in software engineering for parallel processing. In this pattern, a "master" component divides a complex task into smaller subtasks and assigns them to multiple "worker" components. Each worker processes its assigned subtask independently, and the master collects and combines the results to produce the final output.

Programmatic Example

```java
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

// Define a task that workers (teaching assistants) will perform
class GradingWorkerTask implements Callable<Integer> {
    private int paperNumber;

    public GradingWorkerTask(int paperNumber) {
        this.paperNumber = paperNumber;
    }

    @Override
    public Integer call() throws Exception {
        // Simulate grading a paper (e.g., assigning a score)
        int score = (int) (Math.random() * 100); // Assign a random score
        // Simulate some grading time
        Thread.sleep((int) (Math.random() * 1000));
        System.out.println("Graded paper #" + paperNumber + " with a score of " + score);
        return score;
    }
}
```

```java
public class PaperGrading {
    public static void main(String[] args) {
        int totalPapersToGrade = 20; // Total number of papers to grade
        int numberOfTeachingAssistants = 3; // Number of teaching assistants

        // Create a thread pool with a fixed number of teaching assistants (workers)
        ExecutorService executor = Executors.newFixedThreadPool(numberOfTeachingAssistants);

        // Create and submit grading tasks to the executor for each paper
        List<Future<Integer>> results = new ArrayList<>();
        for (int paperNumber = 1; paperNumber <= totalPapersToGrade; paperNumber++) {
            results.add(executor.submit(new GradingWorkerTask(paperNumber)));
        }

        // Shutdown the executor to prevent new tasks from being submitted
        executor.shutdown();

        // Collect and analyze the grading results
        int totalScore = 0;
        for (Future<Integer> result : results) {
            try {
                // Get the score of each graded paper and calculate the total score
                totalScore += result.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        double averageScore = (double) totalScore / totalPapersToGrade;

        System.out.println("Grading completed.");
        System.out.println("Total papers graded: " + totalPapersToGrade);
        System.out.println("Total score: " + totalScore);
        System.out.println("Average score: " + averageScore);
    }
}
```

## Applicability
This pattern can be used when data can be divided into multiple parts, all of which need to go through the same computation to give a result, which need to be aggregated to get the final result.

## Explanation
In this pattern, parallel processing is performed using a system consisting of a master and some number of workers, where a master divides the work among the workers, gets the result back from them and assimilates all the results to give final result. The only communication is between the master and the worker - none of the workers communicate among one another and the user only communicates with the master to get the required job done. The master has to maintain a record of how the divided data has been distributed, how many workers have finished their work and returned a result, and the results themselves to be able to aggregate the data correctly.

## Credits

* [Master-Worker Pattern](https://docs.gigaspaces.com/sbp/master-worker-pattern.html)
* [The Master-Slave Design Pattern](https://www.cs.sjsu.edu/~pearce/oom/patterns/behavioral/masterslave.htm)
