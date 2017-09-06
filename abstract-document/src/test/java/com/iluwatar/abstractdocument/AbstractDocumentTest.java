/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.abstractdocument;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * AbstractDocument test class
 */
public class AbstractDocumentTest {

  private static final String KEY = "key";
  private static final String VALUE = "value";

  private class DocumentImplementation extends AbstractDocument {

    DocumentImplementation(Map<String, Object> properties) {
      super(properties);
    }
  }

  private DocumentImplementation document = new DocumentImplementation(new HashMap<>());

  @Test
  public void shouldPutAndGetValue() {
    document.put(KEY, VALUE);
    assertEquals(VALUE, document.get(KEY));
  }

  @Test
  public void shouldRetrieveChildren() {
    Map<String, Object> child1 = new HashMap<>();
    Map<String, Object> child2 = new HashMap<>();
    List<Map<String, Object>> children = Arrays.asList(child1, child2);

    document.put(KEY, children);

    Stream<DocumentImplementation> childrenStream = document.children(KEY, DocumentImplementation::new);
    assertNotNull(children);
    assertEquals(2, childrenStream.count());
  }

  @Test
  public void shouldRetrieveEmptyStreamForNonExistingChildren() {
    Stream<DocumentImplementation> children = document.children(KEY, DocumentImplementation::new);
    assertNotNull(children);
    assertEquals(0, children.count());
  }

  @Test
  public void shouldIncludePropsInToString() {
    Map<String, Object> props = new HashMap<>();
    props.put(KEY, VALUE);
    DocumentImplementation document = new DocumentImplementation(props);
    assertNotNull(document.toString().contains(KEY));
    assertNotNull(document.toString().contains(VALUE));
  }

}
