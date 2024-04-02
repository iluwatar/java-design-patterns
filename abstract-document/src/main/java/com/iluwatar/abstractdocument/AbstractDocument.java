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
package com.iluwatar.abstractdocument;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Abstract implementation of Document interface.
 */
public abstract class AbstractDocument implements Document {

  private final Map<String, Object> documentProperties;

  protected AbstractDocument(Map<String, Object> properties) {
    Objects.requireNonNull(properties, "properties map is required");
    this.documentProperties = properties;
  }

  @Override
  public Void put(String key, Object value) {
    documentProperties.put(key, value);
    return null;
  }

  @Override
  public Object get(String key) {
    return documentProperties.get(key);
  }

  @Override
  public <T> Stream<T> children(String key, Function<Map<String, Object>, T> childConstructor) {
    return Stream.ofNullable(get(key))
            .filter(Objects::nonNull)
            .map(el -> (List<Map<String, Object>>) el)
            .findAny()
            .stream()
            .flatMap(Collection::stream)
            .map(childConstructor);
  }

  @Override
  public String toString() {
    return buildStringRepresentation();
  }

  private String buildStringRepresentation() {
    var builder = new StringBuilder();
    builder.append(getClass().getName()).append("[");

    // Explaining variable for document properties map
    Map<String, Object> documentProperties = this.documentProperties;

    // Explaining variable for the size of document properties map
    int numProperties = documentProperties.size();

    // Explaining variable for tracking the current property index
    int currentPropertyIndex = 0;

    // Iterate over document properties map
    for (Map.Entry<String, Object> entry : documentProperties.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();

      // Append key-value pair
      builder.append("[").append(key).append(" : ").append(value).append("]");

      // Add comma if not last property
      if (++currentPropertyIndex < numProperties) {
        builder.append(", ");
      }
    }

    builder.append("]");
    return builder.toString();
  }

}
