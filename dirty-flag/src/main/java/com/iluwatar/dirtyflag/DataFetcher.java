package com.iluwatar.dirtyflag;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A mock database manager -- Fetches data from a raw file.
 * 
 * @author swaisuan
 *
 */
public class DataFetcher {

  private final String filename = "world.txt";
  private long lastFetched;

  public DataFetcher() {
    this.lastFetched = -1;
  }

  private boolean isDirty(long fileLastModified) {
    if (lastFetched != fileLastModified) {
      lastFetched = fileLastModified;
      return true;
    }
    return false;
  }

  /**
   * Fetches data/content from raw file.
   * 
   * @return List of strings
   */
  public List<String> fetch() {
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource(filename).getFile());

    if (isDirty(file.lastModified())) {
      System.out.println(filename + " is dirty! Re-fetching file content...");

      List<String> data = new ArrayList<String>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = br.readLine()) != null) {
          data.add(line);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      return data;
    }

    return new ArrayList<String>();
  }
}
