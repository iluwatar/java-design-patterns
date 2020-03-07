/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.acyclicvisitor;


import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

/**
 * Zoom test class
 */
public class ZoomTest {
  
  @Test
  public void testAcceptForDos() {  
    var zoom = new Zoom();
    var mockVisitor = mock(ConfigureForDosVisitor.class);
    
    zoom.accept(mockVisitor);
    verify((ZoomVisitor)mockVisitor).visit(eq(zoom));
  }
  
  @Test
  public void testAcceptForUnix() {
    var zoom = new Zoom();
    var mockVisitor = mock(ConfigureForUnixVisitor.class);
    
    zoom.accept(mockVisitor);
    verify((ZoomVisitor)mockVisitor).visit(eq(zoom));
  }
}
