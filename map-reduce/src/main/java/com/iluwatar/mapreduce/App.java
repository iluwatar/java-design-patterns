package com.iluwatar.mapreduce;

import lombok.extern.slf4j.Slf4j;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The main intent of the MapReduce design pattern is to allow for the processing of large data sets with a distributed algorithm,
 * minimizing the overall time of computation by exploiting various parallel computing nodes. This design pattern simplifies the complexity
 * of concurrency and hides the details of data distribution,fault tolerance, and load balancing, making it an effective model for
 * processing vast amounts of data.
 */
@Slf4j
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {

    // Sample input: List of sentences
    List<String> sentences = Arrays.asList(
        "hello world",
        "hello java java",
        "map reduce pattern in java",
        "world of java map reduce"
    );

    // Step 1: Map phase
    List<Map.Entry<String, Integer>> mappedWords = map(sentences);

    // Step 2: Reduce phase
    Map<String, Integer> wordCounts = reduce(mappedWords);

    // Step 3: Output the final result
    wordCounts.forEach((word, count) -> LOGGER.info("{}: {}", word, count));
  }

  /**
   * The map function processes a list of input data and produces key-value pairs.
   *
   * @param sentences The input data to be processed by the map function.
   * @return A List of maps entries containing keys (e.g., words) and their occurrences.
   */
  public static List<Map.Entry<String, Integer>> map(List<String> sentences) {
    List<Map.Entry<String, Integer>> mapped = new ArrayList<>();
    for (String sentence : sentences) {
      // Split the sentence into words using whitespace as a delimiter
      String[] words = sentence.split("\\s+");
      for (String word : words) {
        // Create a key-value pair where the key is the word and the value is 1
        mapped.add(new AbstractMap.SimpleEntry<>(word.toLowerCase(), 1));
      }
    }
    return mapped;
  }

  /**
   * The reduce function processes the grouped data and aggregates the values
   * (e.g., sums up the occurrences for each word).
   *
   * @param mappedWords A List of maps where each key has a list of associated values.
   * @return A final map with each key and its aggregated result.
   */
  public static Map<String, Integer> reduce(List<Map.Entry<String, Integer>> mappedWords) {
    Map<String, Integer> reduced = new HashMap<>();
    for (Map.Entry<String, Integer> entry : mappedWords) {
      // If the word is already in the map, increment the count, otherwise set it to 1
      reduced.merge(entry.getKey(), entry.getValue(), Integer::sum);
    }
    return reduced;
  }

}