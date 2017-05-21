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

import java.applet.Applet;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Drawing in applets is almost always done with double-buffering.
 * This means that drawing is first done to an off-screen image, and when all
 * is done, the off-screen image is drawn on the screen.
 * This reduces the nasty flickering applets otherwise have.
 */
public class DoubleBuffer extends Applet implements Runnable, MouseListener {

  private static final int FPS = 30;

  private int frameNumber = -1;
  private int delay;
  private Thread animatorThread;
  private boolean frozen = false;

  private boolean fillColumnTop = true;

  private Dimension offDimension;
  private Image offImage;
  private Graphics offGraphics;

  @Override
  public void init() {
    delay = 1000 / FPS;
    addMouseListener(this);
  }

  @Override
  public void start() {
    if (!frozen) {
      //Start animating!
      if (animatorThread == null) {
        animatorThread = new Thread(this);
      }
      animatorThread.start();
    }
  }

  @Override
  public void stop() {
    //Stop the animating thread.
    animatorThread = null;

    //Get rid of the objects necessary for double buffering.
    offGraphics = null;
    offImage = null;
  }

  @Override
  public void run() {
    //Remember the starting time.
    long startTime = System.currentTimeMillis();

    //Animation loop.
    while (Thread.currentThread() == animatorThread) {
      //Advance the animation frame.
      frameNumber++;
      repaint();
      //Delay depending on how far we are behind.
      try {
        startTime += delay;
        Thread.sleep(Math.max(0,
            startTime - System.currentTimeMillis()));
      } catch (InterruptedException e) {
        break;
      }
    }
  }

  @Override
  public void paint(Graphics g) {
    update(g);
  }

  @Override
  public void update(Graphics g) {
    Dimension d = getSize();
    boolean fillSquare;
    boolean fillNextFrame;
    int rowWidth = 0;
    int x = 0;
    int y = 0;
    int width;
    int tmp;

    //Create the off-screen graphics context, if no good one exists.
    getOffScreenGraphics(d);

    //Erase the previous image.
    offGraphics.setColor(getBackground());
    offGraphics.fillRect(0, 0, d.width, d.height);
    offGraphics.setColor(Color.black);

    //Set width of first "square". Decide whether to fill it.
    fillSquare = fillColumnTop;
    fillColumnTop = !fillColumnTop;
    int squareSize = 20;
    tmp = frameNumber % squareSize;
    if (tmp == 0) {
      width = squareSize;
      fillNextFrame = !fillSquare;
    } else {
      width = tmp;
      fillNextFrame = fillSquare;
    }

    //Draw from left to right.
    drawSquares(d, fillSquare, rowWidth, x, y, width, squareSize);

    fillColumnTop = fillNextFrame;

    //Paint the image onto the screen.
    g.drawImage(offImage, 0, 0, this);
  }

  private void drawSquares(Dimension dimension, boolean fillSquare,
                           int rowWidth, int x, int y, int width, int squareSize) {
    int height;
    while (x < dimension.width) {
      int colHeight = 0;

      //Draw the column.
      while (y < dimension.height) {
        colHeight += squareSize;

        //If we don't have room for a full square, cut if off.
        if (colHeight > dimension.height) {
          height = dimension.height - y;
        } else {
          height = squareSize;
        }

        //Draw the rectangle if necessary.
        if (fillSquare) {
          offGraphics.fillRect(x, y, width, height);
          fillSquare = false;
        } else {
          fillSquare = true;
        }

        y += height;
      }

      //Determine x, y, and width for the next go around.
      x += width;
      y = 0;
      width = squareSize;
      rowWidth += width;
      if (rowWidth > dimension.width) {
        width = dimension.width - x;
      }
      fillSquare = fillColumnTop;
      fillColumnTop = !fillColumnTop;
    }
  }

  private void getOffScreenGraphics(Dimension d) {
    if (offGraphics == null || d.width != offDimension.width || d.height != offDimension.height) {
      offDimension = d;
      offImage = createImage(d.width, d.height);
      offGraphics = offImage.getGraphics();
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (frozen) {
      frozen = false;
      start();
    } else {
      frozen = true;

      //Instead of calling stop(), which destroys the
      //back buffer, just stop the animating thread.
      animatorThread = null;
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }
}
