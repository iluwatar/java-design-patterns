/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
package com.iluwatar.model.view.presenter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serial;
import java.io.Serializable;
import java.util.stream.Collectors;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Every instance of this class represents the Model component in the Model-View-Presenter
 * architectural pattern.
 *
 * <p>It is responsible for reading and loading the contents of a given file.
 */
@Getter
public class FileLoader implements Serializable {

  /**
   * Generated serial version UID.
   */
  @Serial
  private static final long serialVersionUID = -4745803872902019069L;

  private static final Logger LOGGER = LoggerFactory.getLogger(FileLoader.class);

  /**
   * Indicates if the file is loaded or not.
   */
  private boolean loaded;

  /**
   * The name of the file that we want to load.
   */
  private String fileName;

  /**
   * Loads the data of the file specified.
   */
  public String loadData() {
    var dataFileName = this.fileName;
    try (var br = new BufferedReader(new FileReader(dataFileName))) {
      var result = br.lines().collect(Collectors.joining("\n"));
      this.loaded = true;
      return result;
    } catch (Exception e) {
      LOGGER.error("File {} does not exist", dataFileName);
    }

    return null;
  }

  /**
   * Sets the path of the file to be loaded, to the given value.
   *
   * @param fileName The path of the file to be loaded.
   */
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  /**
   * Returns true if the given file exists.
   *
   * @return True, if the file given exists, false otherwise.
   */
  public boolean fileExists() {
    return new File(this.fileName).exists();
  }
}
