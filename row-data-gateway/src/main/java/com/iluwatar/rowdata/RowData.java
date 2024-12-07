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
package com.iluwatar.rowdata;

import lombok.Getter;
import lombok.Setter;

/**
 * The RowData class represents a single row of data in the database.
 * It contains properties like ID, Name, and Value, along with getters and setters for each property.
 */
@Getter
@Setter
public class RowData {

  private int id;
  private String name;
  private int value;

  /**
   * Constructor to initialize RowData object with the specified values.
   *
   * @param id    the ID of the row
   * @param name  the name of the row
   * @param value the value of the row
   */
  public RowData(int id, String name, int value) {
    this.id = id;
    this.name = name;
    this.value = value;
  }

  /**
   * Gets the current name of the RowData.
   *
   * @return the name of the row
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the RowData.
   *
   * @param name the new name of the row
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the current value of the RowData.
   *
   * @return the value of the row
   */
  public int getValue() {
    return value;
  }

  /**
   * Sets the value of the RowData.
   *
   * @param value the new value of the row
   */
  public void setValue(int value) {
    this.value = value;
  }

  /**
   * Gets the current ID of the RowData.
   *
   * @return the ID of the row
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the ID of the RowData.
   *
   * @param id the new ID of the row
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Compares two RowData objects for equality.
   * Rows are considered equal if their ID, name, and value are identical.
   *
   * @param obj the object to compare this RowData to
   * @return true if the RowData objects are equal, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    RowData rowData = (RowData) obj;
    return id == rowData.id && value == rowData.value && name.equals(rowData.name);
  }

  /**
   * Returns a hash code for the RowData object, based on its ID, name, and value.
   *
   * @return the hash code for the RowData
   */
  @Override
  public int hashCode() {
    int result = Integer.hashCode(id);
    result = 31 * result + name.hashCode();
    result = 31 * result + Integer.hashCode(value);
    return result;
  }
}
