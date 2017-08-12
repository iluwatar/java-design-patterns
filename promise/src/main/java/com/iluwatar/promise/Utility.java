/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Utility to perform various operations
 */
public class Utility {

  private static final Logger LOGGER = LoggerFactory.getLogger(Utility.class);

  /**
   * Calculates character frequency of the file provided.
   * @param fileLocation location of the file.
   * @return a map of character to its frequency, an empty map if file does not exist.
   */
  public static Map<Character, Integer> characterFrequency(String fileLocation) {
    Map<Character, Integer> characterToFrequency = new HashMap<>();
    try (Reader reader = new FileReader(fileLocation);
        BufferedReader bufferedReader = new BufferedReader(reader)) {
      for (String line; (line = bufferedReader.readLine()) != null;) {
        for (char c : line.toCharArray()) {
          if (!characterToFrequency.containsKey(c)) {
            characterToFrequency.put(c, 1);
          } else {
            characterToFrequency.put(c, characterToFrequency.get(c) + 1);
          }
        }
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return characterToFrequency;
  }

  /**
   * @return the character with lowest frequency if it exists, {@code Optional.empty()} otherwise.
   */
  public static Character lowestFrequencyChar(Map<Character, Integer> charFrequency) {
    Character lowestFrequencyChar = null;
    Iterator<Entry<Character, Integer>> iterator = charFrequency.entrySet().iterator();
    Entry<Character, Integer> entry = iterator.next();
    int minFrequency = entry.getValue();
    lowestFrequencyChar = entry.getKey();

    while (iterator.hasNext()) {
      entry = iterator.next();
      if (entry.getValue() < minFrequency) {
        minFrequency = entry.getValue();
        lowestFrequencyChar = entry.getKey();
      }
    }

    return lowestFrequencyChar;
  }

  /**
   * @return number of lines in the file at provided location. 0 if file does not exist.
   */
  public static Integer countLines(String fileLocation) {
    int lineCount = 0;
    try (Reader reader = new FileReader(fileLocation);
        BufferedReader bufferedReader = new BufferedReader(reader)) {
      while (bufferedReader.readLine() != null) {
        lineCount++;
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return lineCount;
  }

  /**
   * Downloads the contents from the given urlString, and stores it in a temporary directory.
   * @return the absolute path of the file downloaded.
   */
  public static String downloadFile(String urlString) throws MalformedURLException, IOException {
    LOGGER.info("Downloading contents from url: {}", urlString);
    URL url = new URL(urlString);
    File file = File.createTempFile("promise_pattern", null);
    try (Reader reader = new InputStreamReader(url.openStream());
        BufferedReader bufferedReader = new BufferedReader(reader);
        FileWriter writer = new FileWriter(file)) {
      for (String line; (line = bufferedReader.readLine()) != null; ) {
        writer.write(line);
        writer.write("\n");
      }
      LOGGER.info("File downloaded at: {}", file.getAbsolutePath());
      return file.getAbsolutePath();
    } catch (IOException ex) {
      throw ex;
    }
  }
}
