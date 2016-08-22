package com.iluwatar.promise;

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
import java.util.Optional;
import java.util.Map.Entry;

public class Utility {

  public static Map<Character, Integer> characterFrequency(String fileLocation) {
    Map<Character, Integer> characterToFrequency = new HashMap<>();
    try (Reader reader = new FileReader(fileLocation); 
        BufferedReader bufferedReader = new BufferedReader(reader);) {
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
  
  public static Optional<Character> lowestFrequencyChar(Map<Character, Integer> charFrequency) {
    Optional<Character> lowestFrequencyChar = Optional.empty();
    if (charFrequency.isEmpty()) {
      return lowestFrequencyChar;
    }
    
    Iterator<Entry<Character, Integer>> iterator = charFrequency.entrySet().iterator();
    Entry<Character, Integer> entry = iterator.next();
    int minFrequency = entry.getValue();
    lowestFrequencyChar = Optional.of(entry.getKey());
    
    while (iterator.hasNext()) {
      entry = iterator.next();
      if (entry.getValue() < minFrequency) {
        minFrequency = entry.getValue();
        lowestFrequencyChar = Optional.of(entry.getKey());
      }
    }
    
    return lowestFrequencyChar;
  }
  
  public static Integer countLines(String fileLocation) {
    int lineCount = 0;
    try (Reader reader = new FileReader(fileLocation); 
        BufferedReader bufferedReader = new BufferedReader(reader);) {
      while (bufferedReader.readLine() != null) {
        lineCount++;
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return lineCount;
  }

  public static String downloadFile(String urlString) throws MalformedURLException, IOException {
    System.out.println("Downloading contents from url: " + urlString);
    URL url = new URL(urlString);
    File file = File.createTempFile("promise_pattern", null);
    try (Reader reader = new InputStreamReader(url.openStream()); 
        BufferedReader bufferedReader = new BufferedReader(reader);
        FileWriter writer = new FileWriter(file)) {
      for (String line; (line = bufferedReader.readLine()) != null; ) {
        writer.write(line);
        writer.write("\n");
      }
      System.out.println("File downloaded at: " + file.getAbsolutePath());
      return file.getAbsolutePath();
    } catch (IOException ex) {
      throw ex;
    }
  }
}
