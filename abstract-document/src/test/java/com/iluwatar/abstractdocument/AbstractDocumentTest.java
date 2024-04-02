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

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AbstractDocument test class
 */
class AbstractDocumentTest {

  private static final String KEY = "key";
  private static final String VALUE = "value";

  private static class DocumentImplementation extends AbstractDocument {

    DocumentImplementation(Map<String, Object> properties) {
      super(properties);
    }
  }

  private final DocumentImplementation document = new DocumentImplementation(new HashMap<>());

  @Test
  void shouldPutAndGetValue() {
    document.put(KEY, VALUE);
    assertEquals(VALUE, document.get(KEY));
  }

  @Test
  void shouldRetrieveChildren() {
    var children = List.of(Map.of(), Map.of());

    document.put(KEY, children);

    var childrenStream = document.children(KEY, DocumentImplementation::new);
    assertNotNull(children);
    assertEquals(2, childrenStream.count());
  }

  @Test
  void shouldRetrieveEmptyStreamForNonExistingChildren() {
    var children = document.children(KEY, DocumentImplementation::new);
    assertNotNull(children);
    assertEquals(0, children.count());
  }

  @Test
  void shouldIncludePropsInToString() {
    var props = Map.of(KEY, (Object) VALUE);
    var document = new DocumentImplementation(props);
    assertTrue(document.toString().contains(KEY));
    assertTrue(document.toString().contains(VALUE));
  }

  @Test
  void shouldHandleExceptionDuringConstruction() {
    Map<String, Object> invalidProperties = null; // Invalid properties, causing NullPointerException

    // Throw null pointer exception
    assertThrows(NullPointerException.class, () -> {
      // Attempt to construct a document with invalid properties
      new DocumentImplementation(invalidProperties);
    });
  }

  @Test
  void shouldPutAndGetNestedDocument() {
    // Creating a nested document
    DocumentImplementation nestedDocument = new DocumentImplementation(new HashMap<>());
    nestedDocument.put("nestedKey", "nestedValue");


    document.put("nested", nestedDocument);

    // Retrieving the nested document
    DocumentImplementation retrievedNestedDocument = (DocumentImplementation) document.get("nested");

    assertNotNull(retrievedNestedDocument);
    assertEquals("nestedValue", retrievedNestedDocument.get("nestedKey"));
  }

  @Test
  void shouldUpdateExistingValue() {
    // Arrange
    final String key = "key";
    final String originalValue = "originalValue";
    final String updatedValue = "updatedValue";

    document.put(key, originalValue);

    // Updating the value
    document.put(key, updatedValue);

    //Verifying that the updated value is retrieved correctly
    assertEquals(updatedValue, document.get(key));
  }
}
