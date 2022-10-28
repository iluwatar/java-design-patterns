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

package com.iluwatar.parameter.object;

public class ParameterObject {

  /**
   * Default values are defined here.
   */
  public static final String DEFAULT_SORT_BY = "price";
  public static final SortOrder DEFAULT_SORT_ORDER = SortOrder.ASC;

  private String type;

  /**
   * Default values are assigned here.
   */
  private String sortBy = DEFAULT_SORT_BY;
  private SortOrder sortOrder = DEFAULT_SORT_ORDER;

  /**
   * Overriding default values on object creation only when builder object has a valid value.
   */
  private ParameterObject(Builder builder) {
    setType(builder.type);
    setSortBy(builder.sortBy != null && !builder.sortBy.isBlank() ? builder.sortBy : sortBy);
    setSortOrder(builder.sortOrder != null ? builder.sortOrder : sortOrder);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getSortBy() {
    return sortBy;
  }

  public void setSortBy(String sortBy) {
    this.sortBy = sortBy;
  }

  public SortOrder getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(SortOrder sortOrder) {
    this.sortOrder = sortOrder;
  }

  @Override
  public String toString() {
    return String.format("ParameterObject[type='%s', sortBy='%s', sortOrder='%s']",
        type, sortBy, sortOrder);
  }

  public static final class Builder {

    private String type;
    private String sortBy;
    private SortOrder sortOrder;

    private Builder() {
    }

    public Builder withType(String type) {
      this.type = type;
      return this;
    }

    public Builder sortBy(String sortBy) {
      this.sortBy = sortBy;
      return this;
    }

    public Builder sortOrder(SortOrder sortOrder) {
      this.sortOrder = sortOrder;
      return this;
    }

    public ParameterObject build() {
      return new ParameterObject(this);
    }
  }
}
