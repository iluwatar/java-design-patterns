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

package com.iluwatar.facet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.facet.dragon.Dragon;
import com.iluwatar.facet.dragon.DragonFacet;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

/**
 * Test that the definition of a facet is upheld. That is,
 * a facet handles all access to the protected object,
 * and the functions of the facet are a subset of the
 * protected object's functions.
 */
public class MethodsOfFacetTest {
  /**
   * No functions should be callable from the protected class.
   * All objects must only be able to interact with the facet.
   */
  @Test
  public void visibleDragonMethods() {
    Dragon dragon = new Dragon(100);
    Method[] methods = Dragon.class.getDeclaredMethods();
    for (Method method : methods) {
      assertThrows(IllegalAccessException.class,
          () -> method.invoke(dragon, ""));
    }
  }

  /**
   * Checks that the functions in the facet are a subset of the functions in
   * the class it is faceting. This means that:
   * 1. The number of functions in the facet class are less than or equal to
   *    the number of functions in the protected class.
   * 2. Every protected class function that is marked with "faceted" must be
   *    in the facet class (with "faceted" removed). All other functions must
   *    not be in the facet class. "faceted" is the way of marking a function
   *    to be used in the facet, which is recommended at
   *    <a href="https://wiki.c2.com/?FacetPattern">Facet - c2 wiki.</a>
   * 3. Every function in the facet class must be in the protected class
   *    (with "faceted" in front of the name).
   * (2. and 3. imply 1.)
   */
  @Test
  public void facetIsSubset() {
    Method[] dragonMethods = Dragon.class.getDeclaredMethods();
    Method[] dragonFacetMethods = DragonFacet.class.getDeclaredMethods();

    //Check 1. from javadoc comment.
    assertTrue(dragonMethods.length >= dragonFacetMethods.length);

    ArrayList<String> facetMethodNames = new ArrayList<>();
    for (Method facetMethod : dragonFacetMethods) {
      if (!facetMethod.isSynthetic()) {
        facetMethodNames.add(facetMethod.getName());
      }
    }
    ArrayList<String> dragonMethodNames = new ArrayList<>();
    for (Method dragonMethod : dragonMethods) {
      if (!dragonMethod.isSynthetic()) {
        dragonMethodNames.add(dragonMethod.getName());
      }
    }

    //Check 2. from javadoc comment.
    for (String dragonMethodName : dragonMethodNames) {
      if (dragonMethodName.startsWith("faceted")) {
        String expectedName = dragonMethodName.substring("faceted".length());
        expectedName = Character.toLowerCase(expectedName.charAt(0))
            + expectedName.substring(1);
        assertTrue(facetMethodNames.contains(expectedName));
      } else {
        assertFalse(facetMethodNames.contains(dragonMethodName));
      }
    }

    //Check 3. from javadoc comment.
    for (String facetMethodName : facetMethodNames) {
      String expectedName = "faceted"
          + Character.toUpperCase(facetMethodName.charAt(0))
          + facetMethodName.substring(1);
      assertTrue(dragonMethodNames.contains(expectedName));
    }
  }
}
