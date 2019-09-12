/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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
