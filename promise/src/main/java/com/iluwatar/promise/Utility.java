/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.promise;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility to perform various operations.
 */
@Slf4j
public class Utility {

  /**
   * Calculates character frequency of the file provided.
   *
   * @param fileLocation location of the file.
   * @return a map of character to its frequency, an empty map if file does not exist.
   */
  public static Map<Character, Long> characterFrequency(String fileLocation) {
    try (var bufferedReader = new BufferedReader(new FileReader(fileLocation))) {
      return bufferedReader.lines()
          .flatMapToInt(String::chars)
          .mapToObj(x -> (char) x)
          .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return Collections.emptyMap();
  }

  /**
   * Return the character with the lowest frequency, if exists.
   *
   * @return the character, {@code Optional.empty()} otherwise.
   */
  public static Character lowestFrequencyChar(Map<Character, Long> charFrequency) {
    return charFrequency
        .entrySet()
        .stream()
        .min(Comparator.comparingLong(Entry::getValue))
        .map(Entry::getKey)
        .orElseThrow();
  }

  /**
   * Count the number of lines in a file.
   *
   * @return number of lines, 0 if file does not exist.
   */
  public static Integer countLines(String fileLocation) {
    try (var bufferedReader = new BufferedReader(new FileReader(fileLocation))) {
      return (int) bufferedReader.lines().count();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return 0;
  }

  /**
   * Downloads the contents from the given urlString, and stores it in a temporary directory.
   *
   * @return the absolute path of the file downloaded.
   */
  public static String downloadFile(String urlString) throws IOException {
    LOGGER.info("Downloading contents from url: {}", urlString);
    var url = new URL(urlString);
    var file = File.createTempFile("promise_pattern", null);
    try (var bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
         var writer = new FileWriter(file)) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        writer.write(line);
        writer.write("\n");
      }
      LOGGER.info("File downloaded at: {}", file.getAbsolutePath());
      return file.getAbsolutePath();
    }
  }
}
