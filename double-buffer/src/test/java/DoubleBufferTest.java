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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link DoubleBuffer}
 */
public class DoubleBufferTest {

  @Mock
  private Graphics graphics;

  @Mock
  private Dimension dimension;

  private DoubleBuffer doubleBuffer;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    this.doubleBuffer = new DoubleBuffer();
  }

  @After
  public void tearDown() throws Exception {
    this.doubleBuffer = null;
  }

  @Test
  public void onMouseClickTheShowMustNotGoOnAfterMouseClick() throws NoSuchFieldException, IllegalAccessException {
    doubleBuffer.init();
    MouseEvent me = new MouseEvent(doubleBuffer, 0, 0, 0, 100, 100, 1, false);

    MouseListener[] listeners = doubleBuffer.getMouseListeners();
    listeners[0].mousePressed(me);

    Field f = doubleBuffer.getClass().getDeclaredField("frozen"); //NoSuchFieldException
    f.setAccessible(true);
    assertTrue((boolean) f.get(doubleBuffer)); //IllegalAccessException
  }

  @Test
  public void onStartANewThreadShouldStart() throws NoSuchFieldException, IllegalAccessException {
    doubleBuffer.start();

    Field f = doubleBuffer.getClass().getDeclaredField("animatorThread"); //NoSuchFieldException
    f.setAccessible(true);

    Thread animatorThread = (Thread) f.get(doubleBuffer);
    assertEquals(Thread.State.RUNNABLE, animatorThread.getState());
  }

}