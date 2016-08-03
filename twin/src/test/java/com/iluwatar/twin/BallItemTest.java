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
package com.iluwatar.twin;

import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Date: 12/30/15 - 18:44 PM
 *
 * @author Jeroen Meulemeester
 */
public class BallItemTest extends StdOutTest {

  @Test
  public void testClick() {
    final BallThread ballThread = mock(BallThread.class);
    final BallItem ballItem = new BallItem();
    ballItem.setTwin(ballThread);

    final InOrder inOrder = inOrder(ballThread);

    for (int i = 0; i < 10; i++) {
      ballItem.click();
      inOrder.verify(ballThread).suspendMe();

      ballItem.click();
      inOrder.verify(ballThread).resumeMe();
    }

    inOrder.verifyNoMoreInteractions();
  }

  @Test
  public void testDoDraw() {
    final BallItem ballItem = new BallItem();
    final BallThread ballThread = mock(BallThread.class);
    ballItem.setTwin(ballThread);

    ballItem.draw();
    verify(getStdOutMock()).println("draw");
    verify(getStdOutMock()).println("doDraw");

    verifyNoMoreInteractions(ballThread, getStdOutMock());
  }

  @Test
  public void testMove() {
    final BallItem ballItem = new BallItem();
    final BallThread ballThread = mock(BallThread.class);
    ballItem.setTwin(ballThread);

    ballItem.move();
    verify(getStdOutMock()).println("move");

    verifyNoMoreInteractions(ballThread, getStdOutMock());
  }

}